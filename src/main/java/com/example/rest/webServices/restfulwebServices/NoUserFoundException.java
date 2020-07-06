package com.example.rest.webServices.restfulwebServices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserFoundException extends RuntimeException {

	public NoUserFoundException(String msg) {
		super(msg);
	}

}
