package org.serratec.com.backend.ecommerce.utilities;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice

public class ExceptionController extends ResponseEntityExceptionHandler {


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
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
		return ResponseEntity.badRequest().headers(this.header(e)).build();
    }
}
