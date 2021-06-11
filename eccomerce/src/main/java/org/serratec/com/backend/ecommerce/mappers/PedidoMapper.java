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
	ClienteService clienteService;
	
	public PedidoEntity toEntity(PedidoDto dto) throws EntityNotFoundException {
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroPedido(dto.getNumeroPedido());
		entity.setDataEntrega(dto.getDataEntrega());
		entity.setDataPedido(dto.getDataPedido());
		entity.setCliente(clienteService.findById(dto.getCliente()));
		entity.setStatus(dto.getStatus());
		entity.setValorTotal(dto.getValorTotal());

		return entity;
	}

	public PedidoDto toDto(PedidoEntity entity) {
		PedidoDto dto = new PedidoDto();
		dto.setNumeroPedido(entity.getNumeroPedido());
		dto.setDataEntrega(entity.getDataEntrega());
		dto.setDataPedido(entity.getDataPedido());
		dto.setCliente(entity.getCliente().getId());
		dto.setStatus(entity.getStatus());
		dto.setValorTotal(entity.getValorTotal());

		return dto;
	}
	
	public CadastroPedidoDto toCadastroPedidoDto(PedidoDto pedidoDto) {
		CadastroPedidoDto cadastroPedidoDto = new CadastroPedidoDto();
		cadastroPedidoDto.setNumeroPedido(pedidoDto.getNumeroPedido());
		cadastroPedidoDto.setCliente(pedidoDto.getCliente());
		cadastroPedidoDto.setStatus(pedidoDto.getStatus());
		cadastroPedidoDto.setValorTotal(pedidoDto.getValorTotal());
		
		return cadastroPedidoDto;
	}
	
}
