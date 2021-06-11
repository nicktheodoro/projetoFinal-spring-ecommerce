package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteDto;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.ClienteMapper;
import org.serratec.com.backend.ecommerce.mappers.EnderecoMapper;
import org.serratec.com.backend.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoService enderecoService;

	@Autowired
	ClienteMapper clienteMapper;

	@Autowired
	EnderecoMapper enderecoMapper;

	public List<ClienteDto> getAll() {
		return clienteRepository.findAll().stream().map(clienteMapper::toDto).collect(Collectors.toList());
	}

	public ClienteDto getById(Long id) throws EntityNotFoundException {
		return clienteMapper.toDto(this.findById(id));
	}

	public ClienteSimplesDto create(ClienteDto clienteDto) throws EntityNotFoundException {
		ClienteEntity clienteEntity = clienteMapper.toEntity(clienteDto);
		ClienteEntity savedClienteEntity = clienteRepository.save(clienteEntity);

		List<EnderecoEntity> listaEnderecosEntity = enderecoMapper.listaSimplficadaToEntity(enderecoService
				.criarPeloCliente(enderecoMapper.listToDto(clienteDto.getEnderecos()), savedClienteEntity.getId()));
		savedClienteEntity.setEnderecos(listaEnderecosEntity);

		return clienteMapper.toSimplesDto(savedClienteEntity);
	}

	public ClienteDto update(Long id, ClienteDto clienteUpdate) throws EntityNotFoundException {
		ClienteEntity clienteEntity = this.findById(id);

		clienteEntity.setUsername(clienteUpdate.getUsername());
		clienteEntity.setSenha(clienteUpdate.getSenha());
		clienteEntity.setNome(clienteUpdate.getNome());
		clienteEntity.setCpf(clienteUpdate.getCpf());
		clienteEntity.setTelefone(clienteUpdate.getTelefone());
		clienteEntity.setDataNascimento(clienteUpdate.getDataNascimento());

		return clienteMapper.toDto(clienteRepository.save(clienteEntity));
	}

	public void delete(Long id) throws EntityNotFoundException, DataIntegrityViolationException {

		if (this.findById(id) != null) {
			enderecoService.deleteAll(this.findById(id));
		}

		clienteRepository.deleteById(id);

	}

	public ClienteEntity findById(Long id) throws EntityNotFoundException {
		return clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

}