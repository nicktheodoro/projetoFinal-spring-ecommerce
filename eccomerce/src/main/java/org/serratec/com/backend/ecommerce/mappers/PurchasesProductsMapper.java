package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.PurchasesProducts;
import org.serratec.com.backend.ecommerce.entities.dto.PurchasesProductsDto;
import org.springframework.stereotype.Component;

@Component
public class PurchasesProductsMapper {

	public PurchasesProducts toEntity(PurchasesProductsDto dto) {
		PurchasesProducts entity = new PurchasesProducts();
		entity.setPreco(dto.getPreco());
		entity.setQuantidade(dto.getQuantidade());

		return entity;
	}

	public PurchasesProductsDto toDto(PurchasesProducts entity) {
		PurchasesProductsDto dto = new PurchasesProductsDto();
		entity.setPreco(dto.getPreco());
		entity.setQuantidade(dto.getQuantidade());

		return dto;
	}
}
