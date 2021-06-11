package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteDto;
import org.serratec.com.backend.ecommerce.entities.dto.ClienteSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.ClienteException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.ClienteMapper;
import org.serratec.com.backend.ecommerce.mappers.EnderecoMapper;
import org.serratec.com.backend.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoService enderecoService;

	@Autowired
	PedidoService pedidoService;

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

	public ClienteSimplesDto create(ClienteDto clienteDto) throws EntityNotFoundException, ClienteException {
		if (this.findByCpf(clienteDto.getCpf()) != null
				|| clienteRepository.findByEmail(clienteDto.getEmail()).size() != 0
				|| clienteRepository.findByUsername(clienteDto.getUsername()).size() != 0) {
			throw new ClienteException("CPF ou Email ou UserName já cadastrado");
		} else {
			ClienteEntity clienteEntity = clienteMapper.toEntity(clienteDto);
			ClienteEntity savedClienteEntity = clienteRepository.save(clienteEntity);

			List<EnderecoEntity> listaEnderecosEntity = enderecoMapper.listaSimplficadaToEntity(enderecoService
					.criarPeloCliente(enderecoMapper.listToDto(clienteDto.getEnderecos()), savedClienteEntity.getId()));
			savedClienteEntity.setEnderecos(listaEnderecosEntity);

			return clienteMapper.toSimplesDto(savedClienteEntity);
		}
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

	public void delete(String cpf) throws EntityNotFoundException, ClienteException {

		if (this.findByCpf(cpf) != null) {
			if (pedidoService.getByCliente(this.findByCpf(cpf)).isEmpty()) {
				Long id = this.findByCpf(cpf).getId();
				enderecoService.deleteAll(this.findById(id));
				clienteRepository.deleteById(id);
			} else {
				throw new ClienteException("O cliente com cpf " + cpf + "possui pedidos vinculados. Favor verificar");
			}
		}
	}

	public ClienteEntity findById(Long id) throws EntityNotFoundException {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Cliente com id: " + id + " não encontrado."));
	}

	public ClienteEntity findByCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

}