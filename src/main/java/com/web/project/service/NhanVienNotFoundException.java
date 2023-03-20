package com.web.project.service;

public class NhanVienNotFoundException extends Exception {

	private String message ;

	public NhanVienNotFoundException(String message) {
		this.message = message;
	}
	
}
