package org.serratec.com.backend.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.serratec.com.backend.ecommerce.entities.dto.PurchasesProductsDto;
import org.serratec.com.backend.ecommerce.exceptions.DataIntegrityViolationException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.PurchasesProductsService;
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
@RequestMapping("/pedidosProdutos") // alterar se necessário
public class PurchasesProductsController {

	@Autowired
	PurchasesProductsService service;
	
	@GetMapping
	public ResponseEntity<List<PurchasesProductsDto>> getAll(){
		return new ResponseEntity<List<PurchasesProductsDto>>(service.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PurchasesProductsDto> getById(@PathVariable Long id) throws EntityNotFoundException{
		return new ResponseEntity<PurchasesProductsDto>(service.getById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PurchasesProductsDto> create(@Valid @RequestBody PurchasesProductsDto dto){
		return new ResponseEntity<PurchasesProductsDto>(service.create(dto),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PurchasesProductsDto> update(@PathVariable Long id, @Valid @RequestBody PurchasesProductsDto dto) throws EntityNotFoundException{
		return new ResponseEntity<PurchasesProductsDto>(service.update(id, dto),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		service.delete(id);
		return new ResponseEntity<String>("Categoria com id: " + id +" deletada com sucesso!", HttpStatus.NO_CONTENT);
	}
}
