package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import org.serratec.com.backend.ecommerce.entities.ClientEntity;
import org.serratec.com.backend.ecommerce.entities.ProductEntity;

public class PurchaseDto {
	
	@NotBlank
	private Long nrPedido;

	private List<ProductEntity> listaProdutos;
	
	@NotBlank
	private Double valorTotal;
	
	
	private LocalDate dtPedido;
	
	@FutureOrPresent
	private LocalDate dtEntrega;
	
	
	private String status;
	
	
	@NotBlank
	private ClientEntity cliente;

	

	public void setNrPedido(Long nrPedido) {
		this.nrPedido = nrPedido;
	}

	public Long getNrPedido() {
		return nrPedido;
	}

	public List<ProductEntity> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<ProductEntity> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(LocalDate dtPedido) {
		this.dtPedido = dtPedido;
	}

	public LocalDate getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(LocalDate dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ClientEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClientEntity cliente) {
		this.cliente = cliente;
	}
}
