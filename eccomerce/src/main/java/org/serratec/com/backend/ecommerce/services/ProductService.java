package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProductDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
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
	
	private ProductEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}
	
	public List<ProductDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList()); 
	}
	
	public ProductDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}
	
	public ProductDto create(ProductDto product) {
		repository.save(mapper.toModel(product));
		return product;
	}
	
	public ProductDto update(Long id, ProductDto dto) throws EntityNotFoundException {
		ProductEntity product = this.findById(id);
		
				
		if(dto.getNome() != null) {
			product.setNome(dto.getNome());	
		}
		if(dto.getDescricao() != null) {
			product.setDescricao(dto.getDescricao());
		}
		if(dto.getPreco()!= null) {
			product.setPreco(dto.getPreco());
		}
		if(dto.getQtdEstoque() != null) {
			product.setQtdEstoque(dto.getQtdEstoque());
		}
		if(dto.getCategoria() != null) {
			product.setCategoria(dto.getCategoria());
		}		
		//Update de imagem
//		if(dto.getImagem() != null) {
//			product.setImagem(dto.getImagem());
//		}	
		return mapper.toDto(repository.save(product));
	}
	
	public void delete(Long id) throws EntityNotFoundException {
		if(this.findById(id) !=null) {
			repository.deleteById(id);
		}
	}
}
