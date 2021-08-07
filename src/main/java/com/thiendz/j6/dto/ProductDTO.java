package com.thiendz.j6.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.thiendz.j6.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private Integer id;
	@NotNull(message = "Danh mục không được để trống!")
	private Integer idCategory;
	@NotBlank(message = "Tên không được để trống!")
	private String name;
	@NotBlank(message = "Giới tính không được để trống!")
	private String sex;
	@NotNull(message = "Giá tiền không được để trống!")
	@Digits(message = "Giá tiền phải là số!", fraction = 0, integer = 10)
	@Min(value = 0, message = "Giá tiền phải lớn hơn 0!")
	private Integer price;
	private String img;
	@NotBlank(message = "Tiêu đề không được để trống!")
	private String title;
	@NotBlank(message = "Nội dung không được để trống!")
	private String des;
	@NotNull(message = "Tình trạng phải có!")
	private Boolean status;

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.idCategory = product.getCategory().getId();
		this.name = product.getName();
		this.sex = product.getSex();
		this.price = product.getPrice();
		this.img = product.getImg();
		this.title = product.getTitle();
		this.des = product.getDes();
		this.status = product.getStatus();
	}
}
