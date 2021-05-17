package com.bci.test.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "es obligatorio")
	private String name;

	@NotBlank(message = "es obligatorio")
	@Email(message = "Correo ingresado tiene formato invalido")
	private String email;

	@NotBlank(message = "es obligatorio")
	@Pattern(regexp = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$", message = "Debe al menos tenes 1 digito, 1 mayuscula ")
	private String password;
	private Date create;
	private Date modified;
	private Date lastLogin;

	private List<PhoneRequestDTO> phones;

}
