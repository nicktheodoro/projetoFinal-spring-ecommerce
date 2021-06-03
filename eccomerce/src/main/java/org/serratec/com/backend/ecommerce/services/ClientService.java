package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ClientEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ClientDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.ClientMapper;
import org.serratec.com.backend.ecommerce.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	ClientRepository repository;

	@Autowired
	ClientMapper mapper;

	private ClientEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " nÃ£o encontrado."));
	}

	public List<ClientDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public ClientDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public ClientDto create(ClientDto dto) {
		return mapper.toDto(repository.save(mapper.toEntity(dto)));
	}

	public ClientDto update(Long id, ClientDto clientUpdate) throws EntityNotFoundException {
		ClientEntity client = this.findById(id);

		client.setEmail(clientUpdate.getEmail());
		client.setUsername(clientUpdate.getUsername());
		client.setSenha(clientUpdate.getSenha());
		client.setNome(clientUpdate.getNome());
		client.setCpf(clientUpdate.getCpf());
		client.setTelefone(clientUpdate.getTelefone());
		client.setDataNascimento(clientUpdate.getDataNascimento());
		client.setEndereco(clientUpdate.getEndereco());

		return mapper.toDto(repository.save(client));
	}

	public String delete(Long id) {
		repository.deleteById(id);
		return "Deletado com sucesso";
	}
}