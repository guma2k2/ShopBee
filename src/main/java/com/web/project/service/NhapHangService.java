package com.web.project.service;

import com.web.project.entity.ChiTietNhap;
import com.web.project.entity.SanPham;
import com.web.project.repository.CtnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

@Service
public class NhapHangService {
    public static int ctnPerPage = 10;

    @Autowired
    private CtnRepository ctnRepository;

    public Page<ChiTietNhap> listByPage(String sortDir , String sortField , String keywod , int pageNum){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1 , ctnPerPage , sort);
        if(keywod != null){
            return ctnRepository.findAll(keywod , pageable);
        }
        return ctnRepository.findAll(pageable);
    }
    public void saveNhapHang(ChiTietNhap chiTietNhap) {
        ctnRepository.save(chiTietNhap);
    }

    public ChiTietNhap get(Long id) throws SanPhamNotFoundException {
        ChiTietNhap chiTietNhap =  ctnRepository.findById(id).orElseThrow(() -> new SanPhamNotFoundException("Not found nhap hang")) ;
        return chiTietNhap ;

    }
}
