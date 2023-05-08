package com.web.project.repository;

import com.web.project.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Query("SELECT r FROM Review r WHERE r.khachHang.email = ?1 AND r.sanPham.id=?2")
    List<Review> findByCustomerProduct(String emailCustomer, Integer productId);

    @Query("SELECT r FROM Review r WHERE r.sanPham.id = ?1")
    List<Review> findByProduct(Integer productId) ;

    @Query("SELECT COUNT(r) FROM Review r WHERE r.khachHang.email = ?1 AND r.sanPham.id = ?2")
    long countByCustomerProduct(String email, Integer productId);

    @Query("SELECT r FROM Review r WHERE r.sanPham.ten LIKE %?1% OR CONCAT(r.khachHang.ho, ' ' , r.khachHang.ten)  LIKE %?1%")
    Page<Review> findAll(String keyword, Pageable pageable) ;

    @Query("SELECT r FROM Review r " +
            "INNER JOIN r.khachHang k " +
            "INNER JOIN r.sanPham s WHERE k.email = ?2 AND s.ten = ?1")
    Page<Review> findAllCustomer(String keyword, String email, Pageable pageable) ;

    @Query("SELECT r FROM Review r " +
            "INNER JOIN r.khachHang k " +
            "INNER JOIN r.sanPham s WHERE k.email = ?1")
    Page<Review> findAllCustomer(String email, Pageable pageable) ;
}
