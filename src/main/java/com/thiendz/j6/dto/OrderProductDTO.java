package com.thiendz.j6.dto;


import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderProductDTO {
	@NotNull
	private Integer id;
	@NotBlank
	@NotNull
	private String title;
	@NotNull
	private Integer amount;
}
