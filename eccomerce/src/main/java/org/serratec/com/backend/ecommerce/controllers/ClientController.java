package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.serratec.com.backend.ecommerce.entities.dto.ClientDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.ClientService;
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
@RequestMapping("/cliente")
public class ClientController {

	@Autowired
	ClientService service;

	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		return new ResponseEntity<List<ClientDto>>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> getById(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<ClientDto>(service.getById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientDto dto) throws EntityNotFoundException {
		return new ResponseEntity<ClientDto>(service.create(dto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @Valid @RequestBody ClientDto dto)
			throws EntityNotFoundException {
		return new ResponseEntity<ClientDto>(service.update(id, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		return new ResponseEntity<String>(service.delete(id), HttpStatus.NO_CONTENT);
	}
}