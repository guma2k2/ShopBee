package com.web.project.controller;

import com.web.project.Utility;
import com.web.project.entity.*;
import com.web.project.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;


    @Autowired
    private OrderTrackService orderTrackService ;

    @Autowired
    private NhanVienService khachHangService;

    @Autowired
    private HoaDonService hoaDonService ;

    @Autowired
    private CthdService cthdService;

    @Autowired
    private SizeService sizeService;


    @Autowired
    private SanPhamService sanPhamService;




    @PostMapping("/cart/add/{sanphamId}")
    public String addToCart(@PathVariable("sanphamId") Integer sanphamId ,
                            @RequestParam("quantity") int soluong ,
                            @RequestParam("idSize") Long idSize ,
                            HttpServletRequest request ,
                            RedirectAttributes re){
        try {
            // Láy size theo id, nếu != null sẽ return, ngược lại trả về null
            Size size = idSize != 0 ? sizeService.get(idSize) : null;
            // Kiểm tra số lượng còn lại của sản phẩm theo 2 trường hợp:
            // 1) Size != null : lấy số lương của sản phẩm theo size
            // 2) Size == null : lấy số lượng của sản phẩm
            long currentQuantity ;
            if(size != null) {
                currentQuantity = size.getSoLuong();
            } else {
                currentQuantity =  sanPhamService.get(sanphamId).getSoLuong();
            }
            // Nếu sô lượng tồn nhỏ hơn số lượng của request thì bão lỗi
            if(currentQuantity < soluong) {
                re.addFlashAttribute("message", "Sản phẩm chỉ còn " +size.getSoLuong() +
                        " cái !!");
                return "redirect:/product_details/" + sanphamId;
            }
            // Lấy thông tin khách hàng theo request
            NhanVien khachHang = getKhachHang(request);
            cartService.addToCart(sanphamId , khachHang , soluong , idSize);
            re.addFlashAttribute("message" , "Thêm thành công vào giỏ hàng !!");
            return "redirect:/product_details/" + sanphamId;
        } catch (NhanVienNotFoundException e) {
            return "login";
        } catch (SanPhamNotFoundException e) {
            re.addFlashAttribute("message" , "Sản phẩm không được tìm thấy");
            return "redirect:/product_details/" + sanphamId;
        }

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


    @PostMapping("/cart/updateMinus/{sanPhamId}/{sizeId}")
    public String updateMinus(@PathVariable("sanPhamId")Integer sanPhamId,
                              @PathVariable("sizeId")Long sizeId,
                              HttpServletRequest request ,
                              Model model ,
                              RedirectAttributes redirectAttributes){
        try {
            // Lấy email của người dùng đã đăng nhập vào hệ thống
            String email = Utility.getEmailOfAuthenticatedCustomer(request);
            NhanVien khachHang = khachHangService.findByEmail(email);

            // Giảm số lượng của sản phẩm trong giỏ hàng
            cartService.updateSoLuongMinus(khachHang,sanPhamId, sizeId);
            // Lấy tất cả các giỏ hàng theo user
            List<Cart> gioHang = cartService.findByKhachHang(khachHang);
            // Tính tổng tiền của tất cả sản phẩm trong giỏ hàng
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
    @PostMapping("/cart/updatePlus/{sanPhamId}/{sizeId}")
    public String updatePlus(@PathVariable("sanPhamId")Integer sanPhamId,
                             @PathVariable("sizeId")Long sizeId,
                             HttpServletRequest request ,
                             Model model ,
                             RedirectAttributes redirectAttributes){
        try {
            // Lấy email của người dùng đã đăng nhập vào hệ thống
            String email = Utility.getEmailOfAuthenticatedCustomer(request);
            NhanVien khachHang = khachHangService.findByEmail(email);
            // Tăng số lượng của sản phẩm trong giỏ hàng
            cartService.updateSoLuongPlus(khachHang,sanPhamId, sizeId);

            // Lấy tất cả các giỏ hàng theo user
            List<Cart> gioHang = cartService.findByKhachHang(khachHang);

            // Tính tổng tiền của tất cả sản phẩm trong giỏ hàng
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
    @GetMapping("/cart/remove/{cartId}")
    public String removeCart(@PathVariable("cartId")Integer cartId,
                             HttpServletRequest request ,
                             Model model ,
                             RedirectAttributes redirectAttributes){
        try {
            // Lấy email của người dùng đã đăng nhập vào hệ thống
            String email = Utility.getEmailOfAuthenticatedCustomer(request);
            NhanVien khachHang = khachHangService.findByEmail(email);

            // Remove 1 trong số các sản phẩm trong giỏ hàng
            cartService.removeCart(cartId);
            List<Cart> gioHang = cartService.findByKhachHang(khachHang);

            // Tính tổng tiền của tất cả sản phẩm trong giỏ hàng sau khi xóa 1 giỏ hàng
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
        // Lấy email của người dùng đã đăng nhập
        String email = Utility.getEmailOfAuthenticatedCustomer(request);

        // Lấy ra khách hàng từ database
        NhanVien khachHang = khachHangService.findByEmail(email);

        // Liệt kê tất cả các sản phẩm trong giỏ hàng theo người dùng
        List<Cart> gioHang = cartService.findByKhachHang(khachHang);

        // Tính tổng tiền của giỏ hàng
        Double tongTien = Double.valueOf(gioHang
                .stream()
                .map(g -> g.getTongGia())
                .reduce(0,(g1 , g2) -> g1+g2));

        // Luư hóa đơn vói tổng tìền và khách hàng
        HoaDon order = hoaDonService.saveHoaDon(tongTien,khachHang);
        // Tạo ra và lưu Order track với trạnh thái `NEW`
        OrderTrack track = new OrderTrack();
        track.setOrder(order);
        track.setUpdatedTime(LocalDateTime.now());
        track.setNotes("Đon đặt hàng đã được đặt");
        track.setStatus(OrderStatus.NEW);
        orderTrackService.saveOrderTrack(track);


        for(Cart cart : gioHang){
            // lấy số lượng sản phẩm của cart đó
            int soLuong = cart.getSoLuong();

            // láy ra size đã chọn trong giỏ hàng
            Size size = cart.getSize();

            // Lấy ra sản phẩm đã chọn trong giỏ hàng
            SanPham sanPham = cart.getSanPham();

            // Kiểm tra xem số lượng yêu cầu của sản phẩm trong giỏ hàng phải nhỏ hơn hoặc bằng số lượng trong kho
            int currentQuantity = size != null ?  size.getSoLuong() : sanPham.getSoLuong() ;
            if(currentQuantity < soLuong) {
                redirectAttributes.addFlashAttribute("message",
                        "Sản phẩm chỉ còn " + size.getSoLuong() + " cái !!");
                return "redirect:/cart";
            }
            // Lấy ra tổng giá tiền của giỏ hàng đó
            Double gia = Double.valueOf(cart.getTongGia());

            try {
                // Update sô lượng trong kho
                sizeService.updateSoLuong( -cart.getSoLuong() , size , sanPham);
            } catch (SanPhamNotFoundException e) {
                redirectAttributes.addFlashAttribute("message","Sản phẩm không được tìm thấy");
                return "redirect:/cart";
            }
            // Lưu chi tiết hóa đơn của hóa đơn đó
            cthdService.save(soLuong,gia,sanPham,order, size);
        }
        // Sau khi lưu thành công hóa đơn sẽ xóa tất cả giỏ hàng của người dùng
        cartService.removeCartByKhachHang(khachHang);
        redirectAttributes.addFlashAttribute("message","Hóa đơn đã được thanh toán thành công");
        return "redirect:/cart";
    }



}
