package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchaseDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {
	
	@Autowired
	ClientService service;
	
	public PurchaseEntity toEntity(PurchaseDto dto) throws EntityNotFoundException {
		PurchaseEntity purchase = new PurchaseEntity();
		purchase.setNumeroPedido(dto.getNumeroPedido());
		purchase.setDataEntrega(dto.getDataEntrega());
		purchase.setDataPedido(dto.getDataPedido());
		purchase.setCliente(service.findById(dto.getCliente()));
		purchase.setStatus(dto.getStatus());
		purchase.setValorTotal(dto.getValorTotal());

		return purchase;
	}

	public PurchaseDto toDto(PurchaseEntity purchase) {
		PurchaseDto dto = new PurchaseDto();
		dto.setNumeroPedido(purchase.getNumeroPedido());
		dto.setDataEntrega(purchase.getDataEntrega());
		dto.setDataPedido(purchase.getDataPedido());
		dto.setCliente(purchase.getCliente().getId());
		dto.setStatus(purchase.getStatus());
		dto.setValorTotal(purchase.getValorTotal());

		return dto;
	}
}
