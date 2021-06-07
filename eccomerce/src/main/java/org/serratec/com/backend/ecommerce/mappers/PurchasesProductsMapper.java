package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchasesProductsDto;
import org.springframework.stereotype.Component;

@Component
public class PurchasesProductsMapper {

	public PurchasesProductsEntity toEntity(PurchasesProductsDto dto) {
		PurchasesProductsEntity entity = new PurchasesProductsEntity();
		entity.setPreco(dto.getPreco());
		entity.setQuantidade(dto.getQuantidade());

		return entity;
	}

	public PurchasesProductsDto toDto(PurchasesProductsEntity entity) {
		PurchasesProductsDto dto = new PurchasesProductsDto();
		entity.setPreco(dto.getPreco());
		entity.setQuantidade(dto.getQuantidade());

		return dto;
	}
}
