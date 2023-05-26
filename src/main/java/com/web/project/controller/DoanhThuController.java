package com.web.project.controller;

import com.web.project.dto.DoanhThuTheoSanPham;
import com.web.project.service.CthdService;
import com.web.project.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DoanhThuController {


    @Autowired
    private CthdService cthdService;

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/doanhthu")
    public String doanhThu(Model model){
        LocalDate now = LocalDate.now();
        // Lấy ra số lương các loại sản phẩm trong tất cả đơn đặt hàng
        Long categories = cthdService.countCategoryByDate(now , now) == null ?
                0 : cthdService.countCategoryByDate(now , now);

        // Lấy ra số lương các sản phẩm trong tất cả đơn đặt hàng
        Long products = cthdService.countProductByDate(now , now) == null ?
                0 : cthdService.countProductByDate(now , now);

        // Lấy ra số lương các đơn đặt hàng trong tất cả đơn đặt hàng
        Long bills = hoaDonService.countByDate(now , now) == null ?
                0 : hoaDonService.countByDate(now , now);

        // Tỉnh tổng doanh thu
        Double revenue = hoaDonService.tongDoanhTheoNgay(now, now) == null ?
                0 : hoaDonService.tongDoanhTheoNgay(now , now);

        // Lấy ra top 10 sẩn phẩm có doanh thu cao nhất
        List<DoanhThuTheoSanPham> listDoanhThu = cthdService.findDoanhThuByThoiGian(now , now );
        model.addAttribute("categories" ,categories );
        model.addAttribute("products" , products);
        model.addAttribute("bills",bills);
        model.addAttribute("revenue",revenue);
        model.addAttribute("listDoanhThu",listDoanhThu);
        model.addAttribute("from_date" , now);
        model.addAttribute("to_date" , now);
        return "doanhthu/doanhthu";
    }
    @PostMapping("/doanhthu/search")
    public String doanhTheoThoiGian(@RequestParam("from_date") String from_Date ,
                                    @RequestParam("to_date") String to_Date ,
                                    Model model ,
                                    RedirectAttributes redirectAttributes){
        // Chuyển đổi string từ các input thành LocalDate
        LocalDate fromDate = LocalDate.parse(from_Date);
        LocalDate toDate = LocalDate.parse(to_Date);

        // Kiểm tra ràng buộc giũa ngày bắt đầu và ngày kết thúc
        if(toDate.isBefore(fromDate)) {
            redirectAttributes.addFlashAttribute("message" ,
                    "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc");
            return "redirect:/doanhthu";
        }
        // Lấy ra số lương các loại sản phẩm trong tất cả đơn đặt hàng
        Long categories = cthdService.countCategoryByDate(fromDate , toDate) == null ?
                0 : cthdService.countCategoryByDate(fromDate , toDate);

        // Lấy ra số lương các sản phẩm trong tất cả đơn đặt hàng
        Long products = cthdService.countProductByDate(fromDate , toDate) == null ?
                0 : cthdService.countProductByDate(fromDate , toDate);

        // Lấy ra số lương các hóa đơn trong tất cả đơn đặt hàng
        Long bills = hoaDonService.countByDate(fromDate , toDate) == null ?
                0 : hoaDonService.countByDate(fromDate , toDate);

        // Tính tổng doanh thu
        Double revenue = hoaDonService.tongDoanhTheoNgay(fromDate, toDate) == null ?
                0 : hoaDonService.tongDoanhTheoNgay(fromDate , toDate);

        // Lấy ra danh sách top 10 sản phẩm có doanh thu cao nhất sắp xếp giảm dần
        List<DoanhThuTheoSanPham> listDoanhThu = cthdService.findDoanhThuByThoiGian(fromDate , toDate );
        model.addAttribute("categories" ,categories );
        model.addAttribute("products" , products);
        model.addAttribute("bills",bills);
        model.addAttribute("revenue",revenue);
        model.addAttribute("listDoanhThu",listDoanhThu);
        model.addAttribute("from_date" , fromDate);
        model.addAttribute("to_date" , toDate);
        return "doanhthu/doanhthu";
    }
}
