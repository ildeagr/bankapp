package com.micro.bank.services;

import java.util.List;

import com.micro.bank.dto.LoginUser;
import com.micro.bank.dto.Persona;
import com.micro.bank.entities.User;
import com.micro.bank.exceptions.BadParametersException;

public interface InterServicio {
	
	List<User> findAll();
	
	Persona createPersona(Persona persona) throws BadParametersException;
	LoginUser loginClient(LoginUser loginuser) throws BadParametersException;
	
	Persona findByInfo(Long id) throws BadParametersException;
	Persona findByAcount(Long id) throws BadParametersException;
	
}
