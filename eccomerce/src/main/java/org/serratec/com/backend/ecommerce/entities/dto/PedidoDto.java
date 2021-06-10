package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;
import org.serratec.com.backend.ecommerce.enums.StatusCompra;

public class PedidoDto {

  private String numeroPedido;

  private Double valorTotal;

  private LocalDate dataPedido;

  @FutureOrPresent
  private LocalDate dataEntrega;

  private StatusCompra status;

  @NotNull
  private Long cliente;
	
	private List<CarrinhoEntity> carrinhos;
	
	private List<ProdutosPedidosDto> produto;
	
	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public StatusCompra getStatus() {
		return status;
	}

	public void setStatus(StatusCompra status) {
		this.status = status;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public List<CarrinhoEntity> getCarrinhos() {
		return carrinhos;
	}

	public void setCarrinhos(List<CarrinhoEntity> carrinhos) {
		this.carrinhos = carrinhos;
	}

	public List<ProdutosPedidosDto> getProduto() {
		return produto;
	}

	public void setProduto(List<ProdutosPedidosDto> produto) {
		this.produto = produto;
	}
	
}