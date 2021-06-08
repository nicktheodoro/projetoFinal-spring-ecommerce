package org.serratec.com.backend.ecommerce.repositories;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
	public List<ProductEntity> findByCategoriaId(Long idCategoria);	
	public List<ProductEntity> findByNome(String nome);
}
