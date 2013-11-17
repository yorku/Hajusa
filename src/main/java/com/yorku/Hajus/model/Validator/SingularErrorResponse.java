package com.yorku.Hajus.model.Validator;

public class SingularErrorResponse {

	private String error;
	private String explanation;
	
	public SingularErrorResponse(String error) {
		this.error = error;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	
}
