package com.web.project.service;

import com.web.project.entity.Size;
import com.web.project.repository.SizeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository ;

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

    public void updateSoLuong(int soLuong , Long sizeId) throws SanPhamNotFoundException {
        Size size =  get(sizeId);
        System.out.println(soLuong);
        if(size.getSoLuong() == 0) {
            sizeRepository.updateSoLuong(soLuong, sizeId);
            System.out.println(size.getSoLuong());
        } else {
            System.out.println(size.getSoLuong());
            sizeRepository.updateSoLuong(soLuong + size.getSoLuong() , sizeId);
        }
    }
}
