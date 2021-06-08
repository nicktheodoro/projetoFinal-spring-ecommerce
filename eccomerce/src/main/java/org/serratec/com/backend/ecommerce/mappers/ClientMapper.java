package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.ClientEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

	public ClientEntity toEntity(ClientDto dto) {
		ClientEntity entity = new ClientEntity();
		entity.setEmail(dto.getEmail());
		entity.setUsername(dto.getUsername());
		entity.setSenha(dto.getSenha());
		entity.setNome(dto.getNome());
		entity.setCpf(dto.getCpf());
		entity.setTelefone(dto.getTelefone());
		entity.setDataNascimento(dto.getDataNascimento());
		entity.setEnderecos(dto.getEnderecos());

		return entity;
	}

	public ClientDto toDto(ClientEntity entity) {
		ClientDto dto = new ClientDto();
		dto.setEmail(entity.getEmail());
		dto.setUsername(entity.getUsername());
		dto.setSenha(entity.getSenha());
		dto.setNome(entity.getNome());
		dto.setCpf(entity.getCpf());
		dto.setTelefone(entity.getTelefone());
		dto.setDataNascimento(entity.getDataNascimento());
		dto.setEnderecos(entity.getEnderecos());

		return dto;
	}
}
