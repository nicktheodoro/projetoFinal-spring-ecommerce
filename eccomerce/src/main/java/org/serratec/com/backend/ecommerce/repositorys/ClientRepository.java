package org.serratec.com.backend.ecommerce.repositorys;

import org.serratec.com.backend.ecommerce.entitys.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long>{

}
