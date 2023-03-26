package com.web.project;

import com.web.project.dto.DoanhThuTheoSanPham;
import com.web.project.dto.LichSuGiaoDich;
import com.web.project.dto.LichSuSanPham;
import com.web.project.entity.*;
import com.web.project.service.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

	@Autowired
	private SanPhamService sanPhamService;
	@Autowired
	private NhanVienService nhanVienService ;

	@Autowired
	private SettingService settingService;

	@Autowired
	private HoaDonService hoaDonService;

	@Autowired
	private CthdService cthdService;

	@Autowired
	private LoaiSanPhamService loaiSanPhamService;
	@GetMapping("/")
	public String viewHomePage(Model model ) {
		 return listByNoCategory(model,"desc","gia",null,1);
	}
	@GetMapping("/listSpTheoLoai/{idLoai}")
	public String viewHomePageWithCategory(@PathVariable("idLoai")Integer idLoai , Model model){
		return listByCategory(model ,"desc" , "gia" , null , 1 , idLoai);
	}
	@GetMapping("/page/{idLoai}/{pageNum}")
	public String listByCategory(Model model ,
								 @Param("sortDir") String sortDir ,
								 @Param("sortField") String sortField,
								 @Param("keyword") String keyword ,
								 @PathVariable("pageNum") int pageNum ,
								 @PathVariable("idLoai") int idLoai) {
		Page<SanPham> listSanPham =
				sanPhamService.listSanPhamByCategory(idLoai, keyword, sortDir, sortField, pageNum);
		List<SanPham> listSP = listSanPham.getContent();
		List<LoaiSanPham> listLoai = loaiSanPhamService.listAllEnable();
		int start = (pageNum - 1) * SanPhamService.sanPhamMoiPage + 1;
		int end = start + SanPhamService.sanPhamMoiPage - 1;
		if(end > listSanPham.getTotalElements()) {
			end = (int) listSanPham.getTotalElements() ;
		}
		model.addAttribute("idLoai" , idLoai);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("totalItems", listSanPham.getTotalElements());
		model.addAttribute("totalPages", listSanPham.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("listSP" , listSP);
		model.addAttribute("listLoai" , listLoai);
		return "index";
	}

	@GetMapping("/page/{pageNum}")
	public String listByNoCategory(Model model ,
								 @Param("sortDir") String sortDir ,
								 @Param("sortField") String sortField,
								 @Param("keyword") String keyword ,
								 @PathVariable("pageNum") int pageNum
								  ) {
		List<LoaiSanPham> listLoai = loaiSanPhamService.listAllEnable();
		Page<SanPham> page = sanPhamService.listByPageEnable( sortDir, sortField , keyword , pageNum );
		List<SanPham> listSP = page.getContent();
		int start = (pageNum - 1) * SanPhamService.sanPhamMoiPage + 1;
		int end = start + SanPhamService.sanPhamMoiPage - 1;
		if(end > page.getTotalElements()) {
			end = (int) page.getTotalElements() ;
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("listSP", listSP);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", reverseSortDir);
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


	@GetMapping("/register")
	public String register (Model model , RedirectAttributes redirectAttributes) {
		model.addAttribute("nhanvien" , new NhanVien());
		return "register";
	}
	@PostMapping("/customer/save")
	public String saveCustomer(Model model,NhanVien customer , RedirectAttributes redirectAttributes ,
							   HttpServletRequest request)
			throws MessagingException, UnsupportedEncodingException {
		if(!nhanVienService.checkUniqueEmailCustomer(customer.getEmail())){
			redirectAttributes.addFlashAttribute("message" , "email cua ban da duoc dang ki");
			return "redirect:/register";
		}
		Role role = new Role(3);
		customer.addRole(role);
		nhanVienService.saveCustomer(customer);
		sendVerificationEmail(request , customer);
		return "register_success";
	}

	private void sendVerificationEmail(HttpServletRequest request, NhanVien customer)
			throws MessagingException, UnsupportedEncodingException {
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(settingService);
		String toAddress = customer.getEmail();
		String subject = settingService.getCustomerVerifySubject();
		String content = settingService.getCustomerVerifyContent();

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(settingService.getFromAddress(), settingService.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", customer.getFullName());

		String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);
	}

	@GetMapping("/verify")
	public String verifyCode(@Param("code") String code){
		boolean verify = nhanVienService.verify(code);
		return verify ? "register_success" : "register_fail";
	}

	@GetMapping("/history")
	public String history(HttpServletRequest request , Model model ){
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		List<HoaDon> hoaDonList = hoaDonService.findByEmail(email);
		List<LichSuGiaoDich> history = new ArrayList<>();
		for(HoaDon  hoaDon : hoaDonList){
			LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
			lichSuGiaoDich.setIdHoaDon(hoaDon.getId());
			lichSuGiaoDich.setNgayGiaoDich(hoaDon.getNgayTao());
			List<LichSuSanPham> listSp = cthdService.listByDay(hoaDon.getNgayTao());
			lichSuGiaoDich.setListSP(listSp);
			lichSuGiaoDich.setTongTien(hoaDon.getThanhTien());
			history.add(lichSuGiaoDich);
		}
		model.addAttribute("history" , history);
		return "lichsu";
	}

	@GetMapping("/forgotpassword")
	public String pageForgotPassword (){
		return "forgotpassword";
	}
	@PostMapping("/verifypassword")
	public String forgotPassword(HttpServletRequest request ,Model model){
		String email = request.getParameter("email");
		try {
			String codeVerify = nhanVienService.updatePasswordCustomer(email);
			String link =  Utility.getSiteURL(request) +  "/reset_password?token=" + codeVerify;
			sendVerifyPassword(request ,email,link);
			model.addAttribute("message" , "Gui email thanh cong !!" );
		} catch (NhanVienNotFoundException e) {
			model.addAttribute("error" , "Mat khau ban nhap sai hoac khong ton tai !!");
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error" , "Khong the gui email");
		}
		return "forgotpassword";
	}
	@GetMapping("/reset_password")
	public String forgotPasswordForm(@Param("token") String token,
									 Model model) {
		NhanVien customer = nhanVienService.findByToken(token);
		if(customer != null) {
			model.addAttribute("token" ,token);
			return "forgot_form";
		}else{
			model.addAttribute("pageTitle" ,"Reset password" );
			model.addAttribute("message" , "Reset password fail");
			return "message";
		}
	}

	private void sendVerifyPassword(HttpServletRequest request, String email , String link)
			throws MessagingException, UnsupportedEncodingException {
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(settingService);
		String toAddress = email;
		String subject = "Xác nhân email và thay đổi mật khẩu " ;
		String content = "<p>Xin chao,</p>" +
						"<p> Ban da yeu cau thay doi mat khau </p>" +
				 		"Vui long chon link ben duoi de thay doi mat khau:" +
						"<p> <a href =\"" +link+ "\">Thay doi mat khau</a> </p>" +
						"<br>" +
						"<p>Xoa mail neu ban muon, ngay khi thay doi duoc mat khau</p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(settingService.getFromAddress(), settingService.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);
	}

	@PostMapping("/reset_password")
	public String verifyResetPassword(HttpServletRequest request , Model model ) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		try {
			nhanVienService.updatePasswordReset(token,password);
			model.addAttribute("pageTitle" , "Reset password");
			model.addAttribute("message" ,"Reset password successful !!");
			return "message";
		} catch (NhanVienNotFoundException e) {
			model.addAttribute("pageTitle" , "Reset password");
			model.addAttribute("message" , "fail");
			return "message";
		}
	}

}
