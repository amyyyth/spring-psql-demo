package com.dbconnect.demodb.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailExistsException {
	
	private  Date timestamp;
	private String message;

	public EmailExistsException(String message) {
		super();
		this.setMessage(message);
		this.setTime(new Date());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTime() {
		return timestamp;
	}

	public void setTime(Date timestamp) {
		this.timestamp = timestamp;
	}

	
	
	

}
