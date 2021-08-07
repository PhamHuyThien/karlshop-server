package com.thiendz.j6.dto;


import java.util.List;

import com.thiendz.j6.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRespDTO {
	private List<Product> listProductApplyDiscount;
	private int intoMoney;
}
