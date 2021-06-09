package org.serratec.com.backend.ecommerce.repositories;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.PurchaseEntity;
import org.serratec.com.backend.ecommerce.entities.PurchasesProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasesProductsRepository extends JpaRepository<PurchasesProductsEntity, Long> {
	List<PurchasesProductsEntity> findByPedidos(PurchaseEntity pedidos);

}
