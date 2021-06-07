package org.serratec.com.backend.ecommerce.repositories;

import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasesProductsRepository extends JpaRepository<PurchasesProductsEntity, Long> {

}
