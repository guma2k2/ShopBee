package com.web.project.service;

import com.web.project.dto.DoanhThuTheoSanPham;
import com.web.project.dto.LichSuSanPham;
import com.web.project.entity.ChiTietHoaDon;
import com.web.project.entity.HoaDon;
import com.web.project.entity.SanPham;
import com.web.project.repository.CthdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CthdService {

    @Autowired
    private CthdRepository repo;

    public ChiTietHoaDon save(int soLuong , Double gia , SanPham sanPham , HoaDon hoaDon) {
        ChiTietHoaDon cthd = new ChiTietHoaDon(soLuong,gia,hoaDon , sanPham);
        return repo.save(cthd);
    }

    public List<ChiTietHoaDon> findByIdHoaDon(Integer maHoaDon){
        return  repo.findByIdHoaDon(maHoaDon);
    }


    public List<DoanhThuTheoSanPham> findDoanhThuByThoiGian(LocalDate fromDate , LocalDate toDate) {
        Pageable pageable = PageRequest.of(0 , 10);
        List<DoanhThuTheoSanPham> list = repo.listDoanhThu(fromDate,toDate,pageable).getContent();
        return list;
    }
    public Long countProductByDate(LocalDate fromDate , LocalDate toDate){
        return repo.countNumerOfProducts(fromDate,toDate);
    }

    public Long countCategoryByDate(LocalDate fromDate ,LocalDate toDate){
        return repo.soLoaiSp(fromDate,toDate);
    }

    public List<LichSuSanPham> listByDay(LocalDateTime date){
        return repo.listDoanhThuByExactDay(date);
    }
}
