package com.web.project.repository;

import com.web.project.entity.ChiTietNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CtnhRepository extends JpaRepository<ChiTietNhap, Long> {
}
