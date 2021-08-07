package com.thiendz.j6.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Order;

@Service
public interface OrderDAO extends JpaRepository<Order, Integer>{

}
