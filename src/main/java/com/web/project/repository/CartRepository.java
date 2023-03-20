package com.web.project.repository;

import com.web.project.entity.Cart;
import com.web.project.entity.NhanVien;
import com.web.project.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart , Integer> {

    @Query("SELECT c FROM Cart c WHERE c.khachHang = ?1 AND c.sanPham = ?2")
    public Cart findByKhachHangAndSanPham(NhanVien khachHang, SanPham sanPham);
    @Query("SELECT c FROM Cart c WHERE c.khachHang = ?1")
    public List<Cart> findByKhachHang(NhanVien khachHang);

    @Modifying
    @Query("UPDATE Cart c SET c.soLuong = ?3 WHERE c.khachHang = ?1 AND c.sanPham = ?2")
    public void updateSoLuong(NhanVien khachHang , SanPham sanPham , int soLuong);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.khachHang = ?1 AND c.sanPham = ?2")
    public void removeCart(NhanVien khachHang , SanPham sanPham);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.khachHang = ?1")
    public void removeCartByKhachHang(NhanVien khachHang);
}
