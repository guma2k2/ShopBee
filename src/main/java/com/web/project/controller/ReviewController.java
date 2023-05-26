package com.web.project.controller;

import com.web.project.Utility;
import com.web.project.entity.NhanVien;
import com.web.project.entity.Review;
import com.web.project.entity.SanPham;
import com.web.project.repository.ReviewRepository;
import com.web.project.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService ;

    @Autowired
    private SanPhamService sanPhamService ;

    @Autowired
    private NhanVienService nhanVienService ;

    @GetMapping("/review/write/{sanPhamId}")
    public String writeReview(@PathVariable("sanPhamId") Integer sanPhamId,
                              Model model,
                              RedirectAttributes redirectAttributes ,
                              HttpServletRequest request) {
        SanPham sanPham = new SanPham() ;
        String email = Utility.getEmailOfAuthenticatedCustomer(request) ;
        try {
            sanPham = sanPhamService.get(sanPhamId);
        } catch (SanPhamNotFoundException e) {
            redirectAttributes.addFlashAttribute("message" , "Không tìm thấy sản phẩm này") ;
            return "error/404" ;
        }
        boolean customerReviewed = reviewService.didCustomerReviewProduct(email, sanPhamId) ;
        boolean NoReviewPermission = reviewService.canCustomerReviewProduct(email, sanPhamId) == false ;
        boolean customerCanReview = reviewService.canCustomerReviewProduct(email, sanPhamId) == true;
        model.addAttribute("sanPham" , sanPham) ;
        model.addAttribute("sanPhamId", sanPhamId) ;
        model.addAttribute("customerReviewed" ,  customerReviewed) ;
        model.addAttribute("NoReviewPermission" ,  NoReviewPermission) ;
        model.addAttribute("customerCanReview" ,  customerCanReview) ;
        model.addAttribute("review" , new Review()) ;
        return "review/review" ;
    }
    @PostMapping("/post_review")
    public String saveReview(Review review, HttpServletRequest request ,
                             Model model ,
                             @RequestParam("sanPhamId") Integer sanPhamId,
                             RedirectAttributes redirectAttributes) {
        try {
            // Lấy ra sản phẩm theo Id
            SanPham sanPham = sanPhamService.get(sanPhamId) ;

            // Lấy ra thông tin khách hàng đã đăng nhập
            String email = Utility.getEmailOfAuthenticatedCustomer(request) ;
            NhanVien customer = nhanVienService.findByEmail(email) ;

            // Lưu thông tìn Review mà khách hàng đã nhập thông tin
            Review savedReview = reviewService.saveReview(review , customer , sanPham) ;

            model.addAttribute("review" , savedReview ) ;
            model.addAttribute("sanPham" , sanPham) ;
            return "review/review_success" ;
        } catch (SanPhamNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", "San pham khong tim thay") ;
            return "error/404" ;
        }
    }
    @GetMapping("/review")
    public String listByPage(Model model , HttpServletRequest request) {
        return listByPage(1 , model ,"reviewTime" , "desc" ,null, request );
    }

    @GetMapping("/review/page/{pageNum}")
    public String listByPage(@PathVariable("pageNum") int pageNum , Model model ,
                             @Param("sortField") String sortField ,
                             @Param("sortDir") String sortDir ,
                             @Param("keyword")String keyword ,
                             HttpServletRequest request) {
        // Lấy thông tin user đã đăng nhập vào hệ thống
        String email = Utility.getEmailOfAuthenticatedCustomer(request) ;
        NhanVien user = nhanVienService.findByEmail(email) ;

        // Lấy ra mốt trang danh sách Review
        Page<Review> page = reviewService.listByPage(pageNum , sortField , sortDir , keyword, email);
        List<Review> reviews = page.getContent();
        int start = (pageNum - 1) * ReviewService.reviewPerPage + 1;
        int end = start + ReviewService.reviewPerPage - 1;
        if(end > page.getTotalElements()) {
            end = (int) page.getTotalElements() ;
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("reviews", reviews);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("reverseSortDir", reverseSortDir);
        // Nếu user có role `Customer` thì chuyển về trang đánh giá sản phẩm của khách hàng
        if(user.hasRole("Customer")) {
            return "review/review_customer";
        }
        // Nếu user có role `Admin` thì chuyển về trang đánh giá sản phẩm của Admin
        return "review/review_admin";
    }

    @GetMapping("/review/view/{reviewId}")
    public String viewDetailReview(@PathVariable("reviewId")Long reviewId,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        try {
            // Lấy ra Review theo id
            Review review = reviewService.get(reviewId) ;
            model.addAttribute("review" , review) ;
            return "review/review_detail_modal" ;
        } catch (ReviewNotFoundException e) {
            redirectAttributes.addFlashAttribute("message" , e.getMessage()) ;
            return "error/404" ;
        }
    }
    @GetMapping("/review/delete/{reviewid}")
    public String deleteReview(@PathVariable("reviewId") Long reviewId ,
                               RedirectAttributes redirectAttributes) {
        try {
            reviewService.deleteReviewById(reviewId);
            return "redirect:/review";
        } catch (ReviewNotFoundException e) {
            redirectAttributes.addFlashAttribute("message" , e.getMessage());
            return "redirect:/review";
        }
    }
}
