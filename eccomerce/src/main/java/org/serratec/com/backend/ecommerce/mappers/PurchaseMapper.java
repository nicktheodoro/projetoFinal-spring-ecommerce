package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchaseDto;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {

	public PurchaseEntity toModel(PurchaseDto dto) {
		PurchaseEntity purchase = new PurchaseEntity();
		purchase.setNumeroPedido(dto.getNumeroPedido());
		purchase.setDataEntrega(dto.getDataEntrega());
		purchase.setDataPedido(dto.getDataPedido());
		purchase.setCliente(dto.getCliente());
		purchase.setStatus(dto.getStatus());
		purchase.setValorTotal(dto.getValorTotal());

		return purchase;
	}

	public PurchaseDto toDto(PurchaseEntity purchase) {
		PurchaseDto dto = new PurchaseDto();
		dto.setNumeroPedido(purchase.getNumeroPedido());
		dto.setDataEntrega(purchase.getDataEntrega());
		dto.setDataPedido(purchase.getDataPedido());
		dto.setCliente(purchase.getCliente());
		dto.setStatus(purchase.getStatus());
		dto.setValorTotal(purchase.getValorTotal());

		return dto;
	}
}
