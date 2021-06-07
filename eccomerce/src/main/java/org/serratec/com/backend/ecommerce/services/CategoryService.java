package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.CategoryEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoryDto;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.CategoryMapper;
import org.serratec.com.backend.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repository;

	@Autowired
	CategoryMapper mapper;

	public CategoryEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<CategoryDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public CategoryDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}
	
	public List<CategoryDto> getByName(String nome) {
		return mapper.listToDto(repository.findByNome(nome));
	}
	
	
	public CategoryDto create(CategoryDto category){
		category.setNome(category.getNome().toLowerCase());
		repository.save(mapper.toEntity(category));			

		return category;
	}

	public CategoryDto update(Long id, CategoryDto categoryUpdate) throws EntityNotFoundException {
		CategoryEntity category = this.findById(id);
		category.setNome(categoryUpdate.getNome());

		if (categoryUpdate.getDescricao() != null) {
			category.setDescricao(categoryUpdate.getDescricao());
		}

		return mapper.toDto(repository.save(category));
	}

	public void delete(Long id) throws EntityNotFoundException, DataIntegrityViolationException {
		try {
			if (this.findById(id) != null) {
				repository.deleteById(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Categoria com id: " + id + " está associada a um ou mais produtos, favor verificar");
		}
	}
}
