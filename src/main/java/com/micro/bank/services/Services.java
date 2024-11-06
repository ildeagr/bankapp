package com.micro.bank.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.bank.dto.Persona;
import com.micro.bank.entities.User;
import com.micro.bank.exceptions.BadParametersException;
import com.micro.bank.repositories.userRepository;
import com.micro.bank.security.Security;
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
	public List<User> findAll() {
		return (List<User>) usuariodao.findAll();
	}
	
}