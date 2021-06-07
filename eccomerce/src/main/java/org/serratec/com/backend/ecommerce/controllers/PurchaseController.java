package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.dto.PurchaseDto;
import org.serratec.com.backend.ecommerce.exceptions.DataIntegrityViolationException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.PurchaseService;
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
@RequestMapping("/pedidos")
public class PurchaseController {

	@Autowired
	PurchaseService service;
	
	@GetMapping
	public ResponseEntity<List<PurchaseDto>> getAll(){
		return new ResponseEntity<List<PurchaseDto>>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PurchaseDto> getById(@PathVariable Long id) throws EntityNotFoundException {
		return new ResponseEntity<PurchaseDto>(service.getById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PurchaseDto> create(@RequestBody PurchaseDto purchase) {
		return new ResponseEntity<PurchaseDto>(service.create(purchase),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PurchaseDto> update(@PathVariable Long id, @RequestBody PurchaseDto purchase) throws EntityNotFoundException {
		return new ResponseEntity<PurchaseDto>(service.update(id, purchase), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		service.delete(id);
		return new ResponseEntity<String>("Categoria com id: " + id +" deletada com sucesso!", HttpStatus.NO_CONTENT);
	}
}
