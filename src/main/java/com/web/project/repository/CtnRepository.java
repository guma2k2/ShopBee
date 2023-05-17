package com.web.project.repository;

import com.web.project.entity.ChiTietNhap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CtnRepository extends JpaRepository<ChiTietNhap, Long> {

    @Query("SELECT ctn FROM ChiTietNhap ctn WHERE ctn.id = ?1")
    Page<ChiTietNhap> findAll(String keywod, Pageable pageable);
}
