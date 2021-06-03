package org.serratec.com.backend.ecommerce.repositorys;

import org.serratec.com.backend.ecommerce.entitys.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

}
