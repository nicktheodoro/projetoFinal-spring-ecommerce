package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ClientEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ClientDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.AddressMapper;
import org.serratec.com.backend.ecommerce.mappers.ClientMapper;
import org.serratec.com.backend.ecommerce.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	ClientRepository repository;

	@Autowired
	ClientMapper mapper;

	@Autowired
	AddressService service;
	
	@Autowired
	AddressMapper addressMapper;

	public List<ClientDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public ClientDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public ClientDto create(ClientDto dto) throws EntityNotFoundException {
		ClientEntity entity = mapper.toEntity(dto);
		service.create(addressMapper.listToDto(dto.getEnderecos()));
	
		return mapper.toDto(repository.save(entity));
	}

	public ClientDto update(Long id, ClientDto clientUpdate) throws EntityNotFoundException {
		ClientEntity client = this.findById(id);

		client.setUsername(clientUpdate.getUsername());
		client.setSenha(clientUpdate.getSenha());
		client.setNome(clientUpdate.getNome());
		client.setCpf(clientUpdate.getCpf());
		client.setTelefone(clientUpdate.getTelefone());
		client.setDataNascimento(clientUpdate.getDataNascimento());
		client.setEnderecos(clientUpdate.getEnderecos());

		return mapper.toDto(repository.save(client));
	}

	public void delete(Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		try {
			if (this.findById(id) != null) {
				repository.deleteById(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Categoria com id: " + id + " está associada a um ou mais produtos, favor verificar");
		}

	}

	public ClientEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

}