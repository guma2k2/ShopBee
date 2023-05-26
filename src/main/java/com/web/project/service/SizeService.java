package com.web.project.service;

import com.web.project.entity.SanPham;
import com.web.project.entity.Size;
import com.web.project.repository.SanPhamRepository;
import com.web.project.repository.SizeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository ;
    @Autowired
    private SanPhamService sanPhamService ;

    public void saveSize(Size size) {
        sizeRepository.save(size);
    }
    public Size get(Long sizeId) throws SanPhamNotFoundException {
        Size size = sizeRepository.findById(sizeId).orElseThrow(() ->  new SanPhamNotFoundException("San pham not found"));
        return size ;
    }

    public List<Size> findBySanPham(Integer sanPhamId) {
        return sizeRepository.findBySanPham(sanPhamId);
    }

    public void updateSoLuong(int soLuong , Size size, SanPham sanPham) throws SanPhamNotFoundException {
        System.out.println(soLuong);
        if (size != null) {
            Long sizeId = size.getId();
            if(size.getSoLuong() == 0) {
                sizeRepository.updateSoLuong(soLuong, sizeId);
                log.info(String.valueOf(size.getSoLuong()));
            } else {
                sizeRepository.updateSoLuong(soLuong + size.getSoLuong() , sizeId);
                log.info(String.valueOf(size.getSoLuong()));
            }
        } else {
            int currentQuantity = sanPham.getSoLuong();
            int newQuantity = soLuong + currentQuantity ;
            sanPhamService.updateQuantity(newQuantity, sanPham);
        }
    }
}
