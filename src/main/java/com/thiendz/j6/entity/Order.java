package com.thiendz.j6.entity;

import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name ="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@JoinColumn(name="id_account")
	@ManyToOne
	@JsonIgnore
	private Account account;
	@JoinColumn(name="id_discount")
	@ManyToOne
	@JsonIgnore
	private Discount discount;
	private String des;
	private Boolean status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	//
	@OneToMany(mappedBy = "order")
	List<OrderDetail> listOrderDetails;
}
