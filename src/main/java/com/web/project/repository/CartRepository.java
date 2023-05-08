package com.web.project.repository;

import com.web.project.entity.Cart;
import com.web.project.entity.Customer;
import com.web.project.entity.NhanVien;
import com.web.project.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart , Integer> {

    @Query("SELECT c FROM Cart c WHERE c.khachHang = ?1 AND c.sanPham = ?2")
    public Cart findByKhachHangAndSanPham(Customer khachHang, SanPham sanPham);
    @Query("SELECT c FROM Cart c WHERE c.khachHang = ?1")
    public List<Cart> findByKhachHang(Customer khachHang);

    @Modifying
    @Query("UPDATE Cart c SET c.soLuong = ?3 WHERE c.khachHang = ?1 AND c.sanPham = ?2")
    public void updateSoLuong(Customer khachHang , SanPham sanPham , int soLuong);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.khachHang = ?1 AND c.sanPham = ?2")
    public void removeCart(Customer khachHang , SanPham sanPham);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.khachHang = ?1")
    public void removeCartByKhachHang(Customer khachHang);
}
