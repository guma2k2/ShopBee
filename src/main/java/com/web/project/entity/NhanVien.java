package com.web.project.entity;


import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.ToString;

@Entity(name = "nhanviens")
@Table(name = "nhanviens")
public class NhanVien {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	
	@Column(name = "ho" , length = 40 , nullable = false)
	private String ho ;
	@Column(name = "ten" , length = 40 , nullable = false)
	private String ten;
	@Column(name = "sdt" , length = 20 )
	private String sdt ;


	@Column(name = "dia_chi")
	private String DiaChi;
	
	@Column(name = "gioi_tinh")
	@Enumerated(EnumType.STRING)
	private GioiTinh gioiTinh ;
	
	@Column(name = "trang_thai")
	private boolean trangThai ;
	
	@Column(name = "email" , unique = true , nullable = false)
	private String email ;
	
	@Column(name = "password")
	private String password;
	

	@Column(length = 64)
	private String photos;
	
	@OneToMany(mappedBy = "nhanVien" , cascade = CascadeType.ALL)
	private List<HoaDon> hoaDon  ;

	@Column(name = "verification_code", length = 64)
	private String verificationCode;

	@Column(name = "forgot_password_code" ,length = 100)
	private String forgotPassword ;


	@Enumerated(value = EnumType.STRING)
	@Column(name = "authentication_type" )
	private AuthenticationType authenticationType;



	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "nhanviens_roles",
			joinColumns = @JoinColumn(name = "nhavien_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();


	public NhanVien() {
	}

	public NhanVien(String ho, String ten, String sdt, String diaChi, boolean trangThai, String email,
					String password) {
		this.ho = ho;
		this.ten = ten;
		this.sdt = sdt;
		this.DiaChi = diaChi;
		this.trangThai = trangThai;
		this.email = email;
		this.password = password;
	}

	public NhanVien(Integer id, String ho, String ten, String sdt, String email) {
		super();
		this.id = id;
		this.ho = ho;
		this.ten = ten;
		this.sdt = sdt;
		this.email = email;
	}

	public NhanVien( String ho, String ten, String sdt, String email) {
		this.ho = ho;
		this.ten = ten;
		this.sdt = sdt;
		this.email = email;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getDiaChi() {
		return DiaChi;
	}

	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}

	public GioiTinh getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(GioiTinh gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}
	public NhanVien(Integer id, String ho, String ten, String sdt, String diaChi, GioiTinh gioiTinh, boolean trangThai,
			String email, String password, String photos, Set<Role> roles) {
		this.id = id;
		this.ho = ho;
		this.ten = ten;
		this.sdt = sdt;
		this.DiaChi = diaChi;
		this.gioiTinh = gioiTinh;
		this.trangThai = trangThai;
		this.email = email;
		this.password = password;
		this.photos = photos;
		this.roles = roles;
	}

	public NhanVien(Integer id) {
		this.id = id;
	}

	public List<HoaDon> getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(List<HoaDon> hoaDon) {
		this.hoaDon = hoaDon;
	}
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}


	public String getForgotPassword() {
		return forgotPassword;
	}

	public void setForgotPassword(String forgotPassword) {
		this.forgotPassword = forgotPassword;
	}


	@Transient
	public String getPhotosImagePath() {
		if (id == null || photos == null) return "/images/default-user.png";
		
		return "/nhanvien-photos/" + this.id + "/" + this.photos;
	}

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}

	@Override
	public String toString() {
		return "NhanVien [id=" + id + ", Ho=" + ho + ", ten=" + ten + ", Sdt=" + sdt + ", email=" + email + "]";
	}

	@Transient
	public String getFullName(){
		return this.ho + " " + this.ten;
	}
	
	
}
