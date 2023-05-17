package com.web.project.service;

import com.web.project.entity.*;
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

    @Autowired
    private SizeService sizeService ;

    public void addToCart(Integer sanphamId  , NhanVien khachHang , int soLuong, Long sizeId) throws SanPhamNotFoundException {
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        SanPham sanPham = sanPhamService.get(sanphamId);
        Size size = sizeService.get(sizeId);
        int updatedQuantity = soLuong;
        Cart cart = repo.findByKhachHangAndSanPhamSize(customer , sanPham , size);
        if(cart != null){
            updatedQuantity+=cart.getSoLuong();
        }else{
            cart = new Cart();
            cart.setKhachHang(customer);
            cart.setSanPham(sanPham);
            cart.setSize(size);
        }
        cart.setSoLuong(updatedQuantity);
        repo.save(cart);
    }

    public List<Cart>  findByKhachHang(NhanVien khachHang){
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        return repo.findByKhachHang(customer);
    }

    public void updateSoLuongMinus(NhanVien khachHang , Integer sanPhamId, Long sizeId) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanPhamId);
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        Size size = sizeService.get(sizeId);
        Cart cart = repo.findByKhachHangAndSanPhamSize(customer , sanPham , size);
        int soLuong = cart.getSoLuong();
        if(soLuong > 1){
            soLuong-=1;
        }
        repo.updateSoLuong(cart.getId(),soLuong);
    }
    public void updateSoLuongPlus(NhanVien khachHang , Integer sanPhamId, Long sizeId) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanPhamId);
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        Size size = sizeService.get(sizeId);
        Cart cart = repo.findByKhachHangAndSanPhamSize(customer , sanPham, size);
        int soLuong = cart.getSoLuong();
        repo.updateSoLuong(cart.getId(),soLuong+1);
    }

    public void removeCart(Integer cartId) throws SanPhamNotFoundException {
        repo.removeCart(cartId);
    }

    public void removeCartByKhachHang(NhanVien khachHang){
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        repo.removeCartByKhachHang(customer);
    }
}
