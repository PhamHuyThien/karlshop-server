package com.thiendz.j6.service;

import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.OrderDetail;

@Service
public interface OrderDetailService {
	OrderDetail save(OrderDetail orderDetail);
}
