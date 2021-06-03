package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.AddressEntity;
import org.serratec.com.backend.ecommerce.entities.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

	public AddressEntity toEntity(AddressDto dto) {
		AddressEntity entity = new AddressEntity();
		entity.setCep(dto.getCep());
		entity.setRua(dto.getLogradouro());
		entity.setBairro(dto.getBairro());
		entity.setCidade(dto.getLocalidade());
		entity.setNumero(dto.getNumero());
		entity.setComplemento(dto.getComplemento());
		entity.setEstado(dto.getUf());
		
		return entity;
	}
	
	public AddressDto toDto(AddressEntity entity) {
		AddressDto dto = new AddressDto();
		dto.setCep(entity.getCep());
		dto.setLogradouro(entity.getRua());
		dto.setBairro(entity.getBairro());
		dto.setLocalidade(entity.getCidade());
		dto.setNumero(entity.getNumero());
		dto.setComplemento(entity.getComplemento());
		dto.setUf(entity.getEstado());
		
		return dto;
	}
}
