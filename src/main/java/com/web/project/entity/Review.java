package com.web.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "reviews")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String headline ;

    @Column(length = 1000)
    private String comment;
    private int rating ;

    @Column(name = "review_time")
    private LocalDateTime reviewTime ;
    @ManyToOne
    @JoinColumn(name = "sanPham_id")
    private SanPham sanPham ;

    @ManyToOne
    @JoinColumn(name = "khachHang_id")
    private NhanVien khachHang;


    @OneToMany(mappedBy = "review")
    private List<ReviewVote> reviewVotes = new ArrayList<>();

    @Transient
    public int getVotes() {
        int sum = 0 ;
        for(ReviewVote reviewVote : reviewVotes) {
            sum+=reviewVote.isVoted() ? 1 : 0 ;
        }
        return sum ;
    }
    @Transient
    public boolean voted ;
}
