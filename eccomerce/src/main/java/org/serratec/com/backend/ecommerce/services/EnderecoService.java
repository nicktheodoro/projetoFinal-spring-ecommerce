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
import org.serratec.com.backend.ecommerce.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

	@Autowired
	EnderecoRepository repository;

	@Autowired
	EnderecoMapper mapper;

	@Autowired
	ClienteService clientService;

	public List<EnderecoSimplesDto> getAll() {
		return mapper.toListaSimplficadoDto(repository.findAll());
	}
	
	public EnderecoEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public EnderecoSimplesDto getById(Long id) throws EntityNotFoundException {
		return mapper.toSimplificadoDto(this.findById(id));
	}

	public EnderecoSimplesDto create(EnderecoDto enderecoDto, Long idCliente) throws EntityNotFoundException {

		EnderecoDto endereco = this.setEndereco(enderecoDto, idCliente);

		return mapper.toSimplificadoDto(repository.save(mapper.toEntity(endereco)));
	}

	public List<EnderecoSimplesDto> criarPeloCliente(List<EnderecoDto> listaEndereco, Long idCliente)
			throws EntityNotFoundException {

		for (EnderecoDto enderecoDto : listaEndereco) {
			EnderecoDto endereco = this.setEndereco(enderecoDto, idCliente);
			repository.save(mapper.toEntity(endereco));
		}

		return mapper.toListaSimplficadoDto(repository.findAll());
	}

	public EnderecoDto update(Long id, EnderecoDto enderecoAtualizado) throws EntityNotFoundException {
		EnderecoEntity endereco = this.findById(id);
		endereco.setNumero(enderecoAtualizado.getNumero());

		if (enderecoAtualizado != null) {
			endereco.setComplemento(enderecoAtualizado.getComplemento());
		}

		enderecoAtualizado = this.getCep(enderecoAtualizado.getCep());
		endereco.setCep(enderecoAtualizado.getCep());
		endereco.setBairro(enderecoAtualizado.getBairro());
		endereco.setCidade(enderecoAtualizado.getLocalidade());
		endereco.setEstado(enderecoAtualizado.getUf());
		endereco.setRua(enderecoAtualizado.getLogradouro());

		return mapper.toDto(repository.save(endereco));
	}

	public void delete(Long id) throws EntityNotFoundException {
		
		if (this.findById(id) != null) {	
			repository.deleteById(id);
		}

	}
	
	public void deleteAll(ClienteEntity cliente) {
		List<EnderecoEntity> enderecos = repository.findByCliente(cliente);
		
		for (EnderecoEntity enderecoEntity : enderecos) {
			repository.delete(enderecoEntity);
		}
		
	}


	public EnderecoDto setEndereco(EnderecoDto enderecoDto, Long idCliente) throws EntityNotFoundException {
		EnderecoDto endereco = this.getCep(enderecoDto.getCep());
		endereco.setNumero(enderecoDto.getNumero());
		endereco.setComplemento(enderecoDto.getComplemento());
		endereco.setCliente(clientService.findById(idCliente));

		return endereco;
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
