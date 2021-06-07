package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.serratec.com.backend.ecommerce.entities.ClientEntity;

public class PurchaseDto {

	@NotBlank(message = "{nome.not.blank}")
	private Long numeroPedido;

	@NotBlank(message = "{nome.not.blank}")
	private Double valorTotal;

	@NotNull
	private LocalDate dataPedido;

	@NotNull
	@FutureOrPresent
	private LocalDate dataEntrega;

	@NotNull
	private String status;

	@NotNull
	private ClientEntity cliente;

	public Long getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Long numeroPedido) {
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
