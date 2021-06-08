package org.serratec.com.backend.ecommerce.repositories;

import java.util.List;

import org.serratec.com.backend.ecommerce.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long>{

	
	
	default List<AddressEntity> findAllById(Long clientId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}