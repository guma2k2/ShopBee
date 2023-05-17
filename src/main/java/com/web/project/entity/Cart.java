package com.web.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "gioHang")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "khach_hang_id")
    private Customer khachHang ;

    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "san_pham_id")
    private SanPham sanPham;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "size_id")
    private Size size;

    @Column(name = "so_luong")
    private int soLuong ;

    @Transient
    public int getTongGia(){
        return soLuong * sanPham.getGia();
    }
}
