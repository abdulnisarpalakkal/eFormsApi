package com.focowell.config.error;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice

public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	 @Override
	   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	       String error = "Malformed JSON request";
	       return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	   }

	   private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
	   
	   @ExceptionHandler(Exception.class)
	   protected ResponseEntity<Object> handleAllException(
	           Exception ex) {
	       ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
	       apiError.setMessage(ex.getMessage());
	       return buildResponseEntity(apiError);
	   }
	   
	   @ExceptionHandler(EntityNotFoundException.class)
	   protected ResponseEntity<Object> handleEntityNotFound(
	           EntityNotFoundException ex) {
	       ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
	       apiError.setMessage(ex.getMessage());
	       return buildResponseEntity(apiError);
	   }

	   @ExceptionHandler(NoSuchElementException.class)
	   protected ResponseEntity<Object> handleNoSuchElement(NoSuchElementException ex)
	   {
		   ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		   apiError.setMessage(ex.getMessage());
		   return buildResponseEntity(apiError);
	   }
	   
	   @ExceptionHandler(AlreadyExistsException.class)
	   protected ResponseEntity<Object> handleUserExists(AlreadyExistsException ex)
	   {
		   ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		   apiError.setMessage(ex.getMessage());
		   return buildResponseEntity(apiError);
	   }
	   //other exception handlers below
	   @Override
	   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	     	     
		   ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
//		   apiError.setMessage( ex.getBindingResult().getFieldError().getDefaultMessage());
		   apiError.setMessage(ex.getBindingResult()
			.getAllErrors().stream()
			.map(ObjectError::getDefaultMessage)
			.collect(Collectors.joining(",")));
		   return buildResponseEntity(apiError);
	   } 
}
