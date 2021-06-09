package org.serratec.com.backend.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProductDto;
import org.serratec.com.backend.ecommerce.entities.dto.ProductOrderDto;
import org.serratec.com.backend.ecommerce.entities.dto.PurchaseDto;
import org.serratec.com.backend.ecommerce.entities.dto.PurchasesProductsDto;
import org.serratec.com.backend.ecommerce.enums.PurchasesStatus;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.ProductMapper;
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
	ProductMapper productMapper;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	PurchasesProductsService cartService;

	
	public PurchaseEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<PurchaseDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public PurchaseDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public PurchaseEntity create(PurchaseDto purchase) throws EntityNotFoundException {
		PurchaseEntity entity = mapper.toEntity(purchase);
		entity.setCliente(clientService.findById(purchase.getCliente()));

		purchase.setStatus(PurchasesStatus.NAO_FINALIZADO);

		purchase.setNumeroPedido(this.generateNumber());

		return repository.save(mapper.toEntity(purchase));
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

	public void delete(Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		try {
			if (this.findById(id) != null) {
				repository.deleteById(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Categoria com id: " + id + " está associada a um ou mais produtos, favor verificar");
		}

	}
	
	private String generateNumber() {
		String numeroPedido = "";
		Random number = new Random();
		for (int i = 1; i <= 8; i++) {
			numeroPedido = numeroPedido + number.nextInt(9);
		}
		return numeroPedido;
	}
	
	public PurchaseDto order(PurchaseDto purchase) throws EntityNotFoundException {
		Long idPedido = this.create(purchase).getId();
		
		List<ProductDto> produtos = new ArrayList<>();
		List<PurchasesProductsDto> carrinhos = new ArrayList<>();
		
		List<ProductOrderDto> productOrder = purchase.getProduto();

		for (ProductOrderDto productOrderDto : productOrder) {
			ProductDto dto = productService.getByName(productOrderDto.getNome());
			dto.setQuantidade(productOrderDto.getQuantidade());
			
			produtos.add(dto);
		}
		
		for (ProductDto dto : produtos) {
			PurchasesProductsDto carrinho= new PurchasesProductsDto();
			carrinho.setPreco(dto.getPreco());
			
			carrinho.setProduto(productService.findByName(dto.getNome()).getId());
			
			
			carrinho.setQuantidade(dto.getQuantidade());
			carrinho.setPedido(idPedido);
			carrinhos.add(carrinho);
		}
		
		cartService.create(carrinhos);
		
		
		PurchaseEntity entity = this.findById(idPedido);
		entity.setValorTotal(cartService.calcularTotal(idPedido));
		
		repository.save(entity);
		return mapper.toDto(entity);
	}
	
	
	public PurchaseDto updateOrder(Long id, List<ProductOrderDto> produtosCarrinho) throws EntityNotFoundException {
		
		List<PurchasesProductsDto> carrinhos = new ArrayList<>();
		List<ProductDto> produtos = new ArrayList<>();
		
		for (ProductOrderDto productOrderDto : produtosCarrinho) {
			ProductDto dto = productService.getByName(productOrderDto.getNome());
			dto.setQuantidade(productOrderDto.getQuantidade());
			
			produtos.add(dto);
		}
		
		for (ProductDto productDto : produtos) {
			PurchasesProductsDto produto = new PurchasesProductsDto();
			produto.setPedido(id);
			produto.setQuantidade(productDto.getQuantidade());
			produto.setProduto(productService.findByName(productDto.getNome()).getId());
			
			carrinhos.add(produto);
		}
		cartService.adicionarProduto(carrinhos);
		
		PurchaseEntity entity = this.findById(id);
		entity.setValorTotal(cartService.calcularTotal(id));
		
		repository.save(entity);
		
		return mapper.toDto(entity);	
	}
	
//	public PurchaseDto deletarProdutoOrder(Long id, List<ProductOrderDto> produtosCarrinho) throws EntityNotFoundException {
//		List<PurchasesProductsDto> carrinhos = new ArrayList<>();
//		List<ProductDto> produtos = new ArrayList<>();
//		
//		for (ProductOrderDto productOrderDto : produtosCarrinho) {
//			ProductDto dto = productService.getByName(productOrderDto.getNome());
//			dto.setQuantidade(productOrderDto.getQuantidade());
//			
//			produtos.add(dto);
//		}
//		
//		for (ProductDto productDto : produtos) {
//			PurchasesProductsDto produto = new PurchasesProductsDto();
//			produto.setPedido(id);
//			produto.setQuantidade(productDto.getQuantidade());
//			produto.setProduto(productService.findByName(productDto.getNome()).getId());
//			
//			carrinhos.add(produto);
//		}
//	}
}