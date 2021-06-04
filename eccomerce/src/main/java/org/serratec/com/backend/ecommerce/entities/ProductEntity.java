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
@Table(name = "PRODUTO")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Double preco;
	private Integer qtdEstoque;
	private LocalDate dtCadastro;

	@ManyToOne
	private CategoryEntity categoria;
	
	@JsonIgnore
	@OneToOne
	private PurchasesProducts produtos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public LocalDate getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(LocalDate dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public CategoryEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoryEntity categoria) {
		this.categoria = categoria;
	}

	public PurchasesProducts getProdutos() {
		return produtos;
	}

	public void setProdutos(PurchasesProducts produtos) {
		this.produtos = produtos;
	}

	
	// Adicionar imagens
	// private String imagem;

}
