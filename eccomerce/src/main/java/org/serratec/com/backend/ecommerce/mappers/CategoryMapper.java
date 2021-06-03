package org.serratec.com.backend.ecommerce.mappers;

import org.serratec.com.backend.ecommerce.entities.CategoryEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
	public CategoryEntity toModel(CategoryDto dto) {
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
}
