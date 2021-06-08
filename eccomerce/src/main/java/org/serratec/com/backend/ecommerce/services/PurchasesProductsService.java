package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchasesProductsDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.PurchasesProductsMapper;
import org.serratec.com.backend.ecommerce.repositories.PurchasesProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<PurchasesProductsDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public PurchasesProductsDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public PurchasesProductsEntity create(PurchasesProductsEntity entity) {
		return repository.save(entity);
	}

	public PurchasesProductsDto update(Long id, PurchasesProductsDto purchasesProductsUpdate)
			throws EntityNotFoundException {
		PurchasesProductsEntity purchasesProducts = this.findById(id);
		purchasesProducts.setPreco(purchasesProductsUpdate.getPreco());
		purchasesProducts.setQuantidade(purchasesProductsUpdate.getQuantidade());

		return mapper.toDto(repository.save(purchasesProducts));

	}

	public void delete(Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		try {
			if (this.findById(id) != null) {
				repository.deleteById(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"PedidoProduto com id: " + id + " está associada a um ou mais produtos, favor verificar");
		}

	}

//	public Double totalProduct(PurchaseEntity purchase, Integer quantidade) {
//		Double total = 0D;
//		List<ProductEntity> products = purchase.getProdutos();
//		for (ProductEntity productEntity : products) {
//			total = total + productEntity.getPreco() * quantidade;
//		}
//
//		purchaseservice.totalOrder(purchase, total);
//		return total;
//	}
}
