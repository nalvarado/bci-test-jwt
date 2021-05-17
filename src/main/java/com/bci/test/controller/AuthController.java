package com.bci.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bci.test.dto.UserRequestDTO;
import com.bci.test.dto.UserResponseDTO;
import com.bci.test.exception.UserException;
import com.bci.test.jwt.JwtTokenUtil;
import com.bci.test.model.Users;
import com.bci.test.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	public JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userDto) throws UserException {
		try {
			String jwt = jwtTokenUtil.createToken(userDto.getEmail(), userDto.getPassword());

			Users user = this.userService.updateUser(userDto.getEmail(), jwt);

			UserResponseDTO dto = new UserResponseDTO();
			dto.setId(user.getId());
			dto.setToken(jwt);
			dto.setActivate(user.getActivate());
			dto.setCreated(user.getCreate());
			dto.setModified(user.getModified());
			dto.setLastLogin(user.getLastLogin());

			return new ResponseEntity<UserResponseDTO>(dto, HttpStatus.OK);

		} catch (Exception e) {
			log.error("No es posible autenticar al usuario" + e.getMessage());
			throw UserException.createWith("No es posible autenticar al usuario");
		}

	}
}
