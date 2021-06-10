package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.CategoriaEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoriaDto;
import org.serratec.com.backend.ecommerce.exceptions.CategoriaException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.CategoryMapper;
import org.serratec.com.backend.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repository;

	@Autowired
	CategoryMapper mapper;
	
	@Autowired
	ProdutoService productService;

	public CategoriaEntity findById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<CategoriaDto> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public CategoriaDto getById(Long id) throws EntityNotFoundException {
		return mapper.toDto(this.findById(id));
	}
	
	public CategoriaDto getByName(String nome) {
		return mapper.toDto(repository.findByNome(nome));
	}
	
	
	public CategoriaDto create(CategoriaDto category) throws CategoriaException{
		if(category.getNome().isBlank()) {
			throw new CategoriaException("O nome da categoria é obrigatório");
		}else{
			category.setNome(category.getNome().toLowerCase());
			repository.save(mapper.toEntity(category));			
			
			return category;
		}
	}

	public CategoriaDto update(Long id, CategoriaDto categoryUpdate) throws EntityNotFoundException {
		CategoriaEntity category = this.findById(id);
		category.setNome(categoryUpdate.getNome());

		if (categoryUpdate.getDescricao() != null) {
			category.setDescricao(categoryUpdate.getDescricao());
		}

		return mapper.toDto(repository.save(category));
	}

	public void delete(Long id) throws EntityNotFoundException, CategoriaException {
		if (this.findById(id) != null) {
			if(productService.findByCategoriaId(id).isEmpty()){
				repository.deleteById(id);
			}else {
				throw new CategoriaException("Categoria com id: " + id + " já vinculada a um ou mais produtos, favor verificar!");
			}
		}
	}
}
