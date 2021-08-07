package com.thiendz.j6.dto;

import lombok.NoArgsConstructor;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCheckDTO {
	@NotBlank(message = "Mã giảm giá không được để trống!")
	@NotNull
	private String code;
	@NotEmpty(message = "Phải có ít nhất 1 sản phẩm cần áp dụng mã giảm giá!")
	private List<Integer> listIdProductBuy;
}
