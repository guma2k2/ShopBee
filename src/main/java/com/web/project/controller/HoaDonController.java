package com.web.project.controller;

import com.web.project.entity.ChiTietHoaDon;
import com.web.project.entity.HoaDon;
import com.web.project.repository.HoaDonRepository;
import com.web.project.service.CthdService;
import com.web.project.service.HoaDonNotFoundExeption;
import com.web.project.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class HoaDonController {


    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private CthdService cthdService;
    @GetMapping("/hoadon")
    public String listHoaDon(Model model){
        List<HoaDon> listHd = hoaDonService.listAll();
        listHd.stream().sorted(Comparator.comparing(HoaDon::getNgayTao));
        model.addAttribute("listHd" , listHd);
        return "hoadon/hoadon";
    }

    @GetMapping("/hoadon/view/{id}")
    public String xemHoaDon(Model model,
                            @PathVariable("id") Integer id,
                            RedirectAttributes redirectAttributes){

        try {
            HoaDon hoaDon = hoaDonService.get(id);
            List<ChiTietHoaDon> listCthd = cthdService.findByIdHoaDon(id);
            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("listCthd" , listCthd);
            return "hoadon/hoadon_chitiet";
        } catch (HoaDonNotFoundExeption e) {
            redirectAttributes.addFlashAttribute("message" , e.getMessage());
            return "redirect:/hoadon";
        }

    }

}
