package com.thiendz.j6.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.OrderDetail;

@Service
public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer>{

}
