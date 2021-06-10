package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.serratec.com.backend.ecommerce.entities.dto.EnderecoDto;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.DataIntegrityViolationException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.EnderecoService;
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
public class EnderecoController {

	@Autowired
	EnderecoService service;

	@GetMapping
	public ResponseEntity<List<EnderecoSimplesDto>> getAll() {
		return new ResponseEntity<List<EnderecoSimplesDto>>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EnderecoSimplesDto> getOne(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<EnderecoSimplesDto>(service.getById(id), HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<EnderecoSimplesDto> create(@Valid @RequestBody EnderecoDto dto, @PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<EnderecoSimplesDto>(service.create(dto, id), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EnderecoDto> update(@PathVariable Long id, @Valid @RequestBody EnderecoDto dto)
			throws EntityNotFoundException {
		return new ResponseEntity<EnderecoDto>(service.update(id, dto), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		service.delete(id);
		return new ResponseEntity<String>("Endereço deletado com sucesso!", HttpStatus.OK);
	}

}
