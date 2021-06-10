package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.dto.CategoriaDto;
import org.serratec.com.backend.ecommerce.exceptions.CategoriaException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.CategoriaService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	CategoriaService service;

	@GetMapping
	public ResponseEntity<List<CategoriaDto>> getAll() {
		return new ResponseEntity<List<CategoriaDto>>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDto> getById(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<CategoriaDto>(service.getById(id), HttpStatus.OK);
	}

	@GetMapping("/nome")
	public ResponseEntity<CategoriaDto> getByName(@RequestParam String nome) {
		return new ResponseEntity<CategoriaDto>(service.getByName(nome), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CategoriaDto> create(@RequestBody CategoriaDto category) throws CategoriaException {
		return new ResponseEntity<CategoriaDto>(service.create(category), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDto> update(@PathVariable Long id, @RequestBody CategoriaDto category)
			throws EntityNotFoundException {
		return new ResponseEntity<CategoriaDto>(service.update(id, category), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException, CategoriaException {
		service.delete(id);
		return new ResponseEntity<String>("Categoria com id: " + id +" deletada com sucesso!", HttpStatus.OK);
	}
}
