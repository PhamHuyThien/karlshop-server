package com.thiendz.j6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDiscountDTO {
	private int id;
	private int money;
	private int amount;
	private int totalMoney;
}
