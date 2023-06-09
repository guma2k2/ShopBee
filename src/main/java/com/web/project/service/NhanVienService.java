package com.web.project.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.web.project.RandomString;
import com.web.project.entity.AuthenticationType;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NhanVienService {
	public static final int NhanVienPerPage = 5 ;
	@Autowired
	private NhanVienRepository nhanVienRepository;
	
	@Autowired
	private RoleRepository roleRepo ;
	
	@Autowired
	private PasswordEncoder passwordEncoder ;
	
	public List<NhanVien> listAll(){
		return nhanVienRepository.findAll();
	}
	
	public List<Role> listRoles(){
		return roleRepo.findAll();
	}
	
	public Page<NhanVien> listByPage(int pageNum , String sortField , String sortDir , String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, NhanVienPerPage , sort);
		if(keyword != null) {
			return nhanVienRepository.findAll(keyword, pageable);
		}
		return nhanVienRepository.findAll(pageable);
		
	}

	public NhanVien save(NhanVien nhanvien) {
		// Kiểm tra xem tạo mới hay cập nhật
		boolean isUpdate = (nhanvien.getId() != null);
		if(isUpdate) {
			// nếu là cập nhật thì get Nhan vien theo id
			NhanVien UpdatednhanVien = nhanVienRepository.findById(nhanvien.getId()).get();
			// Cập nhật password với 2 trường hợp để trống hoặc khác null
			if(nhanvien.getPassword().isEmpty()) {
				nhanvien.setPassword(UpdatednhanVien.getPassword());
			}else {
				encodePassword(nhanvien);
			}
		}else {
			encodePassword(nhanvien);
		}
		return nhanVienRepository.save(nhanvien);
	}
	
	public void encodePassword(NhanVien user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer id , String email) {
		NhanVien nhanVien = nhanVienRepository.findByEmail(email);
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
			return nhanVienRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NhanVienNotFoundException("Khong tim thay nhan vien nao");
		}
	}
	public void delete(Integer id ) throws NhanVienNotFoundException {
		Long countedId = nhanVienRepository.countById(id);
		if(countedId == null || countedId == 0) {
			throw new NhanVienNotFoundException("Khong tim thay nhan vien nao"); 
		}
		nhanVienRepository.deleteById(id);
	}
	public void updateTrangThaiNV(Integer id , boolean trangThai) {
		nhanVienRepository.updateTrangThai(id, trangThai);
	}

	public NhanVien findByEmail(String email) {
		return nhanVienRepository.findByEmail(email);
	}
	public NhanVien updateAccount(NhanVien account) throws NhanVienNotFoundException {
		NhanVien nhanVien = nhanVienRepository.findById(account.getId()).orElseThrow(() -> new NhanVienNotFoundException("Nhan vien not found"));
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
		return nhanVienRepository.save(nhanVien);
	}

	public void  saveCustomer(NhanVien customer){
		encodePassword(customer);
		customer.setTrangThai(false);
		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);
		customer.setAuthenticationType(AuthenticationType.DATABASE);
		System.out.println(randomCode);
		nhanVienRepository.save(customer);
	}

	public boolean checkUniqueEmailCustomer(String email){
		return nhanVienRepository.findByEmail(email) == null;
	}

	public boolean verify(String verification){
		NhanVien customer = nhanVienRepository.findByVerificationCode(verification);

		// Kiểm tra xem khách hàng này đã tồn tại chưa
		if(customer == null || customer.isTrangThai()){
			return false ;
		}else {
			// Nếu true cập nhật trạng thái của người dùng về true
			nhanVienRepository.updateTrangThaiCustomer(customer.getId());
			return true;
		}
	}

	public String updatePasswordCustomer(String email) throws NhanVienNotFoundException {
		NhanVien customer = nhanVienRepository.findByEmail(email);
		if(customer != null){
			String code = RandomString.make(64);
			customer.setForgotPassword(code);
			nhanVienRepository.save(customer);
			return code ;
		}else {
			throw  new NhanVienNotFoundException("Email của bạn đã nhập sai hoặc không tồn tại !!");
		}
	}

	public NhanVien findByToken(String token) {
		return nhanVienRepository.findByForgotPassword(token);
	}

	public void updatePasswordReset(String token , String password) throws NhanVienNotFoundException {
		NhanVien customer = nhanVienRepository.findByForgotPassword(token);
		if(customer == null){
			throw new NhanVienNotFoundException("Khong tim thay nhan vien do token sai");
		}
		customer.setPassword(password);
		encodePassword(customer);
		nhanVienRepository.save(customer);
	}



	public void addNewCustomerOauth(String name , String email){
		NhanVien customer = new NhanVien();
		setName(name , customer);
		customer.addRole(new Role(3));
		customer.setEmail(email);
		customer.setTrangThai(true);
		customer.setDiaChi("");
		customer.setPhotos("");
		customer.setPassword("");
		customer.setSdt("");
		nhanVienRepository.save(customer);

	}
	public void setName(String name ,NhanVien customer ){
		String[] full = name.split(" ");
		if(full.length < 2){

			customer.setHo(name);
			customer.setTen("");
		}else{
			String first = full[0];
			customer.setHo(first);
			customer.setTen(name.replace(first , ""));
		}
	}

}
