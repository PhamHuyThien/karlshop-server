package com.thiendz.j6.dto;


import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;
import com.thiendz.j6.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {	
	private Integer id;
	@NotBlank(message = "Tên danh mục không được để trống!")
	private String name;
	@NotNull
	private Boolean status;
	
	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.status = category.getStatus();
	}
}
