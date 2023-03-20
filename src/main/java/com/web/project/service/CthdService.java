package com.web.project.service;

import com.web.project.entity.ChiTietHoaDon;
import com.web.project.entity.HoaDon;
import com.web.project.entity.SanPham;
import com.web.project.repository.CthdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CthdService {

    @Autowired
    private CthdRepository repo;

    public ChiTietHoaDon save(int soLuong , Double gia , SanPham sanPham , HoaDon hoaDon){
        ChiTietHoaDon cthd = new ChiTietHoaDon(soLuong,gia,hoaDon , sanPham);
        return repo.save(cthd);
    }

    public List<ChiTietHoaDon> findByIdHoaDon(Integer maHoaDon){
        return  repo.findByIdHoaDon(maHoaDon);
    }
}
