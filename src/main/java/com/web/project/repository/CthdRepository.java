package com.web.project.repository;

import com.web.project.entity.ChiTietHoaDon;
import com.web.project.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CthdRepository extends JpaRepository<ChiTietHoaDon , Integer> {
    @Query("SELECT c FROM ChiTietHoaDon c WHERE c.hoaDon.id = ?1")
    public List<ChiTietHoaDon> findByIdHoaDon(Integer maHoaDon);
}
