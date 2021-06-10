package org.serratec.com.backend.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.PedidoEntity;
import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CarrinhoDto;
import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.PedidoMapper;
import org.serratec.com.backend.ecommerce.mappers.CarrinhoMapper;
import org.serratec.com.backend.ecommerce.repositories.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

	@Autowired
	CarrinhoRepository repository;

	@Autowired
	CarrinhoMapper mapper;

	@Autowired
	PedidoMapper purchaseMapper;

	@Autowired
	PedidoService purchaseService;

	@Autowired
	ProdutoService productService;

	public CarrinhoEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<CarrinhoDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public List<CarrinhoEntity> findAll() {

		return repository.findAll();
	}

	public CarrinhoDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public void create(List<CarrinhoDto> carrinhos) throws EntityNotFoundException {

		for (CarrinhoDto purchasesProductsDto : carrinhos) {
			repository.save(mapper.toEntity(purchasesProductsDto));
		}
	}

	public CarrinhoDto update(Long id, CarrinhoDto purchasesProductsUpdate)
			throws EntityNotFoundException {
		CarrinhoEntity purchasesProducts = this.findById(id);
		purchasesProducts.setPreco(purchasesProductsUpdate.getPreco());
		purchasesProducts.setQuantidade(purchasesProductsUpdate.getQuantidade());

		return mapper.toDto(repository.save(purchasesProducts));
	}
  
	public void removerProdutoCarrinho(List<CarrinhoDto> carrinhos)
			throws EntityNotFoundException, CarrinhoException {
		if (carrinhos.isEmpty()) {
			throw new CarrinhoException(
					"Para se deletar um produto da lista de seus pedidos é necessário informar o número do pedido e o nome do produto");
		}

		List<CarrinhoEntity> pedidosNoCarrinho = new ArrayList<>();

		for (CarrinhoDto carrinho : carrinhos) {
			pedidosNoCarrinho = repository.findByPedidos(purchaseService.findById(carrinho.getPedido()));
		}

		if (pedidosNoCarrinho != null) {
			for (CarrinhoEntity purchasesProductsEntity : pedidosNoCarrinho) {
				for (CarrinhoDto carrinho : carrinhos) {
					if (purchasesProductsEntity.getProdutos().getId().equals(carrinho.getProduto())) {
						repository.deleteById(purchasesProductsEntity.getId());
					}	
				}
			}
		}
	}

	public Double calcularTotal(Long id) throws EntityNotFoundException {
		List<CarrinhoEntity> pedidosNoCarrinho = repository.findByPedidos(purchaseService.findById(id));
		Double total = 0D;
		for (CarrinhoEntity purchasesProductsEntity : pedidosNoCarrinho) {
			total = total + (purchasesProductsEntity.getQuantidade() * purchasesProductsEntity.getPreco());
		}
		return total;
	}

	private void atualizarQuantidade(PedidoEntity purchase, CarrinhoDto carrinho)
			throws EntityNotFoundException {
		List<CarrinhoEntity> produtosNoCarrinho = this.findAll();

		for (CarrinhoEntity entity : produtosNoCarrinho) {
			if (carrinho.getProduto().equals(mapper.toDto(entity).getProduto())
					&& carrinho.getPedido().equals(mapper.toDto(entity).getPedido())) {
				entity.setQuantidade(carrinho.getQuantidade());
				repository.save(entity);
			}
		}
	}

	public void adicionarProduto(List<CarrinhoDto> carrinhos) throws EntityNotFoundException {
		for (CarrinhoDto purchasesProductsDto : carrinhos) {
			PedidoEntity purchase = purchaseService.findById(purchasesProductsDto.getPedido());
			if (purchase != null) {
				this.atualizarQuantidade(purchase, purchasesProductsDto);
			}
		}
	}
}
