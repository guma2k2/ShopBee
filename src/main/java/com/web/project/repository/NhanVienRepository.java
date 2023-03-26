package com.web.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.web.project.entity.NhanVien;


public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

	@Query("SELECT n FROM nhanviens n WHERE n.email = ?1")
	public NhanVien findByEmail(String email);
	
	@Query("SELECT n FROM nhanviens n WHERE CONCAT(n.ho,' ' ,n.ten) LIKE %?1%")
	public Page<NhanVien> findAll(String keyword , Pageable pageable);
	
	public Long countById(Integer id );
	
	@Query("UPDATE nhanviens n SET n.trangThai = ?2 WHERE n.id = ?1")
	@Modifying
	public void updateTrangThai(Integer id , boolean TrangThai);

	@Query("UPDATE nhanviens n SET n.trangThai = true , n.verificationCode = null WHERE n.id = ?1")
	@Modifying
	public void updateTrangThaiCustomer(Integer id);

	@Query("SELECT k FROM nhanviens k WHERE  k.verificationCode = ?1 ")
	public NhanVien findByVerificationCode(String verificationCode);

	public NhanVien findByForgotPassword(String token);
}
