package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchaseDto;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {

	public PurchaseEntity toModel(PurchaseDto dto) {
		PurchaseEntity purchase = new PurchaseEntity();
		purchase.setNumeroPedido(dto.getNrPedido());
		purchase.setDataEntrega(dto.getDtEntrega());
		purchase.setDataPedido(dto.getDtPedido());
		purchase.setCliente(dto.getCliente());
		purchase.setStatus(dto.getStatus());
		purchase.setValorTotal(dto.getValorTotal());
		//purchase.setListaDeProdutosDoPedido(dto.getListaProdutos());

		return purchase;
	}

	public PurchaseDto toDto(PurchaseEntity purchase) {
		PurchaseDto dto = new PurchaseDto();
		dto.setNrPedido(purchase.getNumeroPedido());
		dto.setDtEntrega(purchase.getDataEntrega());
		dto.setDtPedido(purchase.getDataPedido());
		dto.setCliente(purchase.getCliente());
		dto.setStatus(purchase.getStatus());
		dto.setValorTotal(purchase.getValorTotal());
		//dto.setListaProdutos(purchase.getListaDeProdutosDoPedido());

		return dto;
	}
}
