package com.web.project.service;

import com.web.project.entity.Cart;
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

    public void addToCart(Integer sanphamId  , NhanVien khachHang , int soLuong) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanphamId);
        int updatedQuantity = soLuong;
        Cart cart = repo.findByKhachHangAndSanPham(khachHang , sanPham);
        if(cart != null){
            updatedQuantity+=cart.getSoLuong();
        }else{
            cart = new Cart();
            cart.setKhachHang(khachHang);
            cart.setSanPham(sanPham);
        }
        cart.setSoLuong(updatedQuantity);
        repo.save(cart);
    }

    public List<Cart>  findByKhachHang(NhanVien khachHang){
        return repo.findByKhachHang(khachHang);
    }

    public void updateSoLuongMinus(NhanVien khachHang , Integer sanPhamId) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanPhamId);
        Cart cart = repo.findByKhachHangAndSanPham(khachHang , sanPham);
        int soLuong = cart.getSoLuong();
        if(soLuong > 1){
            soLuong-=1;
        }
        repo.updateSoLuong(khachHang ,sanPham , soLuong);
    }
    public void updateSoLuongPlus(NhanVien khachHang , Integer sanPhamId) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanPhamId);
        Cart cart = repo.findByKhachHangAndSanPham(khachHang , sanPham);
        int soLuong = cart.getSoLuong();
        repo.updateSoLuong(khachHang,sanPham,soLuong+1);
    }

    public void removeCart(NhanVien khachHang , Integer sanPhamId) throws SanPhamNotFoundException {
        SanPham sanPham = sanPhamService.get(sanPhamId);
        repo.removeCart(khachHang , sanPham);
    }

    public void removeCartByKhachHang(NhanVien khachHang){
        repo.removeCartByKhachHang(khachHang);
    }
}
