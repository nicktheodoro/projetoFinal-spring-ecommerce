package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.dto.ProductDto;
import org.serratec.com.backend.ecommerce.exceptions.DataIntegrityViolationException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.ProductService;
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
@RequestMapping("/produto")
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAll(){
		return new ResponseEntity<List<ProductDto>>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getById(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<ProductDto>(service.getById(id), HttpStatus.OK);
	}
	@GetMapping("/nome")
	public ResponseEntity<List<ProductDto>> getByName(@RequestParam String nome) {
		return new ResponseEntity<List<ProductDto>>(service.getByName(nome), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ProductDto> create(@RequestBody ProductDto product) throws EntityNotFoundException {
		return new ResponseEntity<ProductDto>(service.create(product),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto category) throws EntityNotFoundException {
		return new ResponseEntity<ProductDto>(service.update(id, category), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		service.delete(id);

		return new ResponseEntity<String>("Categoria com id: " + id +" deletada com sucesso!", HttpStatus.OK);
	}
}
