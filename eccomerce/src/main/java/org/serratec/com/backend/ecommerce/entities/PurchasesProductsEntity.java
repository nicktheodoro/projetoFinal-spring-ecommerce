package org.serratec.com.backend.ecommerce.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUTO_PEDIDO")
public class PurchasesProductsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double preco;
	
	private Integer quantidade;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private ProductEntity produtos;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private PurchaseEntity pedidos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public ProductEntity getProdutos() {
		return produtos;
	}

	public void setProdutos(ProductEntity produtos) {
		this.produtos = produtos;
	}

	public PurchaseEntity getPedidos() {
		return pedidos;
	}

	public void setPedidos(PurchaseEntity pedidos) {
		this.pedidos = pedidos;
	}

}
