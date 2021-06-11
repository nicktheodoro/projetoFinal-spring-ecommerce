package org.serratec.com.backend.ecommerce.services;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.com.backend.ecommerce.entities.CategoriaEntity;
import org.serratec.com.backend.ecommerce.entities.dto.CategoriaDto;
import org.serratec.com.backend.ecommerce.exceptions.CategoriaException;
import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.serratec.com.backend.ecommerce.mappers.CategoriaMapper;
import org.serratec.com.backend.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	CategoriaMapper categoriaMapper;
	
	@Autowired
	ProdutoService produtoService;

	public CategoriaEntity findById(Long id) throws EntityNotFoundException {
		return categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " não encontrado."));
	}

	public List<CategoriaDto> getAll() {
		return categoriaRepository.findAll().stream().map(categoriaMapper::toDto).collect(Collectors.toList());
	}

	public CategoriaDto getById(Long id) throws EntityNotFoundException {
		return categoriaMapper.toDto(this.findById(id));
	}
	
	public CategoriaDto getByName(String nome) {
		return categoriaMapper.toDto(categoriaRepository.findByNome(nome));
	}
	
	
	public CategoriaDto create(CategoriaDto categoriaDto) throws CategoriaException{
		if(categoriaDto.getNome().isBlank()) {
			throw new CategoriaException("O nome da categoria é obrigatório");
		}else{
			categoriaDto.setNome(categoriaDto.getNome().toLowerCase());
			categoriaRepository.save(categoriaMapper.toEntity(categoriaDto));			
			
			return categoriaDto;
		}
	}

	public CategoriaDto update(Long id, CategoriaDto categoriaUpdate) throws EntityNotFoundException {
		CategoriaEntity categoriaEntity = this.findById(id);
		categoriaEntity.setNome(categoriaUpdate.getNome());

		if (categoriaUpdate.getDescricao() != null) {
			categoriaEntity.setDescricao(categoriaUpdate.getDescricao());
		}

		return categoriaMapper.toDto(categoriaRepository.save(categoriaEntity));
	}

	public void delete(Long id) throws EntityNotFoundException, CategoriaException {
		if (this.findById(id) != null) {
			if(produtoService.findByCategoriaId(id).isEmpty()){
				categoriaRepository.deleteById(id);
			}else {
				throw new CategoriaException("Categoria com id: " + id + " já vinculada a um ou mais produtos, favor verificar!");
			}
		}
	}
}
