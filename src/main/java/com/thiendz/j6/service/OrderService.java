package com.thiendz.j6.service;

import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Order;

@Service
public interface OrderService {
	Order save(Order order);
}
