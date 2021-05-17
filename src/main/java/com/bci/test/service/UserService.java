package com.bci.test.service;

import com.bci.test.dto.UserRequestDTO;
import com.bci.test.exception.AutenticationException;
import com.bci.test.model.Users;

public interface UserService {

	Users createUser(UserRequestDTO user)throws AutenticationException;
	
	void updateUser(Users user, String token);
	
	Users updateUser(String user, String token);
	
}
