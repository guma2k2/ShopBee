package com.web.project.entity;


import jakarta.persistence.*;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sanphams")
public class SanPham {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ten" , nullable = false, length = 30)
	private String ten ;
	
	private int gia;
	
	@Column(name = "anh" , nullable = false , length =100)
	private String anh ;
	
	@Column(name = "size")
	private String size ;
	
	@Column(name = "mo_ta" , length = 800)
	private String moTa ;
	
	@Column(name = "trang_thai")
	private boolean trangThai ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loai_san_pham_id")
	private LoaiSanPham loaiSanPham;



	@Transient
	public String getPhotosImagePath() {
		if (id == null || anh == null) return "/images/default-user.png";
		return "/sanpham-photos/" + this.id + "/" + this.anh;
	}

	public SanPham(Integer id, String ten, int gia, String anh, String size, String moTa, boolean trangThai, LoaiSanPham loaiSanPham) {
		this.id = id;
		this.ten = ten;
		this.gia = gia;
		this.anh = anh;
		this.size = size;
		this.moTa = moTa;
		this.trangThai = trangThai;
		this.loaiSanPham = loaiSanPham;
	}

	public SanPham() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public int getGia() {
		return gia;
	}

	public void setGia(int gia) {
		this.gia = gia;
	}

	public String getAnh() {
		return anh;
	}

	public void setAnh(String anh) {
		this.anh = anh;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public LoaiSanPham getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}

}
