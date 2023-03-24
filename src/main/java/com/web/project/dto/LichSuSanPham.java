package com.web.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LichSuSanPham {
    private Integer idSanPham ;
    private String tenSanPham;
    private int soLuong ;
    private Double gia;
}
