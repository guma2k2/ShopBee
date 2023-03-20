package com.web.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ct_hoa_don")
public class ChiTietHoaDon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "so_luong" , nullable = false , length = 20)
	private int soLuong ;
	
	@Column(name = "gia" , length = 50)
	private Double gia;
	
	@ManyToOne
	@JoinColumn(name = "hoa_don_id")
	private HoaDon hoaDon ;
	
	@ManyToOne
	@JoinColumn(name = "san_pham_id")
	private SanPham sanPham ;

	public ChiTietHoaDon(int soLuong, Double gia, HoaDon hoaDon, SanPham sanPham) {
		this.soLuong = soLuong;
		this.gia = gia;
		this.hoaDon = hoaDon;
		this.sanPham = sanPham;
	}
}
