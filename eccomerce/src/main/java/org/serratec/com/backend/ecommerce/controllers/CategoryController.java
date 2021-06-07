package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.serratec.com.backend.ecommerce.entities.dto.CategoryDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/categoria")
public class CategoryController {

	@Autowired
	CategoryService service;
	
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAll(){
		return new ResponseEntity<List<CategoryDto>>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getById(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<CategoryDto>(service.getById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryDto category){
		return new ResponseEntity<CategoryDto>(service.create(category), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto category) throws EntityNotFoundException {
		return new ResponseEntity<CategoryDto>(service.update(id, category), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException {
		service.delete(id);
		return new ResponseEntity<String>("Categoria com id: " + id +" deletada com sucesso!", HttpStatus.OK);
	}
}
