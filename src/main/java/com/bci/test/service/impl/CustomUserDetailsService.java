package com.bci.test.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bci.test.dto.UserPrincipal;
import com.bci.test.model.Users;
import com.bci.test.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserPrincipal loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		try {
			Optional<Users> usuarioDTO = userRepository.findByEmail(usernameOrEmail);

			return createUserPrincipal(usuarioDTO.get());

		} catch (Exception e) {
			log.error("Error en loadUserByUsername", e);
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	public UserPrincipal loadUserById(Integer idUsuario) {
		Optional<Users> usuarioDTO = userRepository.findById(idUsuario);
		return createUserPrincipal(usuarioDTO.get());
	}

	/**
	 * Genera el Usuario a guardar en el token con toda su informacion y permisos
	 * asociados
	 * 
	 * @param usuarioDTO
	 * @return
	 */
	private UserPrincipal createUserPrincipal(Users user) {

		UserPrincipal userPrincipal = new UserPrincipal();

		userPrincipal.setIdUsuario(user.getId());
		userPrincipal.setDscEmail(user.getEmail());
		userPrincipal.setFullName(user.getName());
		userPrincipal.setPassword(user.getPassword());
		userPrincipal.setLastLogin(user.getLastLogin());

		return userPrincipal;
	}

}
