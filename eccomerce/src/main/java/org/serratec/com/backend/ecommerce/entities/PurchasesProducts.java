package org.serratec.com.backend.ecommerce.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUTOS_PEDIDOS")
public class PurchasesProducts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer quantidade;
	
	private Double preco;
	
	@OneToOne
	private PurchaseEntity pedido;
	
	@OneToOne
	private ProductEntity produto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public PurchaseEntity getPedido() {
		return pedido;
	}

	public void setPedido(PurchaseEntity pedido) {
		this.pedido = pedido;
	}

	public ProductEntity getProduto() {
		return produto;
	}

	public void setProduto(ProductEntity produto) {
		this.produto = produto;
	}
}
