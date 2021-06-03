package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.AddressEntity;
import org.serratec.com.backend.ecommerce.entities.dto.AddressDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.AddressMapper;
import org.serratec.com.backend.ecommerce.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	@Autowired
	AddressRepository repository;

	@Autowired
	AddressMapper mapper;

	private AddressEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}
	
	public List<AddressDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}
	
	public AddressDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}
	
	public AddressDto create(AddressDto dto) {
		return mapper.toDto(repository.save(mapper.toEntity(dto)));
	}
	
	public AddressDto update(Long id, AddressDto addressUpdate) throws EntityNotFoundException {
		AddressEntity address = this.findById(id);
		
		address.setCep(addressUpdate.getCep());
		address.setRua(addressUpdate.getRua());
		address.setBairro(addressUpdate.getBairro());
		address.setCidade(addressUpdate.getCidade());
		address.setNumero(addressUpdate.getNumero());
		address.setComplemento(addressUpdate.getComplemento());
		address.setEstado(addressUpdate.getEstado());
		
		return mapper.toDto(repository.save(address));
	}
	
	public String delete (Long id) {
		repository.deleteById(id);
		return "Deletado com sucesso";
	}
	
	
}
