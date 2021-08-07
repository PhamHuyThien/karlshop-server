package com.thiendz.j6.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thiendz.j6.dto.AccountDTO;
import com.thiendz.j6.dto.LoginDTO;
import com.thiendz.j6.dto.ProfileDTO;
import com.thiendz.j6.dto.RegisterDTO;

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
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private Integer role;
	private Boolean status;
	@Temporal(TemporalType.TIMESTAMP)
	Date time;

	public Account(RegisterDTO registerDTO) {
		this.username = registerDTO.getUsername();
		this.password = registerDTO.getPassword();
		this.firstname = registerDTO.getFirstname();
		this.lastname = registerDTO.getLastname();
		this.email = registerDTO.getEmail();
	}

	public Account(LoginDTO loginDTO) {
		this.username = loginDTO.getUsername();
		this.password = loginDTO.getPassword();
	}
	
	public Account(AccountDTO accountDTO) {
		this.id = accountDTO.getId();
		this.username = accountDTO.getUsername();
		this.password = accountDTO.getPassword();
		this.firstname = accountDTO.getFirstname();
		this.lastname = accountDTO.getLastname();
		this.email = accountDTO.getEmail();
		this.role = accountDTO.getRole();
		this.status = accountDTO.getStatus();
	}
	
	public Account(ProfileDTO profileDTO) {
		this.password = profileDTO.getPasswordNew();
		this.firstname = profileDTO.getFirstname();
		this.lastname = profileDTO.getLastname();
		this.email = profileDTO.getEmail();
	}

}
