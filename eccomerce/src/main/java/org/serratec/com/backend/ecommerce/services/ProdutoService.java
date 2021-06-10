package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutoDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.mappers.ProdutoMapper;
import org.serratec.com.backend.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository repository;

	@Autowired
	ProdutoMapper mapper;

	@Autowired
	CategoriaService service;

	public ProdutoEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<ProdutoEntity> findByCategoriaId(Long idCategoria) {
		return repository.findByCategoriaId(idCategoria);
	}

	public List<ProdutoDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public ProdutoDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public ProdutoDto getByName(String nome) throws EntityNotFoundException{
		return mapper.toDto(repository.findByNome(nome.toLowerCase()));
	}

	public ProdutoEntity findByName(String nome) throws EntityNotFoundException{
		return repository.findByNome(nome.toLowerCase());
	}
	
	public ProdutoDto create(ProdutoDto product) throws EntityNotFoundException, ProdutoException {
		try {
			if (product.getNome().isBlank() || product.getPreco() == null || product.getQuantidadeEstoque() == null
					|| product.getCategoria() == null) {
				throw new ProdutoException("Os campos Nome, Preço, Quantidade em estoque e categoria são obrigatórios!");
			}
			else {
				product.setNome(product.getNome().toLowerCase());
				ProdutoEntity entity = mapper.toEntity(product);
				entity.setCategoria(service.findById(product.getCategoria()));
				
				return mapper.toDto(repository.save(entity));
			}	
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("Categoria com id: " + product.getCategoria() + " não existe");
		}
	}
	
//	public List<ProductDto> createList(List<ProductDto> productDto){ // mudar para entity
//		List<ProductEntity> listProducts= new ArrayList<>();
//		for (ProductDto product : productDto) {
//			if(repository.findByNome(product.getNome())!=null) {
//				listProducts.add(null)
//			}
//		}
//		
//		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
//	}

	public ProdutoDto update(Long id, ProdutoDto productUpdate) throws EntityNotFoundException {
		ProdutoEntity product = this.findById(id);
		product.setNome(productUpdate.getNome());
		product.setPreco(productUpdate.getPreco());
		product.setQuantidadeEstoque(productUpdate.getQuantidadeEstoque());
		product.setCategoria(service.findById(productUpdate.getCategoria()));

		if (productUpdate.getDescricao() != null) {
			product.setDescricao(productUpdate.getDescricao());
		}

		// Update de imagem
//		if(dto.getImagem() != null) {
//			product.setImagem(dto.getImagem());
//		}	
		return mapper.toDto(repository.save(product));
	}

	public void delete(Long id) throws EntityNotFoundException, ProdutoException {
		if (this.findById(id) != null) {
			repository.deleteById(id);
		} else {
			throw new ProdutoException(
					"Produto com id: " + id + " já vinculado a um ou mais pedidos, favor verificar!");
		}

	}
}
