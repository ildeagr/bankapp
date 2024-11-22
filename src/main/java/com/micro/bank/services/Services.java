package com.micro.bank.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.micro.bank.dto.LoginUser;
import com.micro.bank.dto.Persona;
import com.micro.bank.entities.User;
import com.micro.bank.exceptions.BadParametersException;
import com.micro.bank.repositories.userRepository;
import com.micro.bank.security.*;
import com.micro.bank.utils.Utils;


@Service
public class Services implements InterServicio{

	@Autowired
	private userRepository usuariodao;
	

	@Override
	public Persona createPersona(Persona persona) throws BadParametersException {
		Utils.validateNonEmptyParams(persona.getName(), "persona.name");
		Utils.validateNonEmptyParams(persona.getPassword(), "persona.password");
		Utils.validateNonEmptyParams(persona.getAddress(), "persona.address");
		Utils.validateNonEmptyParams(persona.getEmail(), "persona.email");
		Utils.validateNonEmptyParams(persona.getPhoneNumber(), "persona.phoneNumber");
		//Utils.validatePhoneNumber(persona.getPhoneNumber());
		
		Optional<User> usuarioByEmail = usuariodao.findByEmail(persona.getEmail());
		Optional<User> usuarioByPhone = usuariodao.findByPhoneNumber(persona.getPhoneNumber());
		
		

		
		if(usuarioByEmail.isEmpty() && usuarioByPhone.isEmpty()) {
			String uuid = Security.calculateUUID();
			String hashSha3 = null;
			User usuario = new User();
			
			try {
				hashSha3 = Security.calculateHash("SHA3-256", persona.getPassword());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			persona.setAccountNumber(uuid);
			persona.setHashedPassword(hashSha3);
			
			
			usuario.setName(persona.getName());
			usuario.setPassword(persona.getPassword());
			usuario.setAddress(persona.getAddress());
			usuario.setEmail(persona.getEmail());
			usuario.setPhoneNumber(persona.getPhoneNumber());
			usuario.setAccountNumber(persona.getAccountNumber());
			usuario.setHashedPassword(persona.getHashedPassword());
			
			usuariodao.save(usuario);
			
			usuarioByEmail = usuariodao.findByEmail(persona.getEmail());
			Long id = usuarioByEmail.map(User::getId).orElse(null);
			persona.setId(id);
			persona.setPassword("****");
			
		} 
		
		else {
			if(usuarioByPhone.isPresent()) {
				throw new BadParametersException("Phone number already exists.");
			}
		throw new BadParametersException("Email already exists");
		}
	return persona;
	}
	
	
	@Override
	public LoginUser loginClient(LoginUser loginuser) throws BadParametersException{
		Utils.validateNonEmptyParams(loginuser.getPassword(), "persona.password");
		Utils.validateNonEmptyParams(loginuser.getEmail(), "persona.email");
		
		User userdetails = new User();
		Optional<User> usuarioByEmail = usuariodao.findByEmail(loginuser.getEmail());
		
		userdetails.setId(usuarioByEmail.map(User::getId).orElse(null));
		userdetails.setName(usuarioByEmail.map(User::getName).orElse(null));
		userdetails.setPassword(usuarioByEmail.map(User::getPassword).orElse(null));
		userdetails.setAddress(usuarioByEmail.map(User::getAddress).orElse(null));
		userdetails.setEmail(usuarioByEmail.map(User::getEmail).orElse(null));
		userdetails.setPhoneNumber(usuarioByEmail.map(User::getPhoneNumber).orElse(null));
		userdetails.setAccountNumber(usuarioByEmail.map(User::getAccountNumber).orElse(null));
		userdetails.setHashedPassword(usuarioByEmail.map(User::getHashedPassword).orElse(null));
		
		
		
		if(usuarioByEmail.isEmpty()){
			 throw new BadParametersException("User not found for the given identifier: " + loginuser.getEmail() + ".");
		}
		else if(loginuser.getPassword().equals(userdetails.getPassword())) {
			userdetails.setToken(Jwt.generateToken(userdetails));
			loginuser.setToken(userdetails.getToken());
			
			usuariodao.save(userdetails);
		} 
		else {
			loginuser.setToken("1234");
			throw new BadParametersException("Bad credentials");
		}
	
		return loginuser;
	}
	

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>) usuariodao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Persona findByInfo (Long id) throws BadParametersException{
		
		User infouser = new User();
		Persona datos = new Persona();
		
		infouser = (User) usuariodao.findById(id).orElse(null);
		
		Boolean validatetoken = Jwt.validateToken(infouser.getToken(), infouser);
		
		if (validatetoken) {
			
			datos.setName(infouser.getName());
			datos.setEmail(infouser.getEmail());
			datos.setPhoneNumber(infouser.getPhoneNumber());
			datos.setAddress(infouser.getAddress());
			datos.setAccountNumber(infouser.getAccountNumber());
			datos.setHashedPassword(infouser.getHashedPassword());
			
			return datos;
		}
		else {
			throw new BadParametersException("Token not validated");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Persona findByAcount(Long id) throws BadParametersException {
		User infocount = new User();
		Persona datos = new Persona();
		
		infocount = (User) usuariodao.findById(id).orElse(null);
		
		Boolean validatetoken = Jwt.validateToken(infocount.getToken(), infocount);
		
		if (validatetoken) {
			
			datos.setAccountNumber(infocount.getAccountNumber());
			datos.setBalance(infocount.getHashedPassword());
			
			return datos;
		}
		else {
			throw new BadParametersException("Token not validated");
		}
	}
}


