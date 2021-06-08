package org.serratec.com.backend.ecommerce.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUTO_PEDIDO")
public class PurchasesProductsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double preco;
	
	private Integer quantidade;
	
	private Long pedidos_id;
	
	private Long produtos_id;

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

	public Long getPedidos_id() {
		return pedidos_id;
	}

	public void setPedidos_id(Long pedidos_id) {
		this.pedidos_id = pedidos_id;
	}

	public Long getProdutos_id() {
		return produtos_id;
	}

	public void setProdutos_id(Long produtos_id) {
		this.produtos_id = produtos_id;
	}
}
