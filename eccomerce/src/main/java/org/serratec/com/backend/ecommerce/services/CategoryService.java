package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.CategoryEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoryDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.CategoryMapper;
import org.serratec.com.backend.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repository;
	
	@Autowired
	CategoryMapper mapper;
	
	private CategoryEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}
	
	public List<CategoryDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList()); 
	}
	
	public CategoryDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}
	
	public CategoryDto create(CategoryDto category){
		repository.save(mapper.toModel(category));			
		
		return category;
	}
	
	public CategoryDto update(Long id, CategoryDto dto) throws EntityNotFoundException {
		CategoryEntity category = this.findById(id);
		
				
		if(dto.getNome() != null) {
			category.setNome(dto.getNome());	
		}
		if(dto.getDescricao() != null) {
			category.setDescricao(dto.getDescricao());
		}
	
		return mapper.toDto(repository.save(category));
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
