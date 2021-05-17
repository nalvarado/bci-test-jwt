package com.bci.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.bci.test.controller.UserController;
import com.bci.test.dto.UserRequestDTO;
import com.bci.test.dto.UserResponseDTO;
import com.bci.test.exception.AutenticationException;
import com.bci.test.exception.ErrorNegocioException;
import com.bci.test.exception.UserException;
import com.bci.test.jwt.JwtTokenUtil;
import com.bci.test.model.Users;
import com.bci.test.service.UserService;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
class BcitestApplicationTests {

	@InjectMocks
	private UserController controller;

	@Mock
	private UserService userMock;
	
	@Mock
	public JwtTokenUtil tokenMock;

	@Test
	public void CreateUser() throws AutenticationException, UserException, ErrorNegocioException {
		log.info("Validamos servicio de validacion de creacion user");
		ResponseEntity<UserResponseDTO> response;
		
		UserRequestDTO req = new UserRequestDTO();
		req.setName("prueba");
		req.setEmail("nalvaradov85@gmail.com");
		req.setPassword("kjnjkjkds98snjkJ");
		req.setLastLogin(new Date());
		req.setModified(new Date());
		req.setCreate(new Date());
		req.setPhones(new ArrayList<>());
				
		Users res = new Users();
		res.setId(1L);
		res.setActivate(Boolean.TRUE);
		
		when(userMock.createUser(req)).thenReturn(res);
		when(tokenMock.createToken(req.getEmail(),req.getPassword()) ).thenReturn("ssdsadewe");
		
		response = controller.createUser(req);
		
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		
		log.info("Fin de test unitario");

	}

}
