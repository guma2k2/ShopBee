package com.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.project.service.NhanVienService;

@RestController
public class NhanVienRestController {

	@Autowired NhanVienService service ;
	
	@PostMapping("/nhanVien/check_email")
	public String checkEmail(@Param("id")Integer id ,@Param("email")String email) {
		return service.isEmailUnique(id ,email) ?   "SUCCESSFUL" : "DUPDICATED";
	}
}
