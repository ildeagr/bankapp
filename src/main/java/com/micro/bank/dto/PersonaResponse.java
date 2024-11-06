package com.micro.bank.dto;

public class PersonaResponse {
	
	Persona Persona;
	Error Error;
	
	public PersonaResponse() {
		super();
	}
	
	public Persona getPersona() {
		return Persona;
	}
	public void setPersona(Persona persona) {
		Persona = persona;
	}
	public Error getError() {
		return Error;
	}
	public void setError(Error error) {
		Error = error;
	}

}
