package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.serratec.com.backend.ecommerce.entities.dto.AddressDto;
import org.serratec.com.backend.ecommerce.exceptions.DataIntegrityViolationException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class AddressController {

	@Autowired
	AddressService service;

	@GetMapping
	public ResponseEntity<List<AddressDto>> getAll() {
		return new ResponseEntity<List<AddressDto>>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AddressDto> getOne(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<AddressDto>(service.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<List<AddressDto>> create(@Valid @RequestBody List<AddressDto> dto, Long id) throws EntityNotFoundException {
		return new ResponseEntity<List<AddressDto>>(service.create(dto, id), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AddressDto> update(@PathVariable Long id, @Valid @RequestBody AddressDto dto)
			throws EntityNotFoundException {
		return new ResponseEntity<AddressDto>(service.update(id, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		service.delete(id);
		return new ResponseEntity<String>("Endereço com " + id +" deletado com sucesso!", HttpStatus.OK);
	}

}
