package com.hr.microservice.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleRequestParameterExceptions(final MethodArgumentNotValidException ex)
	{
		final Map<String, String> errors = new HashMap<String, String>();
		ex.getBindingResult().getFieldErrors().forEach(err -> {
			errors.put(err.getField(), err.getDefaultMessage());
		});
		return errors;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public AppError handle(final UserNotFoundException ex)
	{
		return new AppError(ex.getCode(), ex.getMessage());
	}
}
