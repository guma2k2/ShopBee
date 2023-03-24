package com.web.project.controller;

import java.io.IOException;
import java.util.List;

import com.web.project.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.project.entity.NhanVien;
import com.web.project.entity.Role;
import com.web.project.service.NhanVienNotFoundException;
import com.web.project.service.NhanVienService;

@Controller
public class NhanVienController {

	@Autowired
	private NhanVienService service;
	
	
	@GetMapping("/nhanvien")
	public String listByPage(Model model) {
		return listByPage(1 , model ,"ho" , "asc" ,null );
	}
	
	@GetMapping("/nhanvien/new")
	public String addNhanVien(Model model) {
		List<Role> listRoles = service.listRoles();
		model.addAttribute("listRoles", listRoles);
		NhanVien nhanVien = new NhanVien();
		nhanVien.setTrangThai(true);
		model.addAttribute("nhanvien", nhanVien);
		model.addAttribute("pageTitle", "Them nhan vien");
		
		return "nhanvien/nhanvien_form";
	}
	@PostMapping("/nhanvien/save")
	public String save(NhanVien nhanvien ,
					   RedirectAttributes re ,
					   @RequestParam("image")MultipartFile multipartFile) throws IOException {
		System.out.println(nhanvien.getPassword());
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			nhanvien.setPhotos(fileName);
			NhanVien savedUser = service.save(nhanvien);
			
			String uploadDir = "nhanvien-photos/" + savedUser.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			if (nhanvien.getPhotos().isEmpty()) nhanvien.setPhotos(null);
			service.save(nhanvien);
		}
		
		
		re.addFlashAttribute("message", "The user has been saved successfully.");
		return "redirect:/nhanvien";
	}
	@GetMapping("/nhanvien/edit/{id}")
	public String edit(@PathVariable("id")Integer id , Model model , RedirectAttributes redirectAttributes) {
		try {
			NhanVien nv = service.getById(id);
			List<Role> roles = service.listRoles();
			model.addAttribute("nhanvien",nv);
			model.addAttribute("listRoles", roles);
			model.addAttribute("pageTitle", "Sua nhan vien");
			return "nhanvien/nhanvien_form";
		} catch (NhanVienNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/nhanvien_form";
		}
		
		
	}
	
	@GetMapping("/nhanvien/delete/{id}")
	public String delete (@PathVariable("id")Integer id,RedirectAttributes redirectAttributes , Model model) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message" , "Xóa thành công!");
		} catch (NhanVienNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			
		}
		return "redirect:/nhanvien";
	}
	@GetMapping("/nhanvien/{id}/enabled/{enabled}")
	public String updateTrangThai(@PathVariable("id")Integer id , @PathVariable("enabled")boolean trangThai , RedirectAttributes redirectAttributes) {
		service.updateTrangThaiNV(id, trangThai);
		redirectAttributes.addFlashAttribute("message", "Update Status successful");
		return "redirect:/nhanvien";
	}
	
	@GetMapping("/nhanvien/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum , Model model ,
			@Param("sortField") String sortField ,
			@Param("sortDir") String sortDir ,
			@Param("keyword")String keyword) {
		Page<NhanVien> page = service.listByPage(pageNum , sortField , sortDir , keyword);
		List<NhanVien> listNV = page.getContent();
		int start = (pageNum - 1) * NhanVienService.NhanVienPerPage + 1;
		int end = start + NhanVienService.NhanVienPerPage - 1;
		if(end > page.getTotalElements()) {
			end = (int) page.getTotalElements() ;
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("listNhanVien", listNV);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		return "nhanvien/nhanvien";
	}
	
}
