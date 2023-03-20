package com.web.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.web.project.entity.NhanVien;
import com.web.project.repository.NhanVienRepository;

public class MyShopUserDetailService implements UserDetailsService {
	@Autowired
	private NhanVienRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		NhanVien nhanVien = repo.findByEmail(username);
		if(nhanVien != null) {
			return new  MyShopUserDetail(nhanVien);
		}
		throw new UsernameNotFoundException("User not found");
	}

}
