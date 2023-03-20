package com.web.project.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.web.project.entity.NhanVien;
import java.util.*;

public class MyShopUserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private NhanVien nhanVien;
	
	
	public MyShopUserDetail(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> authorities = 
				nhanVien.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
		
		return authorities;
	
	}

	@Override
	public String getPassword() {
	    return nhanVien.getPassword();
	}

	@Override
	public String getUsername() {
		return nhanVien.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return nhanVien.isTrangThai();
	}
	public String getFullName() {
		return this.nhanVien.getHo() + " " + this.nhanVien.getTen();
	}
	public String getPhotos() {
		return "/user-photos/" + this.nhanVien.getId() + "/" + this.nhanVien.getPhotos();
	}
	public void setHo(String ho) {
		this.nhanVien.setHo(ho);
	}
	public void setTen(String ten) {
		this.nhanVien.setTen(ten);
	}


}
