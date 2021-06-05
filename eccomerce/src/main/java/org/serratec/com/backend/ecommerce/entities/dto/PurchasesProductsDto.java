package org.serratec.com.backend.ecommerce.entities.dto;

import javax.validation.constraints.NotNull;

public class PurchasesProductsDto {

	@NotNull
	private Double preco;

	@NotNull
	private Integer quantidade;

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
}