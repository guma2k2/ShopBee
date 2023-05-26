package com.web.project.controller;


import com.web.project.Utility;
import com.web.project.entity.ChiTietNhap;
import com.web.project.entity.NhanVien;
import com.web.project.entity.SanPham;
import com.web.project.entity.Size;
import com.web.project.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class NhapHangController {

    @Autowired private NhapHangService nhapHangService;
    @Autowired private SanPhamService sanPhamService;
    @Autowired private SizeService sizeService;

    @Autowired private NhanVienService nhanVienService ;

    @GetMapping("/nhapHang")
    public String nhapHang(Model model){
        return listByPage("desc" , "thoiGianNhap" , null , 1 , model);
    }
    @GetMapping("/nhapHang/new")
    public String formNhapHang(Model model){
        boolean readOnly = false ;
        model.addAttribute("readOnly" , readOnly);
        model.addAttribute("pageTitle" , "Quản lý nhập hàng");
        model.addAttribute("nhaphang" , new ChiTietNhap());
        model.addAttribute("listSp" , sanPhamService.listAll());
        return "nhaphang/nhaphang_form";
    }

    @PostMapping("/nhapHang/save")
    public String saveNhapHang(ChiTietNhap chiTietNhap ,
                               HttpServletRequest request ,
                               RedirectAttributes redirectAttributes) {
        // Lấy ra thông tin người dùng đang đăng nhập
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        NhanVien nhanVien = nhanVienService.findByEmail(email);
        try {
            // Lấy ra size có thể bằng null
            Size size = chiTietNhap.getSize();

            // Lấy ra sản phẩm
            SanPham sanPham = chiTietNhap.getSanPham();

            chiTietNhap.setNhanVien(nhanVien);
            chiTietNhap.setThoiGianNhap(LocalDateTime.now());

            // Lưu nhập hàng
            nhapHangService.saveNhapHang(chiTietNhap);
            // Cập nhật số lượng trong kho
            sizeService.updateSoLuong(chiTietNhap.getSoLuong(), size, sanPham);
            return "redirect:/nhapHang";
        } catch (SanPhamNotFoundException e) {
            redirectAttributes.addFlashAttribute("nhaphang" , chiTietNhap) ;
            redirectAttributes.addFlashAttribute("message" ,
                    "Size của sản phẩm không tìm thấy!") ;
            return "redirect:/nhapHang/new";
        }

    }

    @GetMapping("/nhapHang/size/{sanPhamId}")
    public String formNhapHangWithSize(Model model, @PathVariable("sanPhamId") Integer sanPhamId){
        ChiTietNhap chiTietNhap = new ChiTietNhap() ;
        try {
            // Lấy ra sản phẩm theo id
            SanPham sanPham = sanPhamService.get(sanPhamId);

            chiTietNhap.setSanPham(sanPham);

            // Tìm kiếm các size của sản phẩm đó
            List<Size> sizes = sizeService.findBySanPham(sanPhamId);
            model.addAttribute("readOnly" , false);
            model.addAttribute("pageTitle" , "Quản lý nhập hàng");
            model.addAttribute("nhaphang" , chiTietNhap);
            model.addAttribute("sizes" , sizes);
            model.addAttribute("listSp" , sanPhamService.listAll());
            return "nhaphang/nhaphang_form";
        } catch (SanPhamNotFoundException e) {
            return "nhaphang/nhaphang_form";
        }

    }

    @GetMapping("/nhapHang/edit/{id}")
    public String editNhapHang(@PathVariable("id")Long id , Model model) {
        try {
            boolean readOnly = true ;
            ChiTietNhap chiTietNhap = nhapHangService.get(id) ;
            List<Size> sizes = sizeService.findBySanPham(chiTietNhap.getSanPham().getId());
            model.addAttribute("nhaphang" , chiTietNhap);
            model.addAttribute("sizes" , sizes);
            model.addAttribute("pageTitle" , "Cập nhật nhập hàng");
            model.addAttribute("listSp" , sanPhamService.listAll());
            model.addAttribute("readOnly" , readOnly);
            return "nhaphang/nhaphang_form";
        } catch (SanPhamNotFoundException e) {
            model.addAttribute("message" , e.getMessage());
            return "nhaphang/nhaphang";
        }
    }

    @GetMapping("/nhapHang/page/{pageNum}")
    public String listByPage(
            @Param("sortDir") String sortDir ,
            @Param("sortField") String sortField,
            @Param("keyword") String keyword ,
            @PathVariable("pageNum") int pageNum ,
            Model model
    ){
        // Lấy ra danh sách các phiếu nhập theo các param của một trang
        // môt trang báo gồm `NhapHangService.ctnPerPage`
        Page<ChiTietNhap> page = nhapHangService.listByPage( sortDir, sortField , keyword , pageNum );

        List<ChiTietNhap> listCtn = page.getContent();
        int start = (pageNum - 1) * NhapHangService.ctnPerPage + 1;
        int end = start + NhapHangService.ctnPerPage - 1;
        if(end > page.getTotalElements()) {
            end = (int) page.getTotalElements() ;
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("listCtn", listCtn);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", reverseSortDir);
        return "nhaphang/nhaphang";
    }
}
