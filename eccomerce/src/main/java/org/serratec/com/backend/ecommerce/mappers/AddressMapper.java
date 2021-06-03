package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.AddressEntity;
import org.serratec.com.backend.ecommerce.entities.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

	public AddressEntity toEntity(AddressDto dto) {
		AddressEntity entity = new AddressEntity();
		entity.setCep(dto.getCep());
		entity.setRua(dto.getRua());
		entity.setBairro(dto.getBairro());
		entity.setCidade(dto.getCidade());
		entity.setNumero(dto.getNumero());
		entity.setComplemento(dto.getComplemento());
		entity.setEstado(dto.getEstado());
		
		return entity;
	}
	
	public AddressDto toDto(AddressEntity entity) {
		AddressDto dto = new AddressDto();
		dto.setCep(entity.getCep());
		dto.setRua(entity.getRua());
		dto.setBairro(entity.getBairro());
		dto.setCidade(entity.getCidade());
		dto.setNumero(entity.getNumero());
		dto.setComplemento(entity.getComplemento());
		dto.setEstado(entity.getEstado());
		
		return dto;
	}
}
