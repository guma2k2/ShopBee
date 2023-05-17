package com.web.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

	@ManyToOne
	@JoinColumn(name = "size_id")
	private Size size;

	public ChiTietHoaDon(int soLuong, Double gia, HoaDon hoaDon, SanPham sanPham) {
		this.soLuong = soLuong;
		this.gia = gia;
		this.hoaDon = hoaDon;
		this.sanPham = sanPham;
	}
}
