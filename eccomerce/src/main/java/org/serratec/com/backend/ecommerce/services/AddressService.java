package org.serratec.com.backend.ecommerce.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.AddressEntity;
import org.serratec.com.backend.ecommerce.entities.dto.AddressDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.AddressMapper;
import org.serratec.com.backend.ecommerce.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService {

	@Autowired
	AddressRepository repository;

	@Autowired
	AddressMapper mapper;

	public List<AddressDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public AddressDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public AddressDto create(AddressDto dto) {
		AddressDto address = this.getCep(dto.getCep());
		address.setNumero(dto.getNumero());
		address.setComplemento(dto.getComplemento());

		return mapper.toDto(repository.save(mapper.toEntity(address)));
	}

	public AddressDto update(Long id, AddressDto addressUpdate) throws EntityNotFoundException {
		AddressEntity address = this.findById(id);
		address.setNumero(addressUpdate.getNumero());

		if (addressUpdate != null) {
			address.setComplemento(addressUpdate.getComplemento());
		}

		addressUpdate = this.getCep(addressUpdate.getCep());
		address.setCep(addressUpdate.getCep());
		address.setBairro(addressUpdate.getBairro());
		address.setCidade(addressUpdate.getLocalidade());
		address.setEstado(addressUpdate.getUf());
		address.setRua(addressUpdate.getLogradouro());

		return mapper.toDto(repository.save(address));
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

	public AddressEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	private AddressDto getCep(@PathVariable(name = "cep") String cep) {
		RestTemplate restTemplate = new RestTemplate();

		String uri = "http://viacep.com.br/ws/{cep}/json/";

		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);

		AddressDto address = restTemplate.getForObject(uri, AddressDto.class, params);

		return address;
	}
}
