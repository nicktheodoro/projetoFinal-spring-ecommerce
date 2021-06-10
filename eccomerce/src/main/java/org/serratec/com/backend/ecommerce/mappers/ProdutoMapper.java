package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ProdutoEntity;
import org.serratec.com.backend.ecommerce.entities.dto.ProdutoDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

	@Autowired
	CategoriaService service;
	
  public ProdutoEntity toEntity(ProdutoDto dto) throws EntityNotFoundException {
		ProdutoEntity product = new ProdutoEntity();
		product.setNome(dto.getNome());
		product.setDescricao(dto.getDescricao());
		product.setPreco(dto.getPreco());
		product.setQuantidadeEstoque(dto.getQuantidadeEstoque());
		product.setDataCadastro(dto.getDataCadastro());
		product.setCategoria(service.findById(dto.getCategoria()));
//		product.setImagem(dto.getImagem());

		return product;
	}

	public ProdutoDto toDto(ProdutoEntity product) {
		ProdutoDto dto = new ProdutoDto();
		dto.setNome(product.getNome());
		dto.setDescricao(product.getDescricao());
		dto.setPreco(product.getPreco());
		dto.setQuantidadeEstoque(product.getQuantidadeEstoque());
		dto.setDataCadastro(product.getDataCadastro());
		dto.setCategoria(product.getCategoria().getId());
//		product.setImagem(dto.getImagem());

		return dto;
	}

	public List<ProdutoDto> listToDto(List<ProdutoEntity> produtos) {
		List<ProdutoDto> list = new ArrayList<>();
		for (ProdutoEntity entity : produtos) {
			ProdutoDto dto = this.toDto(entity);
			list.add(dto);
		}
		return list;
	}
	public List<ProdutoEntity> listToEntity(List<ProdutoDto> produtos) throws EntityNotFoundException {
		List<ProdutoEntity> list = new ArrayList<>();
		for (ProdutoDto dto : produtos) {
			ProdutoEntity entity = this.toEntity(dto);
			list.add(entity);
		}
		return list;
	}
}
