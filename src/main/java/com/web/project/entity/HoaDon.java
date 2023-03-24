package com.web.project.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "hoadons")
public class HoaDon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	
	@Column(name = "ngay_tao")
	private LocalDateTime ngayTao ;

	@Column(name = "thanh_tien" , nullable = false , length = 50)
	private Double thanhTien ;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nhan_vien_id")
	private NhanVien nhanVien ;

	// YYYY-DD-MM HH:MM:SS



	public HoaDon(Double thanhTien, NhanVien nhanVien) {
		this.thanhTien = thanhTien;
		this.nhanVien = nhanVien;
	}

	public HoaDon(LocalDateTime ngayTao, Double thanhTien, NhanVien nhanVien) {
		this.ngayTao = ngayTao;
		this.thanhTien = thanhTien;
		this.nhanVien = nhanVien;
	}
}
