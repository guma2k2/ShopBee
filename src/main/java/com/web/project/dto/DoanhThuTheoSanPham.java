package com.web.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DoanhThuTheoSanPham {
    private Integer idSanPham ;
    private String tenSanPham;
    private Long soLuong ;
    private Double tongTien ;
}
