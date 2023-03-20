package com.web.project.service;

public class SanPhamNotFoundException extends  Exception{
    private String message;

    public SanPhamNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
