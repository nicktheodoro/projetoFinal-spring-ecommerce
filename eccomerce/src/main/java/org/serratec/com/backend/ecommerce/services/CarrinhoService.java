package org.serratec.com.backend.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;
import org.serratec.com.backend.ecommerce.entities.PedidoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CarrinhoDto;
import org.serratec.com.backend.ecommerce.exceptions.CarrinhoException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.exceptions.ProdutoException;
import org.serratec.com.backend.ecommerce.mappers.CarrinhoMapper;
import org.serratec.com.backend.ecommerce.mappers.PedidoMapper;
import org.serratec.com.backend.ecommerce.repositories.CarrinhoRepository;
import org.serratec.com.backend.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

	@Autowired
	CarrinhoRepository carrinhoRepository;

	@Autowired
	PedidoRepository pedidoRepository;

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

	private void atualizarQuantidade(PedidoEntity pedidoEntity, CarrinhoDto carrinhoDto)
			throws EntityNotFoundException {
		List<CarrinhoEntity> carrinhoEntity = this.findAll();

		for (CarrinhoEntity entity : carrinhoEntity) {
			if (carrinhoDto.getProduto().equals(carrinhoMapper.toDto(entity).getProduto())
					&& carrinhoDto.getPedido().equals(carrinhoMapper.toDto(entity).getPedido())) {
				entity.setQuantidade(carrinhoDto.getQuantidade());
				carrinhoRepository.save(entity);
			} else if (carrinhoDto.getPedido().equals(carrinhoMapper.toDto(entity).getPedido())) {
				CarrinhoDto novoCarrinho = new CarrinhoDto();

				novoCarrinho.setPedido(carrinhoDto.getPedido());
				novoCarrinho.setProduto(carrinhoDto.getProduto());
				novoCarrinho.setPreco(produtoService.findById(carrinhoDto.getProduto()).getPreco());
				novoCarrinho.setQuantidade(carrinhoDto.getQuantidade());

				carrinhoRepository.save(carrinhoMapper.toEntity(carrinhoDto));
			}
		}
	}

	public void adicionarProduto(List<CarrinhoDto> carrinhosDto) throws EntityNotFoundException {
		for (CarrinhoDto carrinhoDto : carrinhosDto) {
			PedidoEntity pedidoEntity = pedidoService.findById(carrinhoDto.getPedido());
			if (pedidoEntity != null) {
				this.atualizarQuantidade(pedidoEntity, carrinhoDto);
			}
		}

	}
}
