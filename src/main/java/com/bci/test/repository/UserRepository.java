package com.bci.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bci.test.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	
	
	Optional<Users> findByEmail(String email);
}
