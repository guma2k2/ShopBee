package com.web.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Table(name = "setting")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Setting {
    @Id
    @Column( name = "`key`",nullable = false , unique = true , length = 20)
    private String key ;

    @Column(nullable = false, length = 100)
    private String value ;

    @Column(name = "loai" ,nullable = false )
    @Enumerated(EnumType.STRING)
    private LoaiSetting loaiSetting;



}
