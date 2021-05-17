package com.bci.test.dto;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPrincipal implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idUsuario;

	@JsonIgnore
	private String dscEmail;
	private String fullName;
	private String username;
	private String password;
	private String token;
	private Date lastLogin;
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
