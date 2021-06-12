package org.serratec.com.backend.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;
import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CarrinhoDto;
import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.mappers.CarrinhoMapper;
import org.serratec.com.backend.ecommerce.mappers.PedidoMapper;
import org.serratec.com.backend.ecommerce.repositories.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

	@Autowired
	CarrinhoRepository carrinhoRepository;

	@Autowired
	CarrinhoMapper carrinhoMapper;

	@Autowired
	PedidoMapper pedidoMapper;

	@Autowired
	PedidoService pedidoService;

	@Autowired
	ProdutoService produtoService;

	public CarrinhoEntity findById(Long id) throws EntityNotFoundException {
		return carrinhoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}
	
	public List<CarrinhoEntity> findByProdutos(ProdutoEntity produto) throws CarrinhoException{
		if(carrinhoRepository.findByProdutos(produto).isEmpty()) {
			throw new CarrinhoException("Produto com nome: " + produto.getNome() + " não encontrado, favor verificar");
		}else {
			return carrinhoRepository.findByProdutos(produto);
		}
	}
	public List<CarrinhoDto> getAll() {
		return carrinhoRepository.findAll().stream().map(carrinhoMapper::toDto).collect(Collectors.toList());
	}

	public List<CarrinhoEntity> findAll() {

		return carrinhoRepository.findAll();
	}

	public CarrinhoDto getById(Long id) throws EntityNotFoundException {
		return carrinhoMapper.toDto(this.findById(id));
	}

	public void create(List<CarrinhoDto> listaCarrinhoDto) throws EntityNotFoundException, ProdutoException {

		for (CarrinhoDto carrinhoDto : listaCarrinhoDto) {
			produtoService.removerEstoque(carrinhoDto.getProduto(), carrinhoDto.getQuantidade());
			carrinhoRepository.save(carrinhoMapper.toEntity(carrinhoDto));
		}
	}

	public CarrinhoDto update(Long id, CarrinhoDto carrinhoUpdate) throws EntityNotFoundException {
		CarrinhoEntity carrinhoEntity = this.findById(id);
		carrinhoEntity.setPreco(carrinhoUpdate.getPreco());
		carrinhoEntity.setQuantidade(carrinhoUpdate.getQuantidade());

		return carrinhoMapper.toDto(carrinhoRepository.save(carrinhoEntity));
	}

	public void removerProdutoCarrinho(List<CarrinhoDto> listaCarrinhoDto)
			throws EntityNotFoundException, CarrinhoException, ProdutoException {
		if (listaCarrinhoDto.isEmpty()) {
			throw new CarrinhoException(
					"Para se deletar um produto da lista de seus pedidos é necessário informar o número do pedido e o nome do produto");
		}

		List<CarrinhoEntity> listaCarrinhoEntity = new ArrayList<>();

		for (CarrinhoDto carrinhoDto : listaCarrinhoDto) {
			listaCarrinhoEntity = carrinhoRepository.findByPedidos(pedidoService.findById(carrinhoDto.getPedido()));
		}

		if (listaCarrinhoEntity != null) {
			for (CarrinhoEntity carrinhoEntity : listaCarrinhoEntity) {
				for (CarrinhoDto carrinhoDto : listaCarrinhoDto) {
					if (carrinhoEntity.getProdutos().getId().equals(carrinhoDto.getProduto())) {
						produtoService.devolverEstoque(carrinhoDto.getProduto(), carrinhoEntity.getQuantidade());
						carrinhoRepository.deleteById(carrinhoEntity.getId());
					}
				}
			}
		}
	}

	public Double calcularTotal(Long pedidoId) throws EntityNotFoundException {
		List<CarrinhoEntity> carrinhosEntity = carrinhoRepository.findByPedidos(pedidoService.findById(pedidoId));
		Double total = 0D;
		for (CarrinhoEntity carrinhoEntity : carrinhosEntity) {
			total = total + (carrinhoEntity.getQuantidade() * carrinhoEntity.getPreco());
		}
		return total;
	}

	public void atualizarQuantidade(CarrinhoEntity carrinho)
			throws EntityNotFoundException, ProdutoException {
					
			produtoService.removerEstoque(carrinho.getProdutos().getId(), carrinho.getQuantidade());
			carrinhoRepository.save(carrinho);	
	}
	
	public void adicionarProdutoNoCarrinho(CarrinhoDto carrinho) throws EntityNotFoundException, ProdutoException {
		
			CarrinhoDto novoCarrinho = carrinho;
			novoCarrinho.setPreco(produtoService.findById(carrinho.getProduto()).getPreco());
			
			produtoService.removerEstoque(carrinho.getProduto(), carrinho.getQuantidade());
			carrinhoRepository.save(carrinhoMapper.toEntity(novoCarrinho));
	}
	
}