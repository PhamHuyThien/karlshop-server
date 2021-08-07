package com.thiendz.j6.dto;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.thiendz.j6.entity.Discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
	private Integer id;
//	@NotBlank(message = "Mã giảm giá không được để trống!")
	private String code;
	@Digits(message = "Lượt sử dụng phải là số dưới 9 kí tự!", fraction = 0, integer = 9)
	@Min(value = 1, message = "Lượt sử dụng phải lớn hơn 0!")
	private Integer usages;	
	@Pattern(regexp = "^((\\*)|(((@|#)[0-9A-Za-z]+\\|?)+))$", message = "Patern sản phảm áp dụng không hợp lệ!")
	private String pattern;
	@Pattern(regexp = "^(\\*(((>|<)[0-9]+)|((=|(<>))'[a-z]+'))?)$", message = "Pattern điều kiện áp dụng không hợp lệ!")
	private String cond;
	@Pattern(regexp = "^(\\d+|(\\d{1,2}\\%))$", message = "Pattern giảm giá không hợp lệ!")
	private String discount;
//	@NotNull(message = "thời gian kết thúc không đc để trống!")
	private Date timeExpired;
	@NotNull(message = "Tình trạng không được để trống!")
	private Boolean status;
	
	public DiscountDTO(Discount discount) {
		this.id = discount.getId();
		this.code = discount.getCode();
		this.usages = discount.getUsages();
		this.pattern = discount.getPattern();
		this.cond = discount.getCond();
		this.discount = discount.getDiscount();
		this.timeExpired = discount.getTimeExpired();
		this.status = discount.getStatus();
	}
}
