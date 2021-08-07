package com.thiendz.j6.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiendz.j6.dao.OrderDAO;
import com.thiendz.j6.entity.Order;
import com.thiendz.j6.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderDAO orderDAO;
	
	@Override
	public Order save(Order order) {
		return orderDAO.save(order);
	}

}
