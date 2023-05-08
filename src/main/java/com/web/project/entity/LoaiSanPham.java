package com.web.project.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "loaiSanPham")
public class LoaiSanPham {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	@Column(name = "ten" , nullable = false)
	private String ten ;
	
	@Column(name = "trang_thai")
	private boolean trangThai;
	
	@Column(name = "photo")
	private String photo;
	
	public LoaiSanPham(Integer id, String ten, boolean trangThai, String photo, List<SanPham> sanPham) {
		super();
		this.id = id;
		this.ten = ten;
		this.trangThai = trangThai;
		this.photo = photo;
		this.sanPham = sanPham;
	}

	@OneToMany(mappedBy = "loaiSanPham" , cascade = CascadeType.ALL)
	private List<SanPham> sanPham ;

	public LoaiSanPham() {
	}

	public LoaiSanPham(Integer id, String ten, boolean trangThai, List<SanPham> sanPham) {
		super();
		this.id = id;
		this.ten = ten;
		this.trangThai = trangThai;
		this.sanPham = sanPham;
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

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public List<SanPham> getSanPham() {
		return sanPham;
	}

	public void setSanPham(List<SanPham> sanPham) {
		this.sanPham = sanPham;
	}
	
	@Transient
	public String getPhotosImagePath() {
		if (id == null || photo == null) return "/images/default-user.png";
		
		return "/loaisanpham-photos/" + this.id + "/" + this.photo;
	}
	
	@Override
	public String toString() {
		return "LoaiSanPham [id=" + id + ", ten=" + ten + ", trangThai=" + trangThai + ", sanPham=" + sanPham + "]";
	}
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
