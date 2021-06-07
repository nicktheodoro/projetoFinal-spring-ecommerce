package org.serratec.com.backend.ecommerce.repositories;

import org.serratec.com.backend.ecommerce.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
	public List<ProductEntity> findByNome(String nome);
}
