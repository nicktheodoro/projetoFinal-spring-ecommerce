package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.CategoriaEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoriaDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
	public CategoriaEntity toEntity(CategoriaDto dto) {
		CategoriaEntity category = new CategoriaEntity();
		category.setNome(dto.getNome());
		category.setDescricao(dto.getDescricao());

		return category;
	}

	public CategoriaDto toDto(CategoriaEntity category) {
		CategoriaDto dto = new CategoriaDto();
		
		dto.setNome(category.getNome());
		dto.setDescricao(category.getDescricao());
		
		return dto;
	}
	
	public List<CategoriaDto> listToDto(List<CategoriaEntity> categorias) {
		List<CategoriaDto> list = new ArrayList<>();
		for (CategoriaEntity entity : categorias) {
			CategoriaDto dto = this.toDto(entity);
			list.add(dto);
		}
		return list;
	}
	public List<CategoriaEntity> listToEntity(List<CategoriaDto> categorias) {
		List<CategoriaEntity> list = new ArrayList<>();
		for (CategoriaDto dto : categorias) {
			CategoriaEntity entity = this.toEntity(dto);
			list.add(entity);
		}
		return list;
	}
}
