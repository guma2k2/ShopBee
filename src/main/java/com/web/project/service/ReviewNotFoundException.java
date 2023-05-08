package com.web.project.service;

public class ReviewNotFoundException extends Exception {

	private String message;

	public ReviewNotFoundException(String message) {
		this.message = message;
	}
	
}
