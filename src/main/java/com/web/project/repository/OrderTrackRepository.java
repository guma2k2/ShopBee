package com.web.project.repository;

import com.web.project.entity.OrderTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTrackRepository  extends JpaRepository<OrderTrack , Long> {


}
