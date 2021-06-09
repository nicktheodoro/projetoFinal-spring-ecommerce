package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;

import org.serratec.com.backend.ecommerce.enums.PurchasesStatus;

public class PurchaseDto {

  private String numeroPedido;

  private Double valorTotal;

  private LocalDate dataPedido;

  @FutureOrPresent
  private LocalDate dataEntrega;

  private PurchasesStatus status;

  @NotNull
  private Long cliente;
	
	private List<PurchasesProductsEntity> carrinhos;
	
	private List<ProductOrderDto> produto;
	
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

	public PurchasesStatus getStatus() {
		return status;
	}

	public void setStatus(PurchasesStatus status) {
		this.status = status;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public List<PurchasesProductsEntity> getCarrinhos() {
		return carrinhos;
	}

	public void setCarrinhos(List<PurchasesProductsEntity> carrinhos) {
		this.carrinhos = carrinhos;
	}

	public List<ProductOrderDto> getProduto() {
		return produto;
	}

	public void setProduto(List<ProductOrderDto> produto) {
		this.produto = produto;
	}
	
	@Override
	public String toString() {
		return "PurchaseDto [numeroPedido=" + numeroPedido + ", valorTotal=" + valorTotal + ", dataPedido=" + dataPedido
				+ ", dataEntrega=" + dataEntrega + ", status=" + status + ", cliente=" + cliente + ", carrinhos="
				+ carrinhos + ", produto=" + produto + "]";
	}
	
}
