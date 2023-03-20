package com.web.project.service;

import com.web.project.entity.HoaDon;
import com.web.project.entity.NhanVien;
import com.web.project.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    public HoaDon saveHoaDon(Double tongTien , NhanVien khachHang ){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        HoaDon hoaDon = new HoaDon(dtf.format(now), tongTien , khachHang );
        return hoaDonRepository.save(hoaDon);
    }

    public List<HoaDon> listAll(){
        return hoaDonRepository.findAll();
    }

    public HoaDon get(Integer id) throws HoaDonNotFoundExeption {
        try{
            HoaDon hoaDon = hoaDonRepository.findById(id).get();
            return  hoaDon;
        }catch (NoSuchElementException ex){
            throw new HoaDonNotFoundExeption("Khong tim thay hoa don nao ca ");
        }

    }
}
