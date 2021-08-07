package com.thiendz.j6.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiendz.j6.dto.DiscountCheckDTO;
import com.thiendz.j6.dto.DiscountRespDTO;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.entity.Discount;

@Service
public interface DiscountService {

	Discount save(Discount discount);

	Discount findById(int id);
	Discount findByDiscount(String discount);
	Discount findByDiscountActive(String discount);
	List<Discount> findAll(Pageable pageable);
	
	//
	ResponseDTO<DiscountRespDTO> check(DiscountCheckDTO discountCheckDTO);
}
