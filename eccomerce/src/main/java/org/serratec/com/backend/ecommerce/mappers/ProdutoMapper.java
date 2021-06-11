package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutoDto;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutosPedidosDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

	@Autowired
	CategoriaService categoriaService;

	public ProdutoEntity toEntity(ProdutoDto dto) throws EntityNotFoundException {
		ProdutoEntity entity = new ProdutoEntity();
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		entity.setPreco(dto.getPreco());
		entity.setQuantidadeEstoque(dto.getQuantidadeEstoque());
		entity.setDataCadastro(dto.getDataCadastro());
		entity.setCategoria(categoriaService.findById(dto.getCategoria()));

		return entity;
	}

	public ProdutoDto toDto(ProdutoEntity entity) {
		ProdutoDto dto = new ProdutoDto();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		dto.setPreco(entity.getPreco());
		dto.setQuantidadeEstoque(entity.getQuantidadeEstoque());
		dto.setDataCadastro(entity.getDataCadastro());
		dto.setCategoria(entity.getCategoria().getId());

		return dto;
	}

	public ProdutosPedidosDto toProdutosPedidos(ProdutoEntity entity) {
		ProdutosPedidosDto dto = new ProdutosPedidosDto();
		dto.setNome(entity.getNome());

		return dto;
	}

	public List<ProdutoDto> listToDto(List<ProdutoEntity> listaEntity) {
		List<ProdutoDto> listaDto = new ArrayList<>();
		for (ProdutoEntity entity : listaEntity) {
			ProdutoDto dto = this.toDto(entity);
			listaDto.add(dto);
		}
		return listaDto;
	}

	public List<ProdutoEntity> listToEntity(List<ProdutoDto> listaDto) throws EntityNotFoundException {
		List<ProdutoEntity> listaEntity = new ArrayList<>();
		for (ProdutoDto dto : listaDto) {
			ProdutoEntity entity = this.toEntity(dto);
			listaEntity.add(entity);
		}
		return listaEntity;
	}
}
