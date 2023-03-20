package com.web.project;

import com.web.project.entity.LoaiSanPham;
import com.web.project.entity.NhanVien;
import com.web.project.entity.SanPham;
import com.web.project.service.LoaiSanPhamService;
import com.web.project.service.NhanVienService;
import com.web.project.service.SanPhamNotFoundException;
import com.web.project.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class MainController {

	@Autowired
	private SanPhamService sanPhamService;
	@Autowired
	private NhanVienService nhanVienService ;

	@Autowired
	private LoaiSanPhamService loaiSanPhamService;
	@GetMapping("/")
	public String viewHomePage(Model model ,HttpServletRequest request) {
		List<SanPham> listSP = sanPhamService.listAll();
		List<LoaiSanPham> listLoai = loaiSanPhamService.listAll();
		model.addAttribute("listSP" , listSP);
		model.addAttribute("listLoai" , listLoai);
		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}
	public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
		Object principal = request.getUserPrincipal();
		if (principal == null) return null;
		
		String customerEmail =  request.getUserPrincipal().getName();
		
		return customerEmail;
	}
	@GetMapping("/product_details/{id}")
	public String chiTietSanPham(@PathVariable("id")Integer id, Model model , RedirectAttributes re) {
		try {
			SanPham sanPham = sanPhamService.get(id);
			model.addAttribute("sanpham" , sanPham);
		}catch (SanPhamNotFoundException ex){
			model.addAttribute("message" , ex.getMessage());
		}
		return "sanpham/sanpham_chitiet";
	}

	@GetMapping("/listSpTheoLoai/{id}")
	public String listSptheLoai(@PathVariable("id")Integer id , Model model){
		List<SanPham> listSP = sanPhamService.getSpTheoLoai(id);
		List<LoaiSanPham> listLoai = loaiSanPhamService.listAll();
		model.addAttribute("listSP" , listSP);
		model.addAttribute("listLoai" , listLoai);
		return "index";
	}

	@GetMapping("/doanhthu")
	public String doanhThu(){
		return "doanhthu/doanhthu";
	}

}
