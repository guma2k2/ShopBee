package com.web.project.service;

import com.web.project.entity.*;
import com.web.project.repository.CthdRepository;
import com.web.project.repository.ReviewRepository;
import com.web.project.repository.ReviewVoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository ;

    @Autowired
    private  CthdRepository cthdRepository ;

    @Autowired NhanVienService nhanVienService ;
    @Autowired CustomerService customerService ;

    @Autowired
    private ReviewVoteRepository reviewVoteRepository ;

    public static int reviewPerPage = 5;

    public void saveOrUpdateVote(String email, Long reviewId) throws ReviewNotFoundException {
        Review review = get(reviewId) ;
        Customer khachHang = customerService.findByEmailCustomer(email) ;
        Optional<ReviewVote> vote  = reviewVoteRepository.findByCustomerAndReview(email, reviewId);
        boolean update = vote.isPresent();
        if(!update) {
            ReviewVote reviewVote= new ReviewVote();
            reviewVote.setReview(review);
            reviewVote.setKhachHang(khachHang);
            reviewVote.setVoted(true);
            reviewVoteRepository.save(reviewVote) ;
        } else {
            ReviewVote reviewVote = vote.get() ;
            boolean undo = !reviewVote.isVoted() ;
            reviewVoteRepository.updateVote(reviewVote.getId(), undo);
        }
    }

    public void updateVoteForUser(String email, List<Review> reviews ) {
        for (Review review : reviews) {
            Optional<ReviewVote> vote  = reviewVoteRepository.findByCustomerAndReview(email, review.getId());
            if(vote.isPresent()) {
                ReviewVote oldVote = vote.get() ;
                if (oldVote.isVoted()) {
                    review.setVoted(true);
                } else {
                    review.setVoted(false);
                }
            }
        }
    }
    public List<Review> findByCustomerProduct(String email, Integer productId) {
        return reviewRepository.findByCustomerProduct(email, productId) ;
    }
    public List<Review> findByProductAndRating(Integer sanPhamId, int rating) {
        return reviewRepository.findByProductAndRating(sanPhamId, rating);
    }

    public List<Review> findByProduct(Integer productId) {
        return reviewRepository.findByProduct(productId) ;
    }

    public boolean didCustomerReviewProduct(String email, Integer productId) {
        return reviewRepository.countByCustomerProduct(email, productId) > 0 ;
    }
    public boolean canCustomerReviewProduct(String email, Integer productId) {
        return cthdRepository.countByCustomerProductStatus(email, productId, OrderStatus.DELIVERED) > 0 ;
    }
    public Review saveReview(Review review , NhanVien khachHang , SanPham sanPham) {
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        review.setReviewTime(LocalDateTime.now());
        review.setKhachHang(customer);
        review.setSanPham(sanPham);
        return reviewRepository.save(review) ;
    }
    public Page<Review> listByPage(int pageNum , String sortField , String sortDir , String keyword , String email){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, reviewPerPage , sort);
        NhanVien user = nhanVienService.findByEmail(email) ;
        if(user.hasRole("Customer")) {
            if(keyword != null) {
                return reviewRepository.findAllCustomer(keyword, email,pageable) ;
            } else  {
                return reviewRepository.findAllCustomer(email,pageable) ;
            }
        } else {
            if(keyword != null) {
                return reviewRepository.findAll(keyword, pageable);
            } else  {
                return reviewRepository.findAll(pageable);
            }
        }
    }

    public Review get(Long reviewId) throws ReviewNotFoundException {
        Review reviewDb = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Khong tim thay bai danh gia cua ban"));
        return reviewDb ;
    }


}
