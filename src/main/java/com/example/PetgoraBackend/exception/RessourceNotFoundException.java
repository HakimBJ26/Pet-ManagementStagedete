package com.example.PetgoraBackend.exception;

public class RessourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8352421701534980181L;

	public RessourceNotFoundException(String exception) {
		super(exception);
	}
}
