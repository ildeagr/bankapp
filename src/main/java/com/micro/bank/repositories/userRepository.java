package com.micro.bank.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.micro.bank.entities.User;


public interface userRepository extends CrudRepository<User,Long>{

	Optional<User> findByEmail(String email);
	Optional<User> findByPhoneNumber(String phoneNumber);
	
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	List<User> findByEmailForLogin(String email);
}
