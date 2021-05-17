package com.bci.test.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

	private Long id;
	private Date created;
	private Date modified;
	private Date lastLogin;
	private String token;
	private boolean activate;

}
