package org.serratec.com.backend.ecommerce.repositorys;

import org.serratec.com.backend.ecommerce.entitys.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long>{

}
