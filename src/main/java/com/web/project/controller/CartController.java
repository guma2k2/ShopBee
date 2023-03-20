package com.web.project.controller;

import com.web.project.Utility;
import com.web.project.entity.Cart;
import com.web.project.entity.HoaDon;
import com.web.project.entity.NhanVien;
import com.web.project.entity.SanPham;
import com.web.project.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private NhanVienService khachHangService;

    @Autowired
    private HoaDonService hoaDonService ;

    @Autowired
    private CthdService cthdService;



    @PostMapping("/cart/add/{sanphamId}")
    public String addToCart(@PathVariable("sanphamId") Integer sanphamId ,
                            @RequestParam("quantity") int soluong ,
                            HttpServletRequest request ,
                            RedirectAttributes re){
        try {
            NhanVien khachHang = getKhachHang(request);
            cartService.addToCart(sanphamId , khachHang , soluong);
            re.addFlashAttribute("message" , "Thêm thành công vào giỏ hàng !!");
        } catch (NhanVienNotFoundException e) {
            return "login";
        } catch (SanPhamNotFoundException e) {

        }
        return "redirect:/product_details/" + sanphamId;
    }

    private NhanVien getKhachHang(HttpServletRequest request) throws NhanVienNotFoundException {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        if(email == null){
            throw new NhanVienNotFoundException("Vui lòng đăng nhâp ");
        }
        return khachHangService.findByEmail(email);
    }

    @GetMapping("/cart")
    public String gioHang(HttpServletRequest request,
                          Model model){
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        NhanVien khachHang = khachHangService.findByEmail(email);
        List<Cart> gioHang = cartService.findByKhachHang(khachHang);
        Long tongTien = Long.valueOf(gioHang.stream().map(g -> g.getTongGia()).reduce(0,(g1 , g2) -> g1+g2));
        model.addAttribute("tongTien" , tongTien);
        model.addAttribute("gioHang" , gioHang);
        return "cart/cart";
    }


    @PostMapping("/cart/updateMinus/{sanPhamId}")
    public String updateMinus(@PathVariable("sanPhamId")Integer sanPhamId,
                              HttpServletRequest request ,
                              Model model ,
                              RedirectAttributes redirectAttributes){
        try {
            String email = Utility.getEmailOfAuthenticatedCustomer(request);
            NhanVien khachHang = khachHangService.findByEmail(email);
            cartService.updateSoLuongMinus(khachHang,sanPhamId);
            List<Cart> gioHang = cartService.findByKhachHang(khachHang);
            Long tongTien = Long.valueOf(gioHang
                    .stream()
                    .map(g -> g.getTongGia())
                    .reduce(0,(g1 , g2) -> g1+g2));
            model.addAttribute("tongTien" , tongTien);
            model.addAttribute("gioHang" , gioHang);
            return "redirect:/cart";
        } catch (SanPhamNotFoundException e) {
            redirectAttributes.addFlashAttribute("message" ,e.getMessage());
            return "cart/cart";
        }

    }
    @PostMapping("/cart/updatePlus/{sanPhamId}")
    public String updatePlus(@PathVariable("sanPhamId")Integer sanPhamId,
                             HttpServletRequest request ,
                             Model model ,
                             RedirectAttributes redirectAttributes){
        try {
            String email = Utility.getEmailOfAuthenticatedCustomer(request);
            NhanVien khachHang = khachHangService.findByEmail(email);
            cartService.updateSoLuongPlus(khachHang,sanPhamId);
            List<Cart> gioHang = cartService.findByKhachHang(khachHang);
            Long tongTien = Long.valueOf(gioHang
                    .stream()
                    .map(g -> g.getTongGia())
                    .reduce(0,(g1 , g2) -> g1+g2));
            model.addAttribute("tongTien" , tongTien);
            model.addAttribute("gioHang" , gioHang);
            return "redirect:/cart";
        } catch (SanPhamNotFoundException e) {
            redirectAttributes.addFlashAttribute("message" ,e.getMessage());
            return "cart/cart";
        }
    }
    @GetMapping("/cart/remove/{sanPhamId}")
    public String removeCart(@PathVariable("sanPhamId")Integer sanPhamId,
                             HttpServletRequest request ,
                             Model model ,
                             RedirectAttributes redirectAttributes){
        try {
            String email = Utility.getEmailOfAuthenticatedCustomer(request);
            NhanVien khachHang = khachHangService.findByEmail(email);
            cartService.removeCart(khachHang,sanPhamId);
            List<Cart> gioHang = cartService.findByKhachHang(khachHang);
            Long tongTien = Long.valueOf(gioHang
                    .stream()
                    .map(g -> g.getTongGia())
                    .reduce(0,(g1 , g2) -> g1+g2));
            model.addAttribute("tongTien" , tongTien);
            model.addAttribute("gioHang" , gioHang);
            redirectAttributes.addFlashAttribute("message","Xóa thành công !!");
            return "redirect:/cart";
        } catch (SanPhamNotFoundException e) {
            redirectAttributes.addFlashAttribute("message" ,e.getMessage());
            return "cart/cart";
        }
    }
    @GetMapping("/hoadon/save")
    public String luuHoaDon(HttpServletRequest request,
                            RedirectAttributes redirectAttributes){
        // find all cart of kh
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        NhanVien khachHang = khachHangService.findByEmail(email);
        List<Cart> gioHang = cartService.findByKhachHang(khachHang);
        // save hd
        Double tongTien = Double.valueOf(gioHang
                .stream()
                .map(g -> g.getTongGia())
                .reduce(0,(g1 , g2) -> g1+g2));
        HoaDon hoaDon = hoaDonService.saveHoaDon(tongTien,khachHang);

        // save cthd
        for(Cart cart : gioHang){
            int soLuong = cart.getSoLuong();
            Double gia = Double.valueOf(cart.getTongGia());
            SanPham sanPham = cart.getSanPham();
            cthdService.save(soLuong,gia,sanPham,hoaDon);
        }
        cartService.removeCartByKhachHang(khachHang);
        redirectAttributes.addFlashAttribute("message","Hóa đơn đã được thanh toán thành công");
        return "redirect:/cart";
    }



}
