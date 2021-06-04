package org.serratec.com.backend.ecommerce.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
//@Table(name= "PEDIDO")
public class PurchaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long nrPedido;
	
	private List<ProductEntity> listaProdutos;
	
	private Double valorTotal;
	
	private LocalDate dtPedido;
	
	private LocalDate dtEntrega;
	
	private String status;
	
	private ClientEntity cliente;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getNrPedido() {
		return nrPedido;
	}

	public void setNrPedido(Long nrPedido) {
		this.nrPedido = nrPedido;
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
