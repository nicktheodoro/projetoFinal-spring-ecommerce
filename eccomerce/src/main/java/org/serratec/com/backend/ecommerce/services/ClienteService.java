package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteDto;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.ClientMapper;
import org.serratec.com.backend.ecommerce.mappers.EnderecoMapper;
import org.serratec.com.backend.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;

	@Autowired
	EnderecoService enderecoService;

	@Autowired
	ClientMapper clientMapper;

	@Autowired
	EnderecoMapper addressMapper;

	public List<ClienteDto> getAll() {
		return repository.findAll().stream().map(clientMapper::toDto).collect(Collectors.toList());
	}

	public ClienteDto getById(Long id) throws EntityNotFoundException {
		return clientMapper.toDto(this.findById(id));
	}

	//verificar retorno null ClienteSimplesDto
	public ClienteSimplesDto create(ClienteDto dto) throws EntityNotFoundException {
		ClienteEntity entity = clientMapper.toEntity(dto);
		ClienteEntity savedEntity = repository.save(entity);

		List<EnderecoEntity> enderecos = addressMapper.listaSimplficadaToEntity(
				enderecoService.criarPeloCliente(addressMapper.listToDto(dto.getEnderecos()), savedEntity.getId()));
		savedEntity.setEnderecos(enderecos);
		
		return clientMapper.toSimplesDto(savedEntity);
	}

	public ClienteDto update(Long id, ClienteDto clientUpdate) throws EntityNotFoundException {
		ClienteEntity client = this.findById(id);

		client.setUsername(clientUpdate.getUsername());
		client.setSenha(clientUpdate.getSenha());
		client.setNome(clientUpdate.getNome());
		client.setCpf(clientUpdate.getCpf());
		client.setTelefone(clientUpdate.getTelefone());
		client.setDataNascimento(clientUpdate.getDataNascimento());

		return clientMapper.toDto(repository.save(client));
	}

	public void delete(Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		
		if (this.findById(id) != null) {
			enderecoService.deleteAll(this.findById(id));
		}
		
		repository.deleteById(id);

	}

	public ClienteEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

}