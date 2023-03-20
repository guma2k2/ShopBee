package com.web.project.repository;

import com.web.project.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.web.project.entity.LoaiSanPham;

import java.util.List;

public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, Integer> {

	@Query("UPDATE LoaiSanPham l SET l.trangThai = ?2 WHERE l.id = ?1")
	@Modifying
	public void updateTrangThai(Integer id , boolean TrangThai);
	
	@Query("SELECT l FROM LoaiSanPham l WHERE l.ten = ?1")
	public LoaiSanPham findByName(String name);
	
	@Query("SELECT l FROM LoaiSanPham l WHERE l.ten LIKE %?1%")
	public Page<LoaiSanPham> findAll(String keywork,Pageable pageable);
	
	public Long countById(Integer id);


}
