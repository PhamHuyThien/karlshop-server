package com.thiendz.j6.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Discount;

@Service
public interface DiscountDAO extends JpaRepository<Discount, Integer>{
	@Query("SELECT d FROM Discount d WHERE d.code = ?1")
	Discount findByDiscount(String discount);
	
	@Query("SELECT d FROM Discount d WHERE d.code = ?1 AND d.status = true")
	Discount findByDiscountActive(String discount);
}
