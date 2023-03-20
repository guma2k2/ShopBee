package com.web.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.project.FileUploadUtil;
import com.web.project.entity.LoaiSanPham;
import com.web.project.service.LoaiSanPhamNotFoundException;
import com.web.project.service.LoaiSanPhamService;
import com.web.project.service.NhanVienService;

@Controller
public class LoaiSanPhamController {
	@Autowired
	private LoaiSanPhamService service;
	
	@GetMapping("/loaisanpham")
	public String listAll(Model model) {
		return listByPage(1, model,"ten", "asc", null);
	}
	@GetMapping("/loaisanpham/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum , Model model ,
			@Param("sortField") String sortField ,
			@Param("sortDir") String sortDir ,
			@Param("keyword")String keyword) {
		Page<LoaiSanPham> page = service.listByPage( sortDir, sortField , keyword , pageNum );
		List<LoaiSanPham> listSP = page.getContent();
		int start = (pageNum - 1) * LoaiSanPhamService.LoaiSpPerPage + 1;
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
		model.addAttribute("listLoai", listSP);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		return "loaisanpham/loaisanpham";
	}
	
	@GetMapping("/loaisanpham/new")
	public String form(Model model) {
		model.addAttribute("loai", new LoaiSanPham());
		model.addAttribute("pageTitle", "Them loai san pham");
		return "loaisanpham/loaisanpham_form";
	}
	
	@GetMapping("/loaisanpham/{id}/enabled/{enabled}")
	public String updateStatus(@PathVariable("id")Integer id , @PathVariable("enabled")boolean trangThai ,RedirectAttributes re) {
		service.updateTrangThai(id, trangThai);
		re.addFlashAttribute("message", "Cap nhat trang thai thanh cong");
		return "redirect:/loaisanpham";
	}
	
	@PostMapping("/loaisanpham/save")
	public String save(LoaiSanPham loaisanpham , RedirectAttributes re , @RequestParam("image") MultipartFile multipartFile) throws IOException {
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			loaisanpham.setPhoto(fileName);
			LoaiSanPham savedLoai = service.save(loaisanpham);
			String uploadDir = "loaisanpham-photos/" + savedLoai.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}else {
			if(loaisanpham.getPhoto().isEmpty()) loaisanpham.setPhoto(null);
			service.save(loaisanpham);
		}
		re.addFlashAttribute("message", "Loai San pham da duoc luu thanh cong");
		return "redirect:/loaisanpham";
	}
	@GetMapping("/loaisanpham/edit/{id}")
	public String edit(@PathVariable("id")Integer id , RedirectAttributes re ,
			Model model) {
		try {
			LoaiSanPham loai = service.getById(id);
			model.addAttribute("loai", loai);
			model.addAttribute("pageTitle", "Sua loai san pham");
			return "loaisanpham/loaisanpham_form";
		} catch (LoaiSanPhamNotFoundException e) {
			re.addFlashAttribute("message", e.getMessage());
			return "redirect:/loaisanpham_form";
		}
		
	}
	
	@GetMapping("/loaisanpham/delete/{id}")
	public String delete (@PathVariable("id") Integer id , RedirectAttributes re) {
		try {
			service.delete(id);
		} catch (LoaiSanPhamNotFoundException e) {
			re.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/loaisanpham";
	}
}
