package com.web.project.repository;

import com.web.project.dto.DoanhThuTheoSanPham;
import com.web.project.dto.LichSuSanPham;
import com.web.project.entity.ChiTietHoaDon;
import com.web.project.entity.OrderStatus;
import com.web.project.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface CthdRepository extends JpaRepository<ChiTietHoaDon , Integer> {
    @Query("SELECT c FROM ChiTietHoaDon c WHERE c.hoaDon.id = ?1")
    public List<ChiTietHoaDon> findByIdHoaDon(Integer maHoaDon);

    @Query("SELECT NEW com.web.project.dto.DoanhThuTheoSanPham(c.sanPham.id, c.sanPham.ten , SUM(c.soLuong), SUM(c.gia) ) " +
            " FROM ChiTietHoaDon c" +
            " JOIN c.sanPham s " +
            " JOIN c.hoaDon h " +
            " WHERE DATE(c.hoaDon.ngayTao) BETWEEN ?1 AND ?2" +
            " GROUP BY c.sanPham.id ,c.sanPham.ten" +
            " ORDER BY sum(c.gia) DESC ")
    public Page<DoanhThuTheoSanPham> listDoanhThu(LocalDate fromDate , LocalDate toDate , Pageable pageable);

    @Query("SELECT SUM(c.soLuong) " +
            " FROM ChiTietHoaDon c " +
            " JOIN c.hoaDon " +
            " WHERE DATE(c.hoaDon.ngayTao) BETWEEN ?1 AND ?2")
    public Long countNumerOfProducts(LocalDate fromDate , LocalDate toDate);


    @Query("SELECT  COUNT(distinct s.loaiSanPham)" +
            " FROM ChiTietHoaDon c " +
            " JOIN c.sanPham s" +
            " JOIN c.hoaDon h" +
            " WHERE DATE(c.hoaDon.ngayTao) BETWEEN ?1 AND ?2")
    public Long soLoaiSp(LocalDate fromDate , LocalDate toDate);


    @Query("SELECT NEW com.web.project.dto.LichSuSanPham(c.sanPham.id, c.sanPham.ten , c.soLuong , c.gia ) " +
            " FROM ChiTietHoaDon c" +
            " JOIN c.hoaDon h " +
            " WHERE (h.ngayTao) = ?1" )
    public List<LichSuSanPham> listDoanhThuByExactDay(LocalDateTime date);

    @Query("SELECT c FROM ChiTietHoaDon c WHERE c.sanPham.id = ?1")
    public List<SanPham> listProductById(Integer idSp);


    @Query("SELECT COUNT(ct) FROM ChiTietHoaDon ct " +
            "JOIN OrderTrack ot ON ct.hoaDon.id = ot.order.id " +
            "WHERE ct.hoaDon.customer.email = ?1 AND ct.sanPham.id = ?2 AND ot.status = ?3")
    public long countByCustomerProductStatus(String email, Integer productId, OrderStatus status);
}
