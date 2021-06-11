package org.serratec.com.backend.ecommerce.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoDto;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.EnderecoMapper;
import org.serratec.com.backend.ecommerce.repositories.ClienteRepository;
import org.serratec.com.backend.ecommerce.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	EnderecoMapper enderecoMapper;

	@Autowired
	ClienteService clienteService;

	@Autowired
	ClienteRepository clienteRepository;

	public List<EnderecoSimplesDto> getAll() {
		return enderecoMapper.toListaSimplficadoDto(enderecoRepository.findAll());
	}

	public EnderecoEntity findById(Long id) throws EntityNotFoundException {
		return enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public EnderecoSimplesDto getById(Long id) throws EntityNotFoundException {
		return enderecoMapper.toSimplificadoDto(this.findById(id));
	}

	public EnderecoSimplesDto create(EnderecoDto enderecoDto, Long idCliente) throws EntityNotFoundException {

		EnderecoDto endDto = this.setEndereco(enderecoDto, idCliente);

		return enderecoMapper.toSimplificadoDto(enderecoRepository.save(enderecoMapper.toEntity(endDto)));
	}

	public List<EnderecoSimplesDto> criarPeloCliente(List<EnderecoDto> listaEnderecoDto, Long idCliente)
			throws EntityNotFoundException {

		for (EnderecoDto enderecoDto : listaEnderecoDto) {
			EnderecoDto endDto = this.setEndereco(enderecoDto, idCliente);
			enderecoRepository.save(enderecoMapper.toEntity(endDto));
		}

		enderecoMapper.toListaSimplficadoDto(enderecoRepository.findByCliente(clienteService.findById(idCliente)));

		return enderecoMapper
				.toListaSimplficadoDto(enderecoRepository.findByCliente(clienteService.findById(idCliente)));
	}

	public List<EnderecoDto> update(String username, List<EnderecoDto> enderecoAtualizado) // terminar update
			throws EntityNotFoundException {

		ClienteEntity cliente = clienteRepository.findByUsername(username);
		List<EnderecoEntity> listaEnderecos = cliente.getEnderecos();

		for (EnderecoEntity endereco : listaEnderecos) {

			for (EnderecoDto enderecoDto : enderecoAtualizado) {
				if (endereco.getCep().equals(enderecoDto.getCep())) {
					endereco.setNumero(enderecoDto.getNumero());
					endereco.setComplemento(enderecoDto.getComplemento());
					enderecoRepository.save(endereco);
					listaEnderecos.add(endereco);
				}
			}

			enderecoRepository
					.save(enderecoMapper.toEntity(this.setEndereco(enderecoMapper.toDto(endereco), cliente.getId())));
			listaEnderecos.add(endereco);
		}

		return enderecoMapper.listToDto(listaEnderecos);

	}

	public void delete(Long id) throws EntityNotFoundException {

		if (this.findById(id) != null) {
			enderecoRepository.deleteById(id);
		}

	}

	public void deleteAll(ClienteEntity clienteEntity) {
		List<EnderecoEntity> enderecosEntity = enderecoRepository.findByCliente(clienteEntity);

		for (EnderecoEntity enderecoEntity : enderecosEntity) {
			enderecoRepository.delete(enderecoEntity);
		}

	}

	public EnderecoDto setEndereco(EnderecoDto enderecoDto, Long idCliente) throws EntityNotFoundException {
		EnderecoDto endDto = this.getCep(enderecoDto.getCep());
		endDto.setNumero(enderecoDto.getNumero());
		endDto.setComplemento(enderecoDto.getComplemento());
		endDto.setCliente(clienteService.findById(idCliente));

		return endDto;
	}

	private EnderecoDto getCep(@PathVariable(name = "cep") String cep) {
		RestTemplate restTemplate = new RestTemplate();

		String uri = "http://viacep.com.br/ws/{cep}/json/";

		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);

		EnderecoDto endereco = restTemplate.getForObject(uri, EnderecoDto.class, params);

		return endereco;
	}
}
