package com.bci.test.exception;

public class AutenticationException extends Exception {

	private static final long serialVersionUID = 7382770577561284219L;
	private String mensaje;

    public AutenticationException(String msg, Throwable e) {
        super(msg,e);
        this.setMensaje(msg);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}