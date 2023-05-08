package com.web.project.controller;

import com.web.project.Utility;
import com.web.project.entity.ChiTietHoaDon;
import com.web.project.entity.HoaDon;
import com.web.project.entity.NhanVien;
import com.web.project.entity.SanPham;
import com.web.project.repository.HoaDonRepository;
import com.web.project.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class HoaDonController {


    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private CthdService cthdService;

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private ReviewService reviewService ;
    @GetMapping("/hoadon")
    public String listHoaDon(Model model, HttpServletRequest request){
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        List<HoaDon> listHd = hoaDonService.listAll();
        listHd.stream().sorted(Comparator.comparing(HoaDon::getNgayTao));
        NhanVien nhanVien = nhanVienService.findByEmail(email);
        if(!nhanVien.hasRole("Admin") && nhanVien.hasRole("Shipper")) {
            model.addAttribute("listHd" , listHd);
            return "hoadon/hoadon_shipper" ;
        } else {
            model.addAttribute("listHd" , listHd);
            return "hoadon/hoadon";
        }
    }

    @GetMapping("/hoadon/view/{id}")
    public String xemHoaDon(Model model,
                            @PathVariable("id") Integer id,
                            RedirectAttributes redirectAttributes ,
                            HttpServletRequest request){

        try {
            String email = Utility.getEmailOfAuthenticatedCustomer(request) ;
            HoaDon hoaDon = hoaDonService.get(id);
            List<ChiTietHoaDon> listCthd = cthdService.findByIdHoaDon(id);
            setProductReviewStatus(email, listCthd) ;
            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("listCthd" , listCthd);
            return "hoadon/hoadon_tongquan";
        } catch (HoaDonNotFoundExeption e) {
            redirectAttributes.addFlashAttribute("message" , e.getMessage());
            return "redirect:/hoadon";
        }

    }

    private void setProductReviewStatus(String email, List<ChiTietHoaDon> listCthd) {
        for(ChiTietHoaDon chiTietHoaDon : listCthd) {
            SanPham sp = chiTietHoaDon.getSanPham() ;
            boolean didReview = reviewService.didCustomerReviewProduct(email, sp.getId()) ;
//            System.out.println(didReview);
            sp.setDidReview(didReview);
            boolean canReview = reviewService.canCustomerReviewProduct(email, sp.getId()) ;
//            System.out.println(canReview);
            sp.setCanReview(canReview);
        }
    }

    @GetMapping("/orders_shipper/update/{orderId}/{statusName}")
    public String updateStatusOrder(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("statusName") String statusName,
            RedirectAttributes redirectAttributes
    ) {
        try {
            hoaDonService.updateOrderTrack(orderId, statusName);
            return "redirect:/hoadon" ;
        } catch (HoaDonNotFoundExeption e) {
            redirectAttributes.addFlashAttribute("message" , e.getMessage());
            return "redirect:/hoadon" ;
        }
    }

    @GetMapping("/hoadon/modal/return/{orderId}")
    public String modalReturn(@PathVariable("orderId") Integer orderId, Model model) {
        model.addAttribute("orderId" , orderId);
        return "/hoadon/hoadon_return_modal";
    }

    @PostMapping("/hoadon/return/{orderId}")
    public String returnOrder(@PathVariable("orderId") Integer orderId,
                              @RequestParam("returnReason") String reason,
                              @RequestParam("returnNote") String returnNote,
                              RedirectAttributes redirectAttributes) {
        try {
            hoaDonService.returnOrder(orderId, reason, returnNote);
            redirectAttributes.addFlashAttribute("message" , "return order success");
            return "redirect:/history" ;
        } catch (HoaDonNotFoundExeption e) {
            redirectAttributes.addFlashAttribute("message" , e.getMessage());
            return "redirect:/history" ;
        }

    }

}
