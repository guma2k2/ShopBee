package com.web.project.service;

public class LoaiSanPhamNotFoundException extends Exception {

	private String message;

	public LoaiSanPhamNotFoundException(String message) {
		this.message = message;
	}
	
}
