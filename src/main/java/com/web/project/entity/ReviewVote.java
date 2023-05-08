package com.web.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviewVote")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review ;

    @ManyToOne
    @JoinColumn(name = "khachHang_id")
    private NhanVien khachHang ;

    private boolean voted  ; // only vote Up : if true : was vote else false : undo voted
}
