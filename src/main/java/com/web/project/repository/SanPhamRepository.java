package com.web.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.SanPham;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query("UPDATE SanPham s SET s.trangThai = ?2 WHERE s.id = ?1")
    @Modifying
    public void updateStatus(Integer id , boolean status);

    public Long countById(Integer id);
    @Query("SELECT s FROM SanPham s WHERE s.ten = ?1")
    public SanPham findByTen(String ten);

    @Query("SELECT s FROM SanPham s WHERE s.trangThai = 1")
    public List<SanPham> listAllSpEnabled();

    @Query("SELECT s FROM SanPham s WHERE s.loaiSanPham.id = ?1")
    public List<SanPham> listSpTheoLoai(Integer id);

    @Query("SELECT s FROM SanPham s WHERE s.ten LIKE %?1%")
    public Page<SanPham> findAll(String keyword , Pageable pageable);

    @Query("SELECT s FROM SanPham s WHERE s.ten LIKE %?1% AND s.trangThai = true")
    public Page<SanPham> findAllEnable(String keyword , Pageable pageable);
    @Query("SELECT s FROM SanPham s WHERE s.trangThai = true")
    public Page<SanPham> findAllEnable(Pageable pageable);


    @Query("SELECT s FROM SanPham s WHERE s.ten LIKE %?2% AND s.loaiSanPham.id = ?1 AND s.trangThai = true")
    public  Page<SanPham> findByCategory(Integer idLoai,String keyword , Pageable pageable);

    @Query("SELECT s FROM SanPham s WHERE s.loaiSanPham.id = ?1 AND s.trangThai = true ")
    public  Page<SanPham> findByCategory(int idLoai, Pageable pageable);

    @Query("UPDATE SanPham s SET s.soLuong = ?1 WHERE s = ?2")
    @Modifying
    public void updateQuantity(int quantity, SanPham sanPham);
}
