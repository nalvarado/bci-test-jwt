package com.bci.test.jwt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro que se ejecuta cada vez que se tiene una peticion http el cual
 * verifica el token del usuario para acceder a los servicios que se encuentran
 * privados por Spring Framework
 * 
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtTokenProvider tokenProvider;

	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						tokenProvider.getUserPrincipalFromToken(jwt), null, tokenProvider.getPermisos(jwt));

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			LOGGER.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
