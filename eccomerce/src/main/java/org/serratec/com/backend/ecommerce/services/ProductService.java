package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProductDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProductException;
import org.serratec.com.backend.ecommerce.mappers.ProductMapper;
import org.serratec.com.backend.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;

	@Autowired
	ProductMapper mapper;

	@Autowired
	CategoryService service;

	public ProductEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<ProductEntity> findByCategoriaId(Long idCategoria) {
		return repository.findByCategoriaId(idCategoria);
	}

	public List<ProductDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public ProductDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public ProductDto getByName(String nome) throws EntityNotFoundException{
		return mapper.toDto(repository.findByNome(nome.toLowerCase()));
	}

	public ProductEntity findByName(String nome) throws EntityNotFoundException{
		return repository.findByNome(nome.toLowerCase());
	}
	
	public ProductDto create(ProductDto product) throws EntityNotFoundException, ProductException {
		try {
			if (product.getNome().isBlank() || product.getPreco() == null || product.getQuantidadeEstoque() == null
					|| product.getCategoria() == null) {
				throw new ProductException("Os campos Nome, Preço, Quantidade em estoque e categoria são obrigatórios!");
			}
			else {
				product.setNome(product.getNome().toLowerCase());
				ProductEntity entity = mapper.toEntity(product);
				entity.setCategoria(service.findById(product.getCategoria()));
				
				return mapper.toDto(repository.save(entity));
			}	
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("Categoria com id: " + product.getCategoria() + " não existe");
		}
	}

	public ProductDto update(Long id, ProductDto productUpdate) throws EntityNotFoundException {
		ProductEntity product = this.findById(id);
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

	public void delete(Long id) throws EntityNotFoundException, ProductException {
		if (this.findById(id) != null) {
			repository.deleteById(id);
		} else {
			throw new ProductException(
					"Produto com id: " + id + " já vinculado a um ou mais pedidos, favor verificar!");
		}

	}
}
