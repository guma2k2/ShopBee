package com.web.project.repository;

import com.web.project.entity.ReviewVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewVoteRepository extends JpaRepository<ReviewVote, Long> {

    @Query("SELECT v FROM ReviewVote v WHERE v.khachHang.email = ?1 AND v.review.id = ?2")
    Optional<ReviewVote> findByCustomerAndReview(String email, Long reviewId) ;

    @Query("UPDATE ReviewVote v SET v.voted = ?2 WHERE v.id = ?1")
    @Modifying
    void updateVote(Long reviewVoteId, boolean voted) ;
}
