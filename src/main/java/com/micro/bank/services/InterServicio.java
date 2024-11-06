package com.micro.bank.services;

import java.util.List;

import com.micro.bank.dto.Persona;
import com.micro.bank.entities.User;
import com.micro.bank.exceptions.BadParametersException;

public interface InterServicio {

	Persona createPersona(Persona persona) throws BadParametersException;
	List<User> findAll();
		
}
