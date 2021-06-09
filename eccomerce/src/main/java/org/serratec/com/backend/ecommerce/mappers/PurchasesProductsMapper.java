package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchasesProductsDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.ProductService;
import org.serratec.com.backend.ecommerce.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchasesProductsMapper {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	PurchaseService purchaseService;
	
	public PurchasesProductsEntity toEntity(PurchasesProductsDto dto) throws EntityNotFoundException {
		PurchasesProductsEntity entity = new PurchasesProductsEntity();
		entity.setPreco(dto.getPreco());
		entity.setQuantidade(dto.getQuantidade());
		entity.setProdutos(productService.findById(dto.getProduto()));
		entity.setPedidos(purchaseService.findById(dto.getPedido()));

		return entity;
	}

	public PurchasesProductsDto toDto(PurchasesProductsEntity entity) {
		PurchasesProductsDto dto = new PurchasesProductsDto();
		dto.setPreco(entity.getPreco());
		dto.setQuantidade(entity.getQuantidade());
		dto.setProduto(entity.getProdutos().getId());
		dto.setPedido(entity.getPedidos().getId());
		
		return dto;
	}
}
