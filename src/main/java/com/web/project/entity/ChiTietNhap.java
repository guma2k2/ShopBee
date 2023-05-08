package com.web.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ct_nhap_hang")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChiTietNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @ManyToOne
    @JoinColumn(name = "nhanVien_id")
    private NhanVien nhanVien ;

    @ManyToOne
    @JoinColumn(name = "sanPham_id")
    private SanPham sanPham ;

    private int soLuong ;
}
