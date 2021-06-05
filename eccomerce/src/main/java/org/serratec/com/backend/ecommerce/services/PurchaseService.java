package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchaseDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.PurchaseMapper;
import org.serratec.com.backend.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

	@Autowired
	PurchaseRepository repository;

	@Autowired
	PurchaseMapper mapper;

	private PurchaseEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<PurchaseDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public PurchaseDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public PurchaseDto create(PurchaseDto purchase) {
		repository.save(mapper.toModel(purchase));
		return purchase;
	}

	public PurchaseDto update(Long id, PurchaseDto purchaseUpdate) throws EntityNotFoundException {
		PurchaseEntity purchase = this.findById(id);
		purchase.setNumeroPedido(purchaseUpdate.getNumeroPedido());
		purchase.setValorTotal(purchaseUpdate.getValorTotal());
		purchase.setDataPedido(purchaseUpdate.getDataPedido());
		purchase.setDataEntrega(purchaseUpdate.getDataEntrega());
		purchase.setStatus(purchaseUpdate.getStatus());

		return mapper.toDto(repository.save(purchase));
	}

	public void delete(Long id) throws EntityNotFoundException {
		if (this.findById(id) != null) {
			repository.deleteById(id);
		}
	}
}
