package com.bci.test.enums;

public enum KeyClaimsTokenEnum {
	
	ID_USUARIO("idUsuario"),
	FULL_NAME("fullName"),
	EMAIL("email"),
	USERNAME("username"),
	PICTURE("picture"),
	AUTHORITIES("authorities"),
	LAST_LOGIN("lastLogin"),
	TOKEN("token"),
	PERFILES("perfiles");
	
	private String descripcion;
	
	KeyClaimsTokenEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
