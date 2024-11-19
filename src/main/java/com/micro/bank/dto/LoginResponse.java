package com.micro.bank.dto;

public class LoginResponse {
	
	Error Error;
	private String token;
	
	
	public LoginResponse() {
		super();
	}
	public Error getError() {
		return Error;
	}
	public void setError(Error error) {
		Error = error;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
