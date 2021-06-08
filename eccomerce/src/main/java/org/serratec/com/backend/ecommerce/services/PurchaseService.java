package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchaseDto;
import org.serratec.com.backend.ecommerce.enums.PurchasesStatus;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.PurchaseMapper;
import org.serratec.com.backend.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

	@Autowired
	PurchaseRepository repository;

	@Autowired
	PurchaseMapper mapper;

	@Autowired
	ClientService cService;

	@Autowired
	PurchasesProductsService pService;

	public PurchaseEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<PurchaseDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public PurchaseDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public PurchaseDto create(PurchaseDto purchase) throws EntityNotFoundException {
		PurchaseEntity entity = mapper.toEntity(purchase);
		entity.setCliente(cService.findById(purchase.getCliente()));

		purchase.setStatus(PurchasesStatus.NAO_FINALIZADO);

		purchase.setNumeroPedido(this.generateNumber());

		repository.save(mapper.toEntity(purchase));

		return purchase;
	}

	public PurchaseDto update(Long id, PurchaseDto purchaseUpdate) throws EntityNotFoundException {
		PurchaseEntity purchase = this.findById(id);
		if (purchase.getStatus().equals(PurchasesStatus.NAO_FINALIZADO)) {
			purchase.setValorTotal(purchaseUpdate.getValorTotal());
			purchase.setDataPedido(purchaseUpdate.getDataPedido());
			purchase.setDataEntrega(purchaseUpdate.getDataEntrega());

			return mapper.toDto(repository.save(purchase));

		}

		return null;
	}

	public void delete(Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		try {
			if (this.findById(id) != null) {
				repository.deleteById(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Pedido com id: " + id + " está associada a um ou mais produtos, favor verificar");
		}

	}

//	public void finishOrder(Long id) throws EntityNotFoundException {
//		PurchaseEntity purchase = this.findById(id);
//		purchase.setStatus(PurchasesStatus.FINALIZADO);
//		repository.save(purchase);
//	}
//
//	public void totalOrder(PurchaseEntity purchase, Double total) {
//		purchase.setValorTotal(purchase.getValorTotal() + total);
//	}

	public void order(PurchaseEntity purchase) throws EntityNotFoundException {
		this.create(mapper.toDto(purchase));
		List<ProductEntity> products = purchase.getProdutos();
		if (products.size() > 0) {
			for (ProductEntity entity : products) {
				PurchasesProductsEntity purchaseEntity= new PurchasesProductsEntity();
				purchaseEntity.setProdutos_id(entity.getId());
				purchaseEntity.setPedidos_id(purchase.getId());
				
				pService.create(purchaseEntity);
			}
		}

	}

	private String generateNumber() {
		String numeroPedido = "";
		Random number = new Random();
		for (int i = 1; i <= 8; i++) {
			numeroPedido += number.nextInt(9);
		}
		return numeroPedido;
	}
}
