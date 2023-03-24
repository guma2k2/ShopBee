package com.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.HoaDon;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {


    @Query("SELECT COUNT(h) FROM HoaDon h WHERE DATE(h.ngayTao) BETWEEN ?1 AND ?2")
    public Long countByDate(LocalDate fromDate , LocalDate toDate);

    @Query("SELECT SUM(h.thanhTien) FROM HoaDon h WHERE DATE(h.ngayTao) BETWEEN ?1 AND ?2")
    public Double tongDoanhTheoNgay(LocalDate fromDate , LocalDate toDate);

    @Query("SELECT h FROM HoaDon h WHERE h.nhanVien.email = ?1")
    public List<HoaDon> findByEmail(String email );

}
