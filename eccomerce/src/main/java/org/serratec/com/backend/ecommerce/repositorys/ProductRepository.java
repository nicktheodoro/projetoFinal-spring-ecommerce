package org.serratec.com.backend.ecommerce.repositorys;

import org.serratec.com.backend.ecommerce.entitys.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

}
