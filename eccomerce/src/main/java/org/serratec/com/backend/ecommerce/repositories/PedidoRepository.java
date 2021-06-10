package org.serratec.com.backend.ecommerce.repositories;

import org.serratec.com.backend.ecommerce.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
	PedidoEntity findByNumeroPedido(String numeroPedido);
}
