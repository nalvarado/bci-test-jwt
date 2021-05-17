package com.bci.test.dto;

import java.io.Serializable;

import com.bci.test.model.Users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhoneRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer cityCode;
	private Integer countryCode;
	private Integer number;
	private Users user;

}
