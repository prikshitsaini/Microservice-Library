package com.assignment.microservices.bookservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public class TypeNotAllowedException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;

		public TypeNotAllowedException(String message) {
			super(message);
			
		}
	}
	

