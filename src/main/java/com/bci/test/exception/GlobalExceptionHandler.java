package com.bci.test.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Control de excepciones globales y de ingreso
 * 
 * @author NelsonAlvaradoVidal
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * Proporciona manejo de excepciones a lo largo de este servicio.
	 *
	 * @param ex      The target exception
	 * @param request The current request
	 */
	@ExceptionHandler({ UserException.class, ContentNotAllowedException.class, MethodArgumentNotValidException.class })
	@Nullable
	public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();

		log.error("Manejo " + ex.getClass().getSimpleName() + " debido a " + ex.getMessage());

		if (ex instanceof UserException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			UserException unfe = (UserException) ex;
			return handleCommonException(unfe, headers, status, request);

		} else if (ex instanceof ContentNotAllowedException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			ContentNotAllowedException cnae = (ContentNotAllowedException) ex;
			return handleContentNotAllowedException(cnae, headers, status, request);

		} else if (ex instanceof MethodArgumentNotValidException) {
			BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
			List<FieldError> fieldErrors = result.getFieldErrors();

			// obtiene la validacion de los campos obligatorios
			List<String> errorMessage = new ArrayList<>();
			fieldErrors.forEach(f -> errorMessage.add(f.getField() + " " + f.getDefaultMessage()));
			HttpStatus status = HttpStatus.BAD_REQUEST;
			MethodArgumentNotValidException excp = (MethodArgumentNotValidException) ex;
			return handleCommonException(excp, headers, status, request, errorMessage);

		} else {
			if (log.isWarnEnabled()) {
				log.warn("Unknown exception type: " + ex.getClass().getName());
			}

			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleExceptionInternal(ex, null, headers, status, request);
		}
	}

	/**
	 * Personalizar la respuesta para handleNotFoundException.
	 *
	 * @param ex      The exception
	 * @param headers The headers to be written to the response
	 * @param status  The selected response status
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<ApiError> handleCommonException(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		log.error("[handleCommonException]HttpHeaders --> " + headers.toString());
		log.error("[handleCommonException]HttpStatus --> " + status.toString());
		log.error("[handleCommonException]error --> " + ex.getLocalizedMessage(), ex);
		if (errors.size() > 1)
			return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
		else
			return handleExceptionInternal(ex, new ApiError(status, errors.get(0)), headers, status, request);
	}

	/**
	 * Personalizar la respuesta para handleNotFoundException.
	 * 
	 * @param ex      The exception
	 * @param headers The headers to be written to the response
	 * @param status  The selected response status
	 * @param request
	 * @param errors
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<ApiError> handleCommonException(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request, List<String> errors) {
		log.error("[handleCommonException]HttpHeaders --> " + headers.toString());
		log.error("[handleCommonException]HttpStatus --> " + status.toString());
		// log.error("[handleCommonException]error --> " + ex.getLocalizedMessage(),
		// ex);
		return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
	}

	/**
	 * Personalizar la respuesta paraContentNotAllowedException.
	 *
	 * @param ex      The exception
	 * @param headers The headers to be written to the response
	 * @param status  The selected response status
	 * @return a {@code ResponseEntity} instance
	 */
	protected ResponseEntity<ApiError> handleContentNotAllowedException(ContentNotAllowedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errorMessages = ex.getErrors().stream()
				.map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
				.collect(Collectors.toList());
		log.error("[handleContentNotAllowedException]HttpHeaders --> " + headers.toString());
		log.error("[handleContentNotAllowedException]HttpStatus --> " + status.toString());
		log.error("[handleContentNotAllowedException]error --> " + ex.getLocalizedMessage(), ex);
		return handleExceptionInternal(ex, new ApiError(status, errorMessages), headers, status, request);
	}

	/**
	 * Personalizacion de "response body" de todos los tipos de excepción.
	 *
	 * <p>
	 * La implementación predeterminada establece el
	 * {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE} solicitar atributo y crea un
	 * {@link ResponseEntity} de lo dado body, headers, and status.
	 *
	 * @param ex      The exception
	 * @param body    The body for the response
	 * @param headers The headers for the response
	 * @param status  The response status
	 * @param request The current request
	 */
	protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, @Nullable ApiError body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		return new ResponseEntity<>(body, headers, status);
	}

}
