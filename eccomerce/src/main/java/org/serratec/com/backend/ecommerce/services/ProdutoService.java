package org.serratec.com.backend.ecommerce.services;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutoDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.mappers.ProdutoMapper;
import org.serratec.com.backend.ecommerce.repositories.CarrinhoRepository;
import org.serratec.com.backend.ecommerce.repositories.ImagemRepository;
import org.serratec.com.backend.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	ProdutoMapper produtoMapper;

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	ImagemService imagemService;

	@Autowired
	ImagemRepository imagemRepository;

	@Autowired
	CarrinhoRepository carrinhoRepository;

	public ProdutoEntity findById(Long id) throws EntityNotFoundException {
		return produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<ProdutoEntity> findByCategoriaId(Long idCategoria) {
		return produtoRepository.findByCategoriaId(idCategoria);
	}
	
	public List<ProdutoDto> getByCategoriaNome(String nomeCategoria) throws EntityNotFoundException {
		return produtoMapper
				.listToDto((produtoRepository.findByCategoriaId(categoriaService.findByNome(nomeCategoria.toLowerCase()).getId())));
	}

	public List<ProdutoDto> getAll() {
		return produtoRepository.findAll().stream().map(produtoMapper::toDto).collect(Collectors.toList());
	}

	public ProdutoDto getById(Long id) throws EntityNotFoundException {
		return produtoMapper.toDto(this.findById(id));
	}

	public ProdutoDto getByName(String nome) throws EntityNotFoundException {
		return produtoMapper.toDto(produtoRepository.findByNome(nome.toLowerCase()));
	}

	public ProdutoEntity findByName(String nome) throws EntityNotFoundException {
		return produtoRepository.findByNome(nome.toLowerCase());
	}

	public ProdutoDto create(ProdutoDto produtoDto, MultipartFile file)
			throws EntityNotFoundException, ProdutoException, IOException {
		produtoDto.setNome(produtoDto.getNome().toLowerCase());
		try {
			if (produtoDto.getNome().isBlank() || produtoDto.getPreco() == null
					|| produtoDto.getQuantidadeEstoque() == null || produtoDto.getCategoria().isBlank()) {
				throw new ProdutoException(
						"Os campos Nome, Preço, Quantidade em estoque e categoria são obrigatórios!");
			} else {
				if (this.findByName(produtoDto.getNome()) != null) {
					throw new ProdutoException("Produto com o nome: " + produtoDto.getNome() + " já cadastrado.");
				} else {
					produtoDto.setNome(produtoDto.getNome().toLowerCase());
					ProdutoEntity produtoEntity = produtoMapper.toEntity(produtoDto);
					produtoEntity.setCategoria(categoriaService.findByNome(produtoDto.getCategoria().toLowerCase()));

					produtoEntity = produtoRepository.save(produtoEntity);
					imagemService.create(produtoEntity, file);
					ProdutoDto pDto = produtoMapper.toDto(produtoEntity);
					
					pDto.setUrl(criarImagem(produtoEntity.getId()));

					return pDto;
				}

			}
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("Categoria com id: " + produtoDto.getCategoria() + " não existe");
		}
	}

	public ProdutoDto update(String nome, ProdutoDto produtoUpdate) throws EntityNotFoundException {
		ProdutoEntity produtoEntity = this.findByName(nome.toLowerCase());
		produtoEntity.setNome(produtoUpdate.getNome().toLowerCase());
		produtoEntity.setPreco(produtoUpdate.getPreco());
		produtoEntity.setQuantidadeEstoque(produtoUpdate.getQuantidadeEstoque());
		produtoEntity.setCategoria(categoriaService.findByNome(produtoUpdate.getCategoria()));

		if (produtoUpdate.getDescricao() != null) {
			produtoEntity.setDescricao(produtoUpdate.getDescricao());
		}

		return produtoMapper.toDto(produtoRepository.save(produtoEntity));
	}

	public void delete(String nome) throws EntityNotFoundException, ProdutoException {
		ProdutoEntity produto = this.findByName(nome.toLowerCase());
		if(nome.isBlank()) {
			throw new ProdutoException("É necessário informar o nome do produto que se deseja deletar");
		}
		if (produto != null) {
			if (carrinhoRepository.findByProdutos(this.findById(produto.getId())).isEmpty()) {
				imagemService.deletarImagemProduto(produto.getId());
				produtoRepository.deleteById(produto.getId());
			} else {
				throw new ProdutoException(
						"Produto com nome: " + nome + " já vinculado a um ou mais pedidos, favor verificar!");
			}
		} else {
			throw new EntityNotFoundException("Produto com nome: " + nome + "não encontrado!");
		}

	}

	public void removerEstoque(Long idProduto, Integer quantidade) throws EntityNotFoundException, ProdutoException {
		ProdutoEntity produtoEntity = this.findById(idProduto);

		if (quantidade < produtoEntity.getQuantidadeEstoque()) {
			produtoEntity.setQuantidadeEstoque(produtoEntity.getQuantidadeEstoque() - quantidade);
			produtoRepository.save(produtoEntity);
		} else {
			throw new ProdutoException("Estoque indisponível");
		}
	}

	public void devolverEstoque(Long idProduto, Integer quantidade) throws EntityNotFoundException, ProdutoException {
		ProdutoEntity produtoEntity = this.findById(idProduto);

		produtoEntity.setQuantidadeEstoque(produtoEntity.getQuantidadeEstoque() + quantidade);
		produtoRepository.save(produtoEntity);

	}

	public String criarImagem(Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("produto/{produtoId}/imagem")
				.buildAndExpand(id).toUri();
		return uri.toString();

	}

}
