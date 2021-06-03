package org.serratec.com.backend.ecommerce.utilities;

import org.serratec.com.backend.ecommerce.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	private HttpHeaders header(Exception ex) {
		HttpHeaders header = new HttpHeaders();
		header.add("LIBRARY", "ECOMMERCE_V1");
		header.add("x-error-msg", ex.getMessage());
		
		return header; 
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handlerEntityNotFoundException(EntityNotFoundException ex){
		return ResponseEntity.notFound().headers(this.header(ex)).build();
				
	}
}
