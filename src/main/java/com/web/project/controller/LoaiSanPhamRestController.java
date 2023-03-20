package com.web.project.controller;

import com.web.project.service.LoaiSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoaiSanPhamRestController {

    @Autowired
    private LoaiSanPhamService service;

    @PostMapping("/loaisanpham/checkUnique")
    public String checkUnique(@Param("id") Integer id , @Param("ten") String ten ){
        return service.checkTenUnique(id , ten) ? "SUCCESSFUL" : "DUPDICATED";
    }
}
