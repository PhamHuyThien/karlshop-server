package com.thiendz.j6.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thiendz.j6.dto.CategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Boolean status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	//
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	List<Product> listProducts;
	
	public Category(CategoryDTO categoryDTO) {
		this.id = categoryDTO.getId();
		this.name = categoryDTO.getName();
		this.status = categoryDTO.getStatus();
	}
}
