package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.PedidoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CadastroPedidoDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
	
	@Autowired
	ClienteService service;
	
	public PedidoEntity toEntity(PedidoDto dto) throws EntityNotFoundException {
		PedidoEntity purchase = new PedidoEntity();
		purchase.setNumeroPedido(dto.getNumeroPedido());
		purchase.setDataEntrega(dto.getDataEntrega());
		purchase.setDataPedido(dto.getDataPedido());
		purchase.setCliente(service.findById(dto.getCliente()));
		purchase.setStatus(dto.getStatus());
		purchase.setValorTotal(dto.getValorTotal());

		return purchase;
	}

	public PedidoDto toDto(PedidoEntity purchase) {
		PedidoDto dto = new PedidoDto();
		dto.setNumeroPedido(purchase.getNumeroPedido());
		dto.setDataEntrega(purchase.getDataEntrega());
		dto.setDataPedido(purchase.getDataPedido());
		dto.setCliente(purchase.getCliente().getId());
		dto.setStatus(purchase.getStatus());
		dto.setValorTotal(purchase.getValorTotal());

		return dto;
	}
	
	public CadastroPedidoDto toCadastroPedidoDto(PedidoDto pedido) {
		CadastroPedidoDto cadastroPedido = new CadastroPedidoDto();
		cadastroPedido.setNumeroPedido(pedido.getNumeroPedido());
		cadastroPedido.setCliente(pedido.getCliente());
		cadastroPedido.setStatus(pedido.getStatus());
		cadastroPedido.setValorTotal(pedido.getValorTotal());
		
		return cadastroPedido;
	}
	
}
