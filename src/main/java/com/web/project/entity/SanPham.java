package com.web.project.entity;


import jakarta.persistence.*;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sanPham")
public class SanPham {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ten" , nullable = false, length = 100)
	private String ten ;
	
	private int gia;
	
	@Column(name = "anh" , nullable = false , length =100)
	private String anh ;
	
	@Column(name = "mo_ta" , length = 800)
	private String moTa ;
	
	@Column(name = "trang_thai")
	private boolean trangThai ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loai_san_pham_id")
	private LoaiSanPham loaiSanPham;

	@OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>() ;

	@Transient
	public String getPhotosImagePath() {
		if (id == null || anh == null) return "/images/default-user.png";
		return "/sanpham-photos/" + this.id + "/" + this.anh;
	}
	@OneToMany(mappedBy = "sanPham" , cascade = CascadeType.ALL , orphanRemoval = true)
	private List<Size> sizes = new ArrayList<>();

	@Transient
	private boolean canReview ;

	@Transient
	private boolean didReview ;

	public long getTotalQuantity() {
		long total = 0 ;
		for(Size size : sizes) {
			total+=size.getSoLuong();
		}
		return total;
	}

	@Transient
	public int getReviewCount() {
		return reviews.size() > 0 ? reviews.size() : 0 ;
	}

	@Transient
	public Double getAverageRating() {
		return reviews.stream().mapToInt(Review::getRating).average().orElse(0);
	}

	public List<Size> getSizes() {
		return sizes;
	}

	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}

	public SanPham(Integer id, String ten, int gia, String anh, String moTa, boolean trangThai, LoaiSanPham loaiSanPham, List<Review> reviews) {
		this.id = id;
		this.ten = ten;
		this.gia = gia;
		this.anh = anh;
		this.moTa = moTa;
		this.trangThai = trangThai;
		this.loaiSanPham = loaiSanPham;
		this.reviews = reviews;
	}

	public SanPham(Integer id, String ten, int gia, String anh, String moTa, boolean trangThai, LoaiSanPham loaiSanPham) {
		this.id = id;
		this.ten = ten;
		this.gia = gia;
		this.anh = anh;
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

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public boolean isCanReview() {
		return canReview;
	}

	public void setCanReview(boolean canReview) {
		this.canReview = canReview;
	}

	public boolean isDidReview() {
		return didReview;
	}

	public void setDidReview(boolean didReview) {
		this.didReview = didReview;
	}
}
