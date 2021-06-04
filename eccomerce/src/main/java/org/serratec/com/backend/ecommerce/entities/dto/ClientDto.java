package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.serratec.com.backend.ecommerce.entities.AddressEntity;

public class ClientDto {

	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 5, max = 120)
	private String email;

	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(max = 32)
	private String username;

	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 8, max = 128)
	private String senha;

	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 3, max = 200)
	private String nome;

	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 11, max = 11)
	private String cpf;

	@NotEmpty(message = "Este campo não pode ser vazio")
	@Size(min = 10, max = 11)
	private String telefone;

	@NotNull
	private LocalDate dataNascimento;

	private AddressEntity endereco;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public AddressEntity getEndereco() {
		return endereco;
	}

	public void setEndereco(AddressEntity endereco) {
		this.endereco = endereco;
	}
}
