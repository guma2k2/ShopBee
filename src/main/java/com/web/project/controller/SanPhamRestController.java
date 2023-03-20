package com.web.project.controller;

import com.web.project.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SanPhamRestController {
    @Autowired
    private SanPhamService sanPhamService;

    @PostMapping("/sanpham/checkUnique")
    public String checkUnique(@Param("id") Integer id , @Param("ten") String ten ){
        return sanPhamService.checkTenUnique(ten, id) ? "SUCCESSFUL" : "DUPDICATED";
    }
}
