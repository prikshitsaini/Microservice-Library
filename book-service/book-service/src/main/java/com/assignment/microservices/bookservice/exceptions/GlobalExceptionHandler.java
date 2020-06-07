package com.assignment.microservices.bookservice.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(BookNotFoundException.class)
	  public final ResponseEntity<ExceptionResponse> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<ExceptionResponse>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	
	@ExceptionHandler(UserNotFoundException.class)
	 public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
			ExceptionResponse errorDetails = new ExceptionResponse(new Date(), ex.getMessage(),
		        request.getDescription(false));
		    return new ResponseEntity<ExceptionResponse>(errorDetails, HttpStatus.NOT_FOUND);
		  }
	
	@ExceptionHandler(TypeNotAllowedException.class)
	 public final ResponseEntity<ExceptionResponse> handleTypeNotAllowedException(TypeNotAllowedException ex, WebRequest request) {
			ExceptionResponse errorDetails = new ExceptionResponse(new Date(), ex.getMessage(),
		        request.getDescription(false));
		    return new ResponseEntity<ExceptionResponse>(errorDetails, HttpStatus.NOT_FOUND);
		  }
	
	 @Override
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                                  HttpHeaders headers,
	                                                                  HttpStatus status, WebRequest request) {

	        Map<String, Object> body = new HashMap<>();
	        body.put("timestamp", new Date());
	        body.put("status", status.value());

	        List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());

	        body.put("errors", errors);

	        return new ResponseEntity<>(body, headers, status);

	    }
	
}
