package com.bci.test.jwt;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bci.test.exception.ErrorNegocioException;

/**
 * @author Neal JwtTokenUtil.java
 */
@Component
public class JwtTokenUtil {

	private static final Logger LOGGER = LogManager.getLogger(JwtTokenUtil.class);

	@Autowired
	public AuthenticationManager authenticationManager;

	@Autowired
	public JwtTokenProvider tokenProvider;

	/**
	 * Crea el token cuando se loguea el usuario
	 * 
	 * @param usernameOrEmail
	 * @param password
	 * @return
	 * @throws ErrorNegocioException
	 */
	public String createToken(String usernameOrEmail, String password) throws ErrorNegocioException {

		try {
			LOGGER.info("createToken -> usernameOrEmail " + usernameOrEmail);

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));

			return setAuthentication(authentication);

		} catch (Exception e) {
			LOGGER.error("No es posible generar el token del usuario " + e.getMessage());
			throw new ErrorNegocioException(e.getMessage(), e);
		}
	}


	/**
	 * Setea la authentication en el context y genera el nuevo token
	 * 
	 * @param authentication
	 * @return
	 * @throws IOException
	 */
	private String setAuthentication(Authentication authentication) throws IOException {
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return tokenProvider.generateToken(authentication);
	}
}