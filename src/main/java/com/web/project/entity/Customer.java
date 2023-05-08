package com.web.project.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "khachHang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true)
    private String email;
    private String ho ;
    private String ten;
    private String sdt;
    private String password;
    private String photos ;
    private String diaChi ;
    @Transient
    public String getFullName(){
        return this.ho + " " + this.ten;
    }
    @Transient
    public String getPhotosImagePath() {
        if (id == null || photos == null) return "/images/default-user.png";
        return "/nhanvien-photos/" + this.id + "/" + this.photos;
    }
}
