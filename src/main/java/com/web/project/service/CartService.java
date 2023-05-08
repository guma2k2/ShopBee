package com.web.project.service;

import com.web.project.entity.Cart;
import com.web.project.entity.Customer;
import com.web.project.entity.NhanVien;
import com.web.project.entity.SanPham;
import com.web.project.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository repo;

    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private CustomerService customerService ;

    public void addToCart(Integer sanphamId  , NhanVien khachHang , int soLuong) throws SanPhamNotFoundException {
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        SanPham sanPham = sanPhamService.get(sanphamId);
        int updatedQuantity = soLuong;
        Cart cart = repo.findByKhachHangAndSanPham(customer , sanPham);
        if(cart != null){
            updatedQuantity+=cart.getSoLuong();
        }else{
            cart = new Cart();
            cart.setKhachHang(customer);
            cart.setSanPham(sanPham);
        }
        cart.setSoLuong(updatedQuantity);
        repo.save(cart);
    }

    public List<Cart>  findByKhachHang(NhanVien khachHang){
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        return repo.findByKhachHang(customer);
    }

    public void updateSoLuongMinus(NhanVien khachHang , Integer sanPhamId) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanPhamId);
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        Cart cart = repo.findByKhachHangAndSanPham(customer , sanPham);
        int soLuong = cart.getSoLuong();
        if(soLuong > 1){
            soLuong-=1;
        }
        repo.updateSoLuong(customer ,sanPham , soLuong);
    }
    public void updateSoLuongPlus(NhanVien khachHang , Integer sanPhamId) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanPhamId);
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        Cart cart = repo.findByKhachHangAndSanPham(customer , sanPham);
        int soLuong = cart.getSoLuong();
        repo.updateSoLuong(customer,sanPham,soLuong+1);
    }

    public void removeCart(NhanVien khachHang , Integer sanPhamId) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanPhamId);
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        repo.removeCart(customer , sanPham);
    }

    public void removeCartByKhachHang(NhanVien khachHang){
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        repo.removeCartByKhachHang(customer);
    }
}
