package com.web.project.repository;

import com.web.project.entity.AuthenticationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.web.project.entity.NhanVien;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

	@Query("SELECT n FROM NhanVien n WHERE n.email = ?1")
	public NhanVien findByEmail(String email);
	
	@Query("SELECT n FROM NhanVien n WHERE CONCAT(n.ho,' ' ,n.ten) LIKE %?1% OR n.email LIKE %?1%")
	public Page<NhanVien> findAll(String keyword , Pageable pageable);
	
	public Long countById(Integer id );
	
	@Query("UPDATE NhanVien n SET n.trangThai = ?2 WHERE n.id = ?1")
	@Modifying
	public void updateTrangThai(Integer id , boolean TrangThai);

	@Query("UPDATE NhanVien n SET n.trangThai = true , n.verificationCode = null WHERE n.id = ?1")
	@Modifying
	public void updateTrangThaiCustomer(Integer id);

	@Query("SELECT k FROM NhanVien k WHERE  k.verificationCode = ?1 ")
	public NhanVien findByVerificationCode(String verificationCode);

	public NhanVien findByForgotPassword(String token);

	@Modifying
	@Query("UPDATE NhanVien n SET n.authenticationType = ?1 WHERE n.id = ?2")
	public void updateAuthenticationType(AuthenticationType type , Integer id );
}
