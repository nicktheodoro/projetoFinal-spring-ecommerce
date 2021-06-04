package org.serratec.com.backend.ecommerce.exceptions;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 2372270958386916638L;

	private String msg;

	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String message) {
		super(message);
		this.msg = message;
	}

	public String getMsg() {
		return msg;
	}
}
