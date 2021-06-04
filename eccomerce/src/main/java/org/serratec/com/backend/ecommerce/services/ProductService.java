package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProductDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.ProductMapper;
import org.serratec.com.backend.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;

	@Autowired
	ProductMapper mapper;

	public List<ProductDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public ProductDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public ProductDto create(ProductDto product) {
		try {
			repository.save(mapper.toModel(product));
			return product;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Categoria: " + product.getCategoria().getId() + " não existe");
		}
	}

	public ProductDto update(Long id, ProductDto dto) throws EntityNotFoundException {
		ProductEntity product = this.findById(id);
		product.setNome(dto.getNome());
		product.setPreco(dto.getPreco());
		product.setQtdEstoque(dto.getQtdEstoque());
		product.setCategoria(dto.getCategoria());

		if (dto.getDescricao() != null) {
			product.setDescricao(dto.getDescricao());
		}

		// Update de imagem
//		if(dto.getImagem() != null) {
//			product.setImagem(dto.getImagem());
//		}	
		return mapper.toDto(repository.save(product));
	}

	public void delete(Long id) throws EntityNotFoundException {
		try {
			if (this.findById(id) != null) {
				repository.deleteById(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Produto com id: " + id + " está associado a um ou mais pedidos, favor verificar");
		}
	}
	
	private ProductEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}
}
