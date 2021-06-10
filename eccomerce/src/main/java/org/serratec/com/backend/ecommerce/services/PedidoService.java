package org.serratec.com.backend.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.PedidoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutoDto;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutosPedidosDto;
import org.serratec.com.backend.ecommerce.entities.dto.PedidoDto;
import org.serratec.com.backend.ecommerce.entities.dto.CarrinhoDto;
import org.serratec.com.backend.ecommerce.enums.StatusCompra;
import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.mappers.ProdutoMapper;
import org.serratec.com.backend.ecommerce.mappers.PedidoMapper;
import org.serratec.com.backend.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository repository;

	@Autowired
	PedidoMapper mapper;

	@Autowired
	ProdutoMapper productMapper;

	@Autowired
	ClienteService clientService;

	@Autowired
	ProdutoService productService;

	@Autowired
	CarrinhoService cartService;

	public PedidoEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<PedidoDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public PedidoDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}

	public PedidoDto getByNumeroPedido(String numeroPedido) throws EntityNotFoundException {
		return mapper.toDto(repository.findByNumeroPedido(numeroPedido));
	}

	public PedidoEntity create(PedidoDto purchase) throws EntityNotFoundException {
		PedidoEntity entity = mapper.toEntity(purchase);
		entity.setCliente(clientService.findById(purchase.getCliente()));

		purchase.setStatus(StatusCompra.NAO_FINALIZADO);

		purchase.setNumeroPedido(this.generateNumber());

		return repository.save(mapper.toEntity(purchase));
	}

	public PedidoDto update(Long id, PedidoDto purchaseUpdate) throws EntityNotFoundException {
		PedidoEntity purchase = this.findById(id);
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

	public PedidoDto order(PedidoDto purchase) throws EntityNotFoundException, ProdutoException {
		Long idPedido = this.create(purchase).getId();

		List<ProdutoDto> produtos = new ArrayList<>();
		List<CarrinhoDto> carrinhos = new ArrayList<>();

		List<ProdutosPedidosDto> productOrder = purchase.getProduto();

		for (ProdutosPedidosDto productOrderDto : productOrder) {
			ProdutoDto dto = productService.getByName(productOrderDto.getNome());
			dto.setQuantidade(productOrderDto.getQuantidade());

			produtos.add(dto);
		}

		for (ProdutoDto dto : produtos) {
			CarrinhoDto carrinho = new CarrinhoDto();
			carrinho.setPreco(dto.getPreco());

			carrinho.setProduto(productService.findByName(dto.getNome()).getId());

			carrinho.setQuantidade(dto.getQuantidade());
			carrinho.setPedido(idPedido);
			carrinhos.add(carrinho);
		}

		cartService.create(carrinhos);

		PedidoEntity entity = this.findById(idPedido);
		entity.setValorTotal(cartService.calcularTotal(idPedido));

		repository.save(entity);
		return mapper.toDto(entity);
	}

	public PedidoDto updateOrder(String numeroPedido, List<ProdutosPedidosDto> produtosCarrinho)
			throws EntityNotFoundException {

		List<CarrinhoDto> carrinhos = new ArrayList<>();
		List<ProdutoDto> produtos = new ArrayList<>();

		for (ProdutosPedidosDto productOrderDto : produtosCarrinho) {
			ProdutoDto dto = productService.getByName(productOrderDto.getNome());
			dto.setQuantidade(productOrderDto.getQuantidade());

			produtos.add(dto);
		}

		for (ProdutoDto productDto : produtos) {
			CarrinhoDto produto = new CarrinhoDto();
			produto.setPedido(repository.findByNumeroPedido(numeroPedido).getId());
			produto.setQuantidade(productDto.getQuantidade());
			produto.setProduto(productService.findByName(productDto.getNome()).getId());

			carrinhos.add(produto);
		}
		cartService.adicionarProduto(carrinhos);

		PedidoEntity entity = repository.findByNumeroPedido(numeroPedido);
		entity.setValorTotal(cartService.calcularTotal(entity.getId()));

		repository.save(entity);

		return mapper.toDto(entity);
	}

	public PedidoDto deletarProdutoOrder(String numeroPedido, List<ProdutosPedidosDto> produtosCarrinho)
			throws EntityNotFoundException, CarrinhoException {
		List<CarrinhoDto> carrinhos = new ArrayList<>();
		List<ProdutoDto> produtos = new ArrayList<>();

		for (ProdutosPedidosDto productOrderDto : produtosCarrinho) {
			produtos.add(productService.getByName(productOrderDto.getNome()));
		}

		for (ProdutoDto productDto : produtos) {
			CarrinhoDto carrinho = new CarrinhoDto();
			carrinho.setPedido(repository.findByNumeroPedido(numeroPedido).getId());
			carrinho.setProduto(productService.findByName(productDto.getNome()).getId());

			carrinhos.add(carrinho);
		}

		cartService.removerProdutoCarrinho(carrinhos);

		PedidoEntity entity = repository.findByNumeroPedido(numeroPedido);
		entity.setValorTotal(cartService.calcularTotal(repository.findByNumeroPedido(numeroPedido).getId()));

		repository.save(entity);

		return mapper.toDto(entity);
	}
}