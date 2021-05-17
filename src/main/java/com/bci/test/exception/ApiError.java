package com.bci.test.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

/**
 * @author nelson Alvarado
 */
@Getter
public class ApiError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	private List<String> errors;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
	
	ApiError(HttpStatus status, List<String> errors){
		this();
		this.status = status;
		this.errors = errors;
	}
	
	ApiError(HttpStatus status, String message){
		this();
		this.status = status;
		this.message = message;
	}

	ApiError(List<String> errors){
		this();
		this.errors = errors;
	}
	
	ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
}
