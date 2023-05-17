package com.web.project.repository;

import com.web.project.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    @Query("SELECT size FROM Size size WHERE size.sanPham.id = ?1")
    public List<Size> findBySanPham(Integer sanPhamId) ;

    @Modifying
    @Query("UPDATE Size s SET s.soLuong = ?1 WHERE s.id = ?2 ")
    public void updateSoLuong(int soLuong , Long sizeId);
}
