package com.micro.bank.dto;

public class LoginUser {
	
	private String email;
	private String password;
	private String token;
	
	public LoginUser() {
	}
	
	public LoginUser(String name, String password, String token) {
		this.email = name;
		this.password = password;
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String name) {
		this.email = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}


