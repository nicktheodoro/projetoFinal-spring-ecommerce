package org.serratec.com.backend.ecommerce.repositories;

import org.serratec.com.backend.ecommerce.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
	
	ClienteEntity findByCpf(String cpf);
	
}
