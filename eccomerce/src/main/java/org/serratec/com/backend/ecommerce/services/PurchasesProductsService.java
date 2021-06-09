package org.serratec.com.backend.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;
import org.serratec.com.backend.ecommerce.entities.dto.PurchasesProductsDto;
import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.PurchaseMapper;
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
	PurchaseMapper purchaseMapper;

	@Autowired
	PurchaseService purchaseService;

	@Autowired
	ProductService productService;

	public PurchasesProductsEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<PurchasesProductsDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public List<PurchasesProductsEntity> findAll() {

		return repository.findAll();
	}

	public PurchasesProductsDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public void create(List<PurchasesProductsDto> carrinhos) throws EntityNotFoundException {

		for (PurchasesProductsDto purchasesProductsDto : carrinhos) {
			repository.save(mapper.toEntity(purchasesProductsDto));
		}
	}

	public PurchasesProductsDto update(Long id, PurchasesProductsDto purchasesProductsUpdate)
			throws EntityNotFoundException {
		PurchasesProductsEntity purchasesProducts = this.findById(id);
		purchasesProducts.setPreco(purchasesProductsUpdate.getPreco());
		purchasesProducts.setQuantidade(purchasesProductsUpdate.getQuantidade());

		return mapper.toDto(repository.save(purchasesProducts));
	}
  
	public void removerProdutoCarrinho(List<PurchasesProductsDto> carrinhos)
			throws EntityNotFoundException, CarrinhoException {
		if (carrinhos.isEmpty()) {
			throw new CarrinhoException(
					"Para se deletar um produto da lista de seus pedidos é necessário informar o número do pedido e o nome do produto");
		}

		List<PurchasesProductsEntity> pedidosNoCarrinho = new ArrayList<>();

		for (PurchasesProductsDto carrinho : carrinhos) {
			pedidosNoCarrinho = repository.findByPedidos(purchaseService.findById(carrinho.getPedido()));
		}

		if (pedidosNoCarrinho != null) {
			for (PurchasesProductsEntity purchasesProductsEntity : pedidosNoCarrinho) {
				for (PurchasesProductsDto carrinho : carrinhos) {
					if (purchasesProductsEntity.getProdutos().getId().equals(carrinho.getProduto())) {
						repository.deleteById(purchasesProductsEntity.getId());
					}	
				}
			}
		}
	}

	public Double calcularTotal(Long id) throws EntityNotFoundException {
		List<PurchasesProductsEntity> pedidosNoCarrinho = repository.findByPedidos(purchaseService.findById(id));
		Double total = 0D;
		for (PurchasesProductsEntity purchasesProductsEntity : pedidosNoCarrinho) {
			total = total + (purchasesProductsEntity.getQuantidade() * purchasesProductsEntity.getPreco());
		}
		return total;
	}

	private void atualizarQuantidade(PurchaseEntity purchase, PurchasesProductsDto carrinho)
			throws EntityNotFoundException {
		List<PurchasesProductsEntity> produtosNoCarrinho = this.findAll();

		for (PurchasesProductsEntity entity : produtosNoCarrinho) {
			if (carrinho.getProduto().equals(mapper.toDto(entity).getProduto())
					&& carrinho.getPedido().equals(mapper.toDto(entity).getPedido())) {
				entity.setQuantidade(carrinho.getQuantidade());
				repository.save(entity);
			}
		}
	}

	public void adicionarProduto(List<PurchasesProductsDto> carrinhos) throws EntityNotFoundException {
		for (PurchasesProductsDto purchasesProductsDto : carrinhos) {
			PurchaseEntity purchase = purchaseService.findById(purchasesProductsDto.getPedido());
			if (purchase != null) {
				this.atualizarQuantidade(purchase, purchasesProductsDto);
			}
		}
	}
}
