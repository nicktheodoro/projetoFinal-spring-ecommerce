package org.serratec.com.backend.ecommerce.entities.dto;

import org.serratec.com.backend.ecommerce.enums.StatusCompra;

public class CadastroPedidoDto {
	
	private String numeroPedido;

	private Long cliente;
	
	private Double valorTotal;

	private StatusCompra status;

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusCompra getStatus() {
		return status;
	}

	public void setStatus(StatusCompra status) {
		this.status = status;
	}

}
