package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.serratec.com.backend.ecommerce.entities.CategoryEntity;

public class ProductDto {
	private final LocalDate dtCadastro = LocalDate.now();
	
	@NotEmpty
	@Size(max = 40)
	private String nome;
	
	@Size(max = 250)
	private String descricao;
	
	@NotEmpty
	private Double preco;
	
	@NotEmpty
	private Integer qtdEstoque;
	
	@NotEmpty
	private CategoryEntity categoria;
	
		//Adicionar imagens
	//private String imagem;
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Integer getQtdEstoque() {
		return qtdEstoque;
	}
	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public CategoryEntity getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoryEntity categoria) {
		this.categoria = categoria;
	}
	public LocalDate getDtCadastro() {
		return dtCadastro;
	}
}
