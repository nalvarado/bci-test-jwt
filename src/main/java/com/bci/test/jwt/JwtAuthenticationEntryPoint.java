package com.bci.test.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author nalva
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationEntryPoint.class);
    
    /**
     * Metodo que se llama cuando el token no es valido o el usuario no se encuentra autorizado para 
     * acceder a el metodo invocado
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
    	LOGGER.error("Responding with unauthorized error. Message - {}", e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Sorry, You're not authorized to access this resource.");
    }
}
