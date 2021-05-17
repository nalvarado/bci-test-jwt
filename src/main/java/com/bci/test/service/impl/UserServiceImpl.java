package com.bci.test.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bci.test.dto.UserRequestDTO;
import com.bci.test.exception.AutenticationException;
import com.bci.test.model.Phones;
import com.bci.test.model.Users;
import com.bci.test.repository.PhonesRepository;
import com.bci.test.repository.UserRepository;
import com.bci.test.service.UserService;
import com.bci.test.utils.TransFormObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhonesRepository phoneRepository;

	@Autowired
	private TransFormObject transform;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Users createUser(UserRequestDTO userDto) throws AutenticationException {
		log.info("createUser: init creacion de usuario");
		Users user = new Users();

		try {
			user.setEmail(userDto.getEmail());
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			user.setName(userDto.getName());
			user.setLastLogin(new Date());
			user.setCreate(new Date());
			user.setModified(new Date());
			user.setActivate(Boolean.TRUE);

			userRepository.save(user);

			if (!userDto.getPhones().isEmpty()) {
				List<Phones> pho = transform.mapList(userDto.getPhones(), Phones.class);
				pho.stream().forEach((p) -> {
					p.setUser(user);
				});

				phoneRepository.saveAll(pho);
			}

		} catch (RuntimeException e) {
			throw new AutenticationException("Correo ya se encuentra registrado", e);
		}
		log.info("createUser: usuario creado correctamente");
		return user;
	}

	@Override
	public void updateUser(Users user, String token) {
		log.info("updateUser: init update de usuario");
		user.setLastLogin(new Date());
		user.setToken(token);
		this.userRepository.saveAndFlush(user);
		log.info("updateUser: update de usuario correctamente");

	}

	@Override
	public Users updateUser(String email, String token) {
		log.info("updateUser: init update de usuario");
		Users user = this.userRepository.findByEmail(email).get();

		user.setLastLogin(new Date());
		user.setToken(token);
		this.userRepository.saveAndFlush(user);
		log.info("updateUser: update de usuario correctamente");
		return user;
	}
}
