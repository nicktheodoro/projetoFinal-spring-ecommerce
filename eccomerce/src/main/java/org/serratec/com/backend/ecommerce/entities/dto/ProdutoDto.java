package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.serratec.com.backend.ecommerce.entities.CarrinhoEntity;

public class ProdutoDto {

	@NotBlank
	@Size(max = 40)
	private String nome;

	@Size(max = 250)
	private String descricao;

	@NotNull
	private Double preco;

	@NotNull
	private Integer quantidadeEstoque;

	@NotNull
	private Long categoria;

	@NotNull
	private LocalDate dataCadastro = LocalDate.now();
	// Adicionar imagens
	// private String imagem;

	private List<CarrinhoEntity> carrinhos;

	private Integer quantidade;
	
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

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public List<CarrinhoEntity> getCarrinhos() {
		return carrinhos;
	}

	public void setCarrinhos(List<CarrinhoEntity> carrinhos) {
		this.carrinhos = carrinhos;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
