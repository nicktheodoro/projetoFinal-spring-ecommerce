package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchasesProductsDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.PurchasesProductsMapper;
import org.serratec.com.backend.ecommerce.repositories.PurchasesProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchasesProductsService {
	
	@Autowired
	PurchasesProductsRepository repository;
	
	@Autowired
	PurchasesProductsMapper mapper;
	
	@Autowired
	PurchaseService purchaseservice;
	
	@Autowired
	ProductService productservice;
	
	public PurchasesProductsEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(()->new EntityNotFoundException(id + " não encontrado."));
	}
	
	public List <PurchasesProductsDto> getAll(){
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}
	
	public PurchasesProductsDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}
	
	public PurchasesProductsDto create(PurchasesProductsDto dto) {
		return mapper.toDto(repository.save(mapper.toEntity(dto)));
	}
	
	public PurchasesProductsDto update(Long id, PurchasesProductsDto purchasesProductsUpdate ) throws EntityNotFoundException {
		PurchasesProductsEntity purchasesProducts = this.findById(id);
		purchasesProducts.setPreco(purchasesProductsUpdate.getPreco());
		purchasesProducts.setQuantidade(purchasesProductsUpdate.getQuantidade());
		
		return mapper.toDto(repository.save(purchasesProducts));
		
	}
	
	public String delete(Long id) {
		repository.deleteById(id);
		return "Deletado com sucesso";
	}
}
