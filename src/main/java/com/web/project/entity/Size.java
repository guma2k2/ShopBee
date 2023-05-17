package com.web.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "size")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id ;

    private String ten;

    @Column(length = 30)
    private String moTa;

    private int soLuong ;

    @ManyToOne
    @JoinColumn(name = "sanPham_id")
    private SanPham sanPham ;

}
