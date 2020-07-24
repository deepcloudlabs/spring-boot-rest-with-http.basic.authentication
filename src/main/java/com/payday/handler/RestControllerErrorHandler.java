package com.payday.handler;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@RestControllerAdvice
@ControllerAdvice
public class RestControllerErrorHandler {

	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleValidationException(ConstraintViolationException ex) {
		var violations = ex.getConstraintViolations();
		return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("|"));
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining("|"));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleIllegalArgumentException(IllegalArgumentException e) {
		return e.getMessage();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public String handleExceptionException(RuntimeException e) {
		return e.getMessage();
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleAccessDeniedException(AccessDeniedException e) {
		return e.getMessage();
	}
}
