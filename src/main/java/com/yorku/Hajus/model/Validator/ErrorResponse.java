package com.yorku.Hajus.model.Validator;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
 
	private String error;
	private List<FieldErrorResource> fieldErrors;

    public ErrorResponse() { }

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

    public List<FieldErrorResource> getFieldErrors() { return fieldErrors; }

    public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}