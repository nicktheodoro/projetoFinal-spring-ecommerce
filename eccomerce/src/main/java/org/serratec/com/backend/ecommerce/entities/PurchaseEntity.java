package org.serratec.com.backend.ecommerce.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.serratec.com.backend.ecommerce.enums.PurchasesStatus;

@Entity
@Table(name = "PEDIDOS")
public class PurchaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroPedido;
	private Double valorTotal;
	private LocalDate dataPedido;
	private LocalDate dataEntrega;
	private PurchasesStatus status;

	@ManyToOne
	private ClientEntity cliente;

	@OneToMany(mappedBy="pedidos")    
	private List<PurchasesProductsEntity> carrinhos;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public ClientEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClientEntity cliente) {
		this.cliente = cliente;
	}

	public List<PurchasesProductsEntity> getCarts() {
		return carrinhos;
	}

	public void setCarts(List<PurchasesProductsEntity> carts) {
		this.carrinhos = carts;
	}

}
