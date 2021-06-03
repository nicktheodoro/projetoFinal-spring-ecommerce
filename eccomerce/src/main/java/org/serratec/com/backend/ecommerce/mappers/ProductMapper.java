package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProductDto;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
	
	public ProductEntity toModel(ProductDto dto) {
		ProductEntity product = new ProductEntity();
		product.setNome(dto.getNome());
		product.setDescricao(dto.getDescricao());
		product.setPreco(dto.getPreco());
		product.setQtdEstoque(dto.getQtdEstoque());
		product.setDtCadastro(dto.getDtCadastro());
		product.setCategoria(dto.getCategoria());
//		product.setImagem(dto.getImagem());
		
		
		return product;
	}

	public ProductDto toDto(ProductEntity product) {
		ProductDto dto = new ProductDto();
		
		dto.setNome(product.getNome());
		dto.setDescricao(product.getDescricao());
		dto.setPreco(product.getPreco());
		dto.setQtdEstoque(product.getQtdEstoque());
		dto.setCategoria(product.getCategoria());
//		product.setImagem(dto.getImagem());
		
		return dto;
	}
}
