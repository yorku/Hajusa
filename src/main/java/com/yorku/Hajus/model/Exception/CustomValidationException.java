package com.yorku.Hajus.model.Exception;

public class CustomValidationException extends Exception {
   
	private static final long serialVersionUID = 1535146953294067906L;

	public CustomValidationException(String message) {
        super(message);
    }
}