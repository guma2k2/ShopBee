package com.web.project.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.project.entity.LoaiSanPham;
import com.web.project.repository.LoaiSanPhamRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class LoaiSanPhamService {

	public static final int LoaiSpPerPage = 5 ;
	@Autowired
	private LoaiSanPhamRepository repo;
	
	public List<LoaiSanPham> listAll(){
		return repo.findAll();
	}
	
	public Page<LoaiSanPham> listByPage(String sortDir , String sortField , String keyword , int pageNum){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable  pageable = PageRequest.of(pageNum - 1, LoaiSpPerPage , sort);
		if(keyword != null) {
			return repo.findAll(keyword , pageable);
		}
		return repo.findAll(pageable);
	}
	
	public LoaiSanPham save(LoaiSanPham loaiSanPham) {
		return repo.save(loaiSanPham);
	}
	
	public void updateTrangThai(Integer id , boolean status) {
		repo.updateTrangThai(id, status);
	}
	
	public LoaiSanPham getById(Integer id) throws LoaiSanPhamNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new LoaiSanPhamNotFoundException("khong ton tai loai san pham");
		}
	}
	public void delete(Integer id ) throws LoaiSanPhamNotFoundException {
		Long countedId = repo.countById(id);
		if(countedId == null || countedId == 0) {
			throw new LoaiSanPhamNotFoundException("Khong tim thay loai san pham nao"); 
		}
		repo.deleteById(id);
	}
	public boolean checkTenUnique(Integer id,String ten){
		LoaiSanPham loaiSanPham = repo.findByName(ten);
		if(loaiSanPham == null) return true ;
		boolean isCreateNew = (id == null);
		if(isCreateNew){
			if(loaiSanPham != null){
				return false;
			}
		}else {
			if(loaiSanPham.getId() != id){
				return false;
			}
		}
		return true;
	}
}
