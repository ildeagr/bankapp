package com.micro.bank.controllers;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.micro.bank.dto.Persona;
import com.micro.bank.dto.Error;
import com.micro.bank.dto.PersonaResponse;
import com.micro.bank.entities.User;
import com.micro.bank.exceptions.BadParametersException;
import com.micro.bank.services.InterServicio;


@RestController
public class Controllers {
	

	@Autowired
	private InterServicio productoService;
	
	
	@GetMapping("/users")
	public List<User> findAll() {
		return (List<User>) productoService.findAll();
	} 


	@PostMapping("/users/register")
	public ResponseEntity<PersonaResponse> createClient(@RequestBody Persona persona){
		Persona newPersona = null;
		PersonaResponse personaResponse = new PersonaResponse();
		Error error = new Error();
		
		ResponseEntity<PersonaResponse> responseEntity;
		HttpStatus httpStatus = HttpStatus.OK;
		
		try {
			newPersona = productoService.createPersona(persona);
		} catch (BadParametersException e) {
			error.setName("BadParameter");
			error.setDescription(e.getMessage());
			error.setCode(400);
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		
		personaResponse.setPersona(newPersona);
		personaResponse.setError(error);
		responseEntity = new ResponseEntity<>(personaResponse, httpStatus);
		
	 return responseEntity;
	}
}