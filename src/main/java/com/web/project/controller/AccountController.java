package com.web.project.controller;


import java.io.IOException;

import com.web.project.FileUploadUtil;
import com.web.project.Utility;
import com.web.project.service.NhanVienNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.web.project.entity.NhanVien;
import com.web.project.security.MyShopUserDetail;
import com.web.project.service.NhanVienService;

@Controller
public class AccountController {

	
	@Autowired 
	private NhanVienService service ;
	
	@GetMapping("/account")
	public String accountDetails(@AuthenticationPrincipal MyShopUserDetail loggedUser ,
								 Model model ,
								 HttpServletRequest request) {
		// Lấy ra email của user đang đăng nhập
		String email;
		if(loggedUser != null){
			email = loggedUser.getUsername();
		} else {
			email = Utility.getEmailOfAuthenticatedCustomer(request) ;
		}
		NhanVien user = service.findByEmail(email);
		model.addAttribute("nhanvien", user);
		return "nhanvien/account_form";
	}
	@PostMapping("account/update")
	public String save(NhanVien nhanvien , RedirectAttributes re ,
					   @RequestParam("image") MultipartFile multipartFile ,
					   @AuthenticationPrincipal MyShopUserDetail account ,
					   HttpServletRequest request,
					   HttpSession session) throws IOException {
		if (!multipartFile.isEmpty()) { // Kiểm tra xem bạn có thay đổi ảnh đại diện hay không
			// Lấy ra tên file ảnh mà bạn đã upload Vd test.png
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

			nhanvien.setPhotos(fileName);

			// Vì chúng ta dùng hàm này chung với cập nhật tài khoản
			// Nên ta cần kiểm tra xem account hiện tại có trùng với tài khoản mà admin chỉnh sửa không
			String email = Utility.getEmailOfAuthenticatedCustomer(request);
			if(nhanvien.getEmail().equals(email)) { // Nếu trùng thì cập nhật session để hiện thì ảnh mới của account
				String imagePath = nhanvien.getPhotosImagePath();
				session.setAttribute("imagePath", imagePath);
			}
			String uploadDir = "nhanvien-photos/" + nhanvien.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		}
		try {
			service.updateAccount(nhanvien);
		} catch (NhanVienNotFoundException e) {
			re.addFlashAttribute("message" , e.getMessage());
		}
		account.setHo(nhanvien.getHo());
		account.setTen(nhanvien.getTen());
		re.addFlashAttribute("message", "The user account has been updated successfully.");
		return "redirect:/account";
	}
	
}
