package org.serratec.com.backend.ecommerce.mappers;

import java.util.ArrayList;
import java.util.List;

import org.serratec.com.backend.ecommerce.entities.CategoryEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
	public CategoryEntity toEntity(CategoryDto dto) {
		CategoryEntity category = new CategoryEntity();
		category.setNome(dto.getNome());
		category.setDescricao(dto.getDescricao());

		return category;
	}

	public CategoryDto toDto(CategoryEntity category) {
		CategoryDto dto = new CategoryDto();
		
		dto.setNome(category.getNome());
		dto.setDescricao(category.getDescricao());
		
		return dto;
	}
	
	public List<CategoryDto> listToDto(List<CategoryEntity> categorias) {
		List<CategoryDto> list = new ArrayList<>();
		for (CategoryEntity entity : categorias) {
			CategoryDto dto = this.toDto(entity);
			list.add(dto);
		}
		return list;
	}
	public List<CategoryEntity> listToEntity(List<CategoryDto> categorias) {
		List<CategoryEntity> list = new ArrayList<>();
		for (CategoryDto dto : categorias) {
			CategoryEntity entity = this.toEntity(dto);
			list.add(entity);
		}
		return list;
	}
}
