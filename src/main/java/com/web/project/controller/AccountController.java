package com.web.project.controller;


import java.io.IOException;

import com.web.project.FileUploadUtil;
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
	public String accountDetails(@AuthenticationPrincipal MyShopUserDetail loggedUser , Model model) {
		String email = loggedUser.getUsername();
		NhanVien user = service.findByEmail(email);
		model.addAttribute("nhanvien", user);
		return "nhanvien/account_form";
	}
	@PostMapping("account/update")
	public String save(NhanVien nhanvien , RedirectAttributes re ,
					   @RequestParam("image") MultipartFile multipartFile ,
					   @AuthenticationPrincipal MyShopUserDetail account) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			nhanvien.setPhotos(fileName);
			NhanVien savedUser = service.save(nhanvien);
			
			String uploadDir = "nhanvien-photos/" + savedUser.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			if (nhanvien.getPhotos().isEmpty()) nhanvien.setPhotos(null);
			service.updateAccount(nhanvien);
		}
		account.setHo(nhanvien.getHo());
		account.setTen(nhanvien.getTen());
		re.addFlashAttribute("message", "The user account has been updated successfully.");
		return "redirect:/account";
	}
	
}
