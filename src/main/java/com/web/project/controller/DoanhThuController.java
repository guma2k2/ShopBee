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
        Long categories = cthdService.countCategoryByDate(now , now) == null ? 0 : cthdService.countCategoryByDate(now , now);
        Long products = cthdService.countProductByDate(now , now) == null ? 0 : cthdService.countProductByDate(now , now);
        Long bills = hoaDonService.countByDate(now , now) == null ? 0 : hoaDonService.countByDate(now , now);
        Double revenue = hoaDonService.tongDoanhTheoNgay(now, now) == null ? 0 : hoaDonService.tongDoanhTheoNgay(now , now);
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
                                    Model model){
        LocalDate fromDate = LocalDate.parse(from_Date);
        LocalDate toDate = LocalDate.parse(to_Date);
        Long categories = cthdService.countCategoryByDate(fromDate , toDate) == null ? 0 : cthdService.countCategoryByDate(fromDate , toDate);
        Long products = cthdService.countProductByDate(fromDate , toDate) == null ? 0 : cthdService.countProductByDate(fromDate , toDate);
        Long bills = hoaDonService.countByDate(fromDate , toDate) == null ? 0 : hoaDonService.countByDate(fromDate , toDate);
        Double revenue = hoaDonService.tongDoanhTheoNgay(fromDate, toDate) == null ? 0 : hoaDonService.tongDoanhTheoNgay(fromDate , toDate);
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
