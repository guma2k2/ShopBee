package com.web.project.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.web.project.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.project.entity.NhanVien;
import com.web.project.entity.Role;
import com.web.project.repository.NhanVienRepository;
import com.web.project.repository.RoleRepository;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class NhanVienService {
	public static final int NhanVienPerPage = 5 ;
	@Autowired
	private NhanVienRepository nhanVienrRepo;
	
	@Autowired
	private RoleRepository roleRepo ;
	
	@Autowired
	private PasswordEncoder passwordEncoder ;
	
	public List<NhanVien> listAll(){
		return nhanVienrRepo.findAll();
	}
	
	public List<Role> listRoles(){
		return roleRepo.findAll();
	}
	
	public Page<NhanVien> listByPage(int pageNum , String sortField , String sortDir , String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, NhanVienPerPage , sort);
		if(keyword != null) {
			return nhanVienrRepo.findAll(keyword, pageable);
		}
		return nhanVienrRepo.findAll(pageable);
		
	}

	public NhanVien save(NhanVien nhanvien) {
		boolean isUpdate = (nhanvien.getId() != null);
		if(isUpdate) {
			NhanVien UpdatednhanVien = nhanVienrRepo.findById(nhanvien.getId()).get();
			if(nhanvien.getPassword().isEmpty()) {
				nhanvien.setPassword(UpdatednhanVien.getPassword());
			}else {
				encodePassword(nhanvien);
			}
		}else {
			encodePassword(nhanvien);
		}
		return nhanVienrRepo.save(nhanvien);
	}
	
	public void encodePassword(NhanVien user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer id , String email) {
		NhanVien nhanVien = nhanVienrRepo.findByEmail(email);
		if(nhanVien == null) return true;
		boolean isCreateNew = (id == null);
		
		if(isCreateNew) {
		   if(nhanVien != null) {
			   return false ;
		   }
			
		}else {
			if(nhanVien.getId() != id) {
				return false ;
			}
		}
		return true;
	}
	public NhanVien getById(Integer id) throws NhanVienNotFoundException {
		try {
			return nhanVienrRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NhanVienNotFoundException("Khong tim thay nhan vien nao");
		}
	}
	public void delete(Integer id ) throws NhanVienNotFoundException {
		Long countedId = nhanVienrRepo.countById(id);
		if(countedId == null || countedId == 0) {
			throw new NhanVienNotFoundException("Khong tim thay nhan vien nao"); 
		}
		nhanVienrRepo.deleteById(id);
	}
	public void updateTrangThaiNV(Integer id , boolean trangThai) {
		nhanVienrRepo.updateTrangThai(id, trangThai);
	}

	public NhanVien findByEmail(String email) {
		return nhanVienrRepo.findByEmail(email);
	}
	public NhanVien updateAccount(NhanVien account) {
		NhanVien nhanVien = nhanVienrRepo.findById(account.getId()).get();
		if(!account.getPassword().isEmpty()) {
			nhanVien.setPassword(account.getPassword());
			encodePassword(nhanVien);
		}
		if(!account.getPhotos().isEmpty()) {
			nhanVien.setPhotos(account.getPhotos());
		}
		nhanVien.setHo(account.getHo());
		nhanVien.setTen(account.getTen());
		nhanVien.setDiaChi(account.getDiaChi());
		nhanVien.setSdt(account.getSdt());
		return nhanVienrRepo.save(nhanVien);
	}

	public void  saveCustomer(NhanVien customer){
		encodePassword(customer);
		customer.setTrangThai(false);
		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);
		System.out.println(randomCode);
		nhanVienrRepo.save(customer);
	}

	public boolean checkUniqueEmailCustomer(String email){
		return nhanVienrRepo.findByEmail(email) == null;
	}

	public boolean verify(String verification){
		NhanVien customer = nhanVienrRepo.findByVerificationCode(verification);
		if(customer == null || customer.isTrangThai()){
			return false ;
		}else {
			nhanVienrRepo.updateTrangThaiCustomer(customer.getId());
			return true;
		}
	}

	public String updatePasswordCustomer(String email) throws NhanVienNotFoundException {
		NhanVien customer = nhanVienrRepo.findByEmail(email);
		if(customer != null){
			String code = RandomString.make(64);
			customer.setForgotPassword(code);
			nhanVienrRepo.save(customer);
			return code ;
		}else {
			throw  new NhanVienNotFoundException("Email của bạn đã nhập sai hoặc không tồn tại !!");
		}
	}

	public NhanVien findByToken(String token) {
		return nhanVienrRepo.findByForgotPassword(token);
	}

	public void updatePasswordReset(String token , String password) throws NhanVienNotFoundException {
		NhanVien customer = nhanVienrRepo.findByForgotPassword(token);
		if(customer == null){
			throw new NhanVienNotFoundException("Khong tim thay nhan vien do token sai");
		}
		customer.setPassword(password);
		encodePassword(customer);
		nhanVienrRepo.save(customer);
	}
}
