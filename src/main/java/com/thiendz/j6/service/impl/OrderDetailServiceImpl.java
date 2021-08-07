package com.thiendz.j6.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiendz.j6.dao.OrderDetailDAO;
import com.thiendz.j6.entity.OrderDetail;
import com.thiendz.j6.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	@Autowired
	OrderDetailDAO orderDetailDAO;
	
	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		return orderDetailDAO.save(orderDetail);
	}

}
