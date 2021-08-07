package com.thiendz.j6.entity;

import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thiendz.j6.dto.DiscountDTO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "discounts")
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String code;
	private Integer usages;
	private String pattern;
	private String cond;
	private String discount;
	@Column(name = "time_expired")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeExpired;
	private Boolean status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	public Discount(int id) {
		this.id = id;
	}
	
	public Discount(DiscountDTO discountDTO) {
		this.id = discountDTO.getId();
		this.code = discountDTO.getCode();
		this.usages = discountDTO.getUsages();
		this.pattern = discountDTO.getPattern();
		this.cond = discountDTO.getCond();
		this.discount = discountDTO.getDiscount();
		this.timeExpired = discountDTO.getTimeExpired();
		this.status = discountDTO.getStatus();
	}
}
