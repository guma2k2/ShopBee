package com.web.project.service;

import com.web.project.entity.OrderTrack;
import com.web.project.repository.OrderTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderTrackService {

    @Autowired
    private OrderTrackRepository orderTrackRepository ;

    public OrderTrack saveOrderTrack(OrderTrack orderTrack) {
        return orderTrackRepository.save(orderTrack);
    }
}
