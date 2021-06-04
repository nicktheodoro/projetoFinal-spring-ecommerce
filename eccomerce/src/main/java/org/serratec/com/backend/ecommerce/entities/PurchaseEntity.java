package org.serratec.com.backend.ecommerce.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PEDIDOS")
public class PurchaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long numeroPedido;
	private Double valorTotal;
	private LocalDate dataPedido;
	private LocalDate dataEntrega;
	private String status;
	//private List<ProductEntity> listaDeProdutosDoPedido;

	@ManyToOne
	private ClientEntity cliente;

	@JsonIgnore
	@OneToOne
	private PurchasesProducts pedidos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

//	public List<ProductEntity> getListaDeProdutosDoPedido() {
//		return listaDeProdutosDoPedido;
//	}
//
//	public void setListaDeProdutosDoPedido(List<ProductEntity> listaDeProdutosDoPedido) {
//		this.listaDeProdutosDoPedido = listaDeProdutosDoPedido;
//	}

	public ClientEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClientEntity cliente) {
		this.cliente = cliente;
	}

	public PurchasesProducts getPedidos() {
		return pedidos;
	}

	public void setPedidos(PurchasesProducts pedidos) {
		this.pedidos = pedidos;
	}

	
}
