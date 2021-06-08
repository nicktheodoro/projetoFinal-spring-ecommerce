package org.serratec.com.backend.ecommerce.repositories;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	public List<CategoryEntity> findByNome(String nome);
}
