package com.web.project.controller;

import com.web.project.FileUploadUtil;
import com.web.project.entity.LoaiSanPham;
import com.web.project.entity.SanPham;
import com.web.project.service.LoaiSanPhamService;
import com.web.project.service.NhanVienService;
import com.web.project.service.SanPhamNotFoundException;
import com.web.project.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamService service ;

    @Autowired
    private LoaiSanPhamService loaiService;


    @GetMapping("sanpham")
    public String sanPhamPage(Model model){
        return listByPage("asc" , "id" , null , 1 , model);
    }

    @GetMapping("/sanpham/page/{pageNum}")
    public String listByPage(
            @Param("sortDir") String sortDir ,
            @Param("sortField") String sortField,
            @Param("keyword") String keyword ,
            @PathVariable("pageNum") int pageNum ,
            Model model
    ){
        Page<SanPham> page = service.listByPage( sortDir, sortField , keyword , pageNum );
        List<SanPham> listSP = page.getContent();
        int start = (pageNum - 1) * SanPhamService.sanPhamMoiPage + 1;
        int end = start + SanPhamService.sanPhamMoiPage - 1;
        if(end > page.getTotalElements()) {
            end = (int) page.getTotalElements() ;
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("listSP", listSP);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", reverseSortDir);
        return "sanpham/sanpham";
    }

    @GetMapping("/sanpham/new")
    public String themSanPham(Model model){
        List<LoaiSanPham> listLoaiSp = loaiService.listAll();
        model.addAttribute("pageTitle" , "Thêm sản phẩm");
        model.addAttribute("sanpham", new SanPham());
        model.addAttribute("listLoaiSp" , listLoaiSp);

        return "sanpham/sanpham_form";
    }
    @PostMapping("/sanpham/save")
    public String luuSanPham(RedirectAttributes redirectAttributes ,
                             Model model , SanPham sanPham ,
                             @RequestParam("image")MultipartFile multipartFile)
                             throws IOException {
        if(!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            sanPham.setAnh(fileName);
            SanPham savedSp = service.save(sanPham);
            String uploadDir = "sanpham-photos/" + savedSp.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }else {
            if(sanPham.getAnh().isEmpty()) sanPham.setAnh(null);
            service.save(sanPham);
        }
        redirectAttributes.addFlashAttribute("message", "Sản phẩm này đã được lưu thành công");
        return "redirect:/sanpham";
    }
    @GetMapping("/sanpham/edit/{id}")
    public String edit(@PathVariable("id") Integer id ,RedirectAttributes redirectAttributes ,Model model){
        try {
            SanPham sanPham = service.get(id);
            List<LoaiSanPham> listLoaiSp = loaiService.listAll();
            model.addAttribute("sanpham" , sanPham);
            model.addAttribute("pageTitle" , "Sửa sản phẩm với id là " + id);
            model.addAttribute("listLoaiSp" , listLoaiSp);
            return "sanpham/sanpham_form";
        } catch (SanPhamNotFoundException e) {
            redirectAttributes.addFlashAttribute("message" , e.getMessage());
            return "redirect:/sanpham";
        }
    }
    @GetMapping("/sanpham/{id}/enabled/{status}")
    public String updateTrangThai(@PathVariable("id")Integer id , @PathVariable("status")boolean status ,RedirectAttributes re) {
        service.updateTrangThai(id, status);
        re.addFlashAttribute("message" ," Cập nhật trạng thái thành công");
        return "redirect:/sanpham";
    }

    @GetMapping("/sanpham/delete/{id}")
    public String delete(@PathVariable("id")Integer id ,RedirectAttributes redirectAttributes){
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "Xóa sản phầm thành công");
        } catch (SanPhamNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/sanpham";
    }




}
