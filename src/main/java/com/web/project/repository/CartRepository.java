package com.web.project.repository;

import com.web.project.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart , Integer> {

    @Query("SELECT c FROM Cart c WHERE c.khachHang = ?1 AND c.sanPham = ?2 AND c.size = ?3")
    public Cart findByKhachHangAndSanPhamSize(Customer khachHang, SanPham sanPham , Size size);
    @Query("SELECT c FROM Cart c WHERE c.khachHang = ?1 AND c.sanPham = ?2 ")
    public Cart findByKhachHangAndSanPham(Customer khachHang, SanPham sanPham );
    @Query("SELECT c FROM Cart c WHERE c.khachHang = ?1")
    public List<Cart> findByKhachHang(Customer khachHang);

    @Modifying
    @Query("UPDATE Cart c SET c.soLuong = ?2 WHERE c.id = ?1")
    public void updateSoLuong(Integer cartId,  int soLuong);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id = ?1")
    public void removeCart(Integer cartId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.khachHang = ?1")
    public void removeCartByKhachHang(Customer khachHang);
}
