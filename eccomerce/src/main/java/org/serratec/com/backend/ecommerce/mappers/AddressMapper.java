package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.AddressEntity;
import org.serratec.com.backend.ecommerce.entities.dto.AddressDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
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

	public List<AddressDto> listToDto(List<AddressEntity> enderecos) {
		List<AddressDto> list = new ArrayList<>();
		for (AddressEntity entity : enderecos) {
			AddressDto dto = this.toDto(entity);
			list.add(dto);
		}
		return list;
	}

	public List<AddressEntity> listToEntity(List<AddressDto> enderecos) throws EntityNotFoundException {
		List<AddressEntity> list = new ArrayList<>();
		for (AddressDto dto : enderecos) {
			AddressEntity entity = this.toEntity(dto);
			list.add(entity);
		}
		return list;
	}
}
