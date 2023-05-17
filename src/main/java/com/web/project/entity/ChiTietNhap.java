package com.web.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size ;
    private LocalDateTime thoiGianNhap;

    private Long giaMoiSanPham;
    private int soLuong ;

    @Transient
    public Long getTotal() {
        return giaMoiSanPham * soLuong ;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
