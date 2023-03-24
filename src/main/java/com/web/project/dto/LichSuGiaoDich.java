package com.web.project.dto;

import com.web.project.entity.SanPham;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LichSuGiaoDich {
    private Integer idHoaDon ;
    private LocalDateTime ngayGiaoDich ;

    private List<LichSuSanPham> listSP = new ArrayList<>();

    private Double tongTien ;

    public void addSanPham(LichSuSanPham sanPham){
        this.listSP.add(sanPham);
    }

}
