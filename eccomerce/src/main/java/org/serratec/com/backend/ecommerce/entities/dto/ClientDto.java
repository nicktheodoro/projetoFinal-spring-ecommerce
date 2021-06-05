package org.serratec.com.backend.ecommerce.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;
import org.serratec.com.backend.ecommerce.entities.AddressEntity;

public class ClientDto {

	@NotBlank(message = "{nome.not.blank}")
	@Size(min = 5, max = 120)
	@Email
	private String email;

	@NotBlank(message = "{nome.not.blank}")
	@Size(max = 32)
	private String username;

	@NotBlank(message = "{nome.not.blank}")
	@Size(min = 8, max = 128)
	private String senha;

	@NotBlank(message = "{nome.not.blank}")
	@Size(min = 3, max = 200)
	private String nome;

	@NotBlank(message = "{nome.not.blank}")
	@UniqueElements
	@CPF
	private String cpf;

	@NotBlank(message = "{nome.not.blank}")
	@Size(min = 10, max = 11)
	private String telefone;

	@NotNull
	@Past
	private LocalDate dataNascimento;

	@NotNull
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
