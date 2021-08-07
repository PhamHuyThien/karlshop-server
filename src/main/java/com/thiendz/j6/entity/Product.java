package com.thiendz.j6.entity;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thiendz.j6.dto.ProductDTO;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@JoinColumn(name = "id_category")
	@ManyToOne
	@JsonIgnore
	private Category category;
	private String name;
	private String sex;
	private Integer price;
	private String img;
	private String title;
	private String des;
	private Boolean status;
	@Temporal(TemporalType.TIMESTAMP)
	Date time;
	
	public Product(ProductDTO productDTO) {
		this.id = productDTO.getId();
		this.name = productDTO.getName();
		this.sex = productDTO.getSex();
		this.price = productDTO.getPrice();
		this.img = productDTO.getImg();
		this.title = productDTO.getTitle();
		this.des = productDTO.getDes();
		this.status = productDTO.getStatus();
	}
}
