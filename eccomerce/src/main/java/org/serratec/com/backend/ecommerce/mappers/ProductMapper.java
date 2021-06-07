package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductEntity toEntity(ProductDto dto) {
		ProductEntity product = new ProductEntity();
		product.setNome(dto.getNome());
		product.setDescricao(dto.getDescricao());
		product.setPreco(dto.getPreco());
		product.setQuantidadeEstoque(dto.getQuantidadeEstoque());
		product.setDataCadastro(dto.getDataCadastro());
		product.setCategoria(dto.getCategoria());
//		product.setImagem(dto.getImagem());

		return product;
	}

	public ProductDto toDto(ProductEntity product) {
		ProductDto dto = new ProductDto();
		dto.setNome(product.getNome());
		dto.setDescricao(product.getDescricao());
		dto.setPreco(product.getPreco());
		dto.setQuantidadeEstoque(product.getQuantidadeEstoque());
		dto.setDataCadastro(product.getDataCadastro());
		dto.setCategoria(product.getCategoria());
//		product.setImagem(dto.getImagem());

		return dto;
	}

	public List<ProductDto> listToDto(List<ProductEntity> produtos) {
		List<ProductDto> list = new ArrayList<>();
		for (ProductEntity entity : produtos) {
			ProductDto dto = this.toDto(entity);
			list.add(dto);
		}
		return list;
	}
	public List<ProductEntity> listToEntity(List<ProductDto> produtos) {
		List<ProductEntity> list = new ArrayList<>();
		for (ProductDto dto : produtos) {
			ProductEntity entity = this.toEntity(dto);
			list.add(entity);
		}
		return list;
	}
}
