package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.serratec.com.backend.ecommerce.entities.CategoryEntity;

public class ProductDto {

	@NotNull
	private LocalDate dataCadastro = LocalDate.now();

	@NotEmpty
	@Size(max = 40)
	private String nome;

	@Size(max = 250)
	private String descricao;

	@NotEmpty
	private Double preco;

	@NotEmpty
	private Integer quantidadeEstoque;

	@NotEmpty
	private CategoryEntity categoria;

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

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

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public CategoryEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoryEntity categoria) {
		this.categoria = categoria;
	}
	
	// Adicionar imagens
	// private String imagem;
}
