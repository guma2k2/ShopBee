package com.web.project.service;

public class HoaDonNotFoundExeption extends Exception {
    private String message ;
    public HoaDonNotFoundExeption(String message) {
        this.message = message;
    }
}
