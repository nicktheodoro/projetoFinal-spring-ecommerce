package org.serratec.com.backend.ecommerce.repositorys;

import org.serratec.com.backend.ecommerce.entitys.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long>{

}
