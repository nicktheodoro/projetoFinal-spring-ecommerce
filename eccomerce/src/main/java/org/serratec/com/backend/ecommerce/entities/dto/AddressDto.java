package org.serratec.com.backend.ecommerce.entities.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AddressDto {

	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 9 , max = 9)
	private String cep;
	
	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 3, max=120)
	private String rua;
	
	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 3, max=120)
	private String bairro;
	
	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 3, max=120)
	private String cidade;
	
	@NotEmpty(message = "Este campo não pode ser vazio")
	private String numero;
	
	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 3, max=120)
	private String complemento;
	
	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 3, max=120)
	private String estado;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
