package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.serratec.com.backend.ecommerce.entities.EnderecoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CadastroUsuarioDto;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoDto;
import org.serratec.com.backend.ecommerce.entities.dto.EnderecoSimplesDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

	public EnderecoEntity toEntity(EnderecoDto dto) {
		EnderecoEntity entity = new EnderecoEntity();
		entity.setCep(dto.getCep());
		entity.setRua(dto.getLogradouro());
		entity.setBairro(dto.getBairro());
		entity.setCidade(dto.getLocalidade());
		entity.setNumero(dto.getNumero());
		entity.setComplemento(dto.getComplemento());
		entity.setEstado(dto.getUf());
		entity.setCliente(dto.getCliente());

		return entity;
	}

	public EnderecoDto toDto(EnderecoEntity entity) {
		EnderecoDto dto = new EnderecoDto();
		dto.setCep(entity.getCep());
		dto.setLogradouro(entity.getRua());
		dto.setBairro(entity.getBairro());
		dto.setLocalidade(entity.getCidade());
		dto.setNumero(entity.getNumero());
		dto.setComplemento(entity.getComplemento());
		dto.setUf(entity.getEstado());
		dto.setCliente(entity.getCliente());

		return dto;
	}

	public EnderecoSimplesDto toSimplificadoDto(EnderecoEntity endereco) {
		EnderecoSimplesDto dto = new EnderecoSimplesDto();
		dto.setCep(endereco.getCep());
		dto.setLogradouro(endereco.getRua());
		dto.setBairro(endereco.getBairro());
		dto.setLocalidade(endereco.getCidade());
		dto.setNumero(endereco.getNumero());
		dto.setComplemento(endereco.getComplemento());
		dto.setUf(endereco.getEstado());
		dto.setNomeCliente(endereco.getCliente().getNome());

		return dto;
	}

	public EnderecoEntity simplesDtoToEntity(EnderecoSimplesDto enderecoSimples) {
		EnderecoEntity entity = new EnderecoEntity();
		entity.setCep(enderecoSimples.getCep());
		entity.setRua(enderecoSimples.getLogradouro());
		entity.setBairro(enderecoSimples.getBairro());
		entity.setCidade(enderecoSimples.getLocalidade());
		entity.setNumero(enderecoSimples.getNumero());
		entity.setComplemento(enderecoSimples.getComplemento());
		entity.setEstado(enderecoSimples.getUf());

		return entity;
	}
	
	public CadastroUsuarioDto entityToCadastro(EnderecoEntity entity) {
		CadastroUsuarioDto dto = new CadastroUsuarioDto();
		dto.setBairro(entity.getBairro());
		dto.setCep(entity.getCep());
		dto.setComplemento(entity.getComplemento());
		dto.setLocalidade(entity.getCidade());
		dto.setLogradouro(entity.getRua());
		dto.setNumero(entity.getNumero());
		dto.setUf(entity.getEstado());
		
		return dto;
	}

	public List<EnderecoSimplesDto> toListaSimplficadoDto(List<EnderecoEntity> enderecos) {
		List<EnderecoSimplesDto> lista = new ArrayList<>();

		for (EnderecoEntity enderecoEntity : enderecos) {
			EnderecoSimplesDto dto = this.toSimplificadoDto(enderecoEntity);
			lista.add(dto);
		}

		return lista;
	}
	
	public List<CadastroUsuarioDto> toListCadastroDto(List<EnderecoEntity> enderecos) {
		List<CadastroUsuarioDto> lista = new ArrayList<>();

		for (EnderecoEntity enderecoEntity : enderecos) {
			CadastroUsuarioDto dto = this.entityToCadastro(enderecoEntity);
			lista.add(dto);
		}

		return lista;
	}

	public List<EnderecoDto> listToDto(List<EnderecoEntity> enderecos) {
		List<EnderecoDto> list = new ArrayList<>();

		for (EnderecoEntity entity : enderecos) {
			EnderecoDto dto = this.toDto(entity);
			list.add(dto);
		}

		return list;
	}

	public List<EnderecoEntity> listToEntity(List<EnderecoDto> enderecos) throws EntityNotFoundException {
		List<EnderecoEntity> list = new ArrayList<>();

		for (EnderecoDto dto : enderecos) {
			EnderecoEntity entity = this.toEntity(dto);
			list.add(entity);
		}

		return list;
	}

	public List<EnderecoEntity> listaSimplficadaToEntity(List<EnderecoSimplesDto> enderecoSimples) {
		List<EnderecoEntity> list = new ArrayList<>();

		for (EnderecoSimplesDto enderecoDto : enderecoSimples) {
			EnderecoEntity entity = this.simplesDtoToEntity(enderecoDto);
			list.add(entity);
		}

		return list;
	}

}
