package com.thiendz.j6.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.thiendz.j6.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileDTO {
	private String passwordOld;
	private String passwordNew;
	@NotNull(message = "Tên phải có!")
	@NotBlank(message = "Tên không được để trống!")
	private String firstname;
	@NotNull(message = "Họ phải có!")
	@NotBlank(message = "Họ không được để trống!")
	private String lastname;
	@NotNull(message = "Email phải có!")
	@NotBlank(message = "Email không được để trống!")
	@Email(message = "Email không đúng định dạng!")
	private String email;
	
	public ProfileDTO(Account account) {
		this.passwordNew = account.getPassword();
		this.firstname = account.getFirstname();
		this.lastname = account.getLastname();
		this.email = account.getEmail();
	}

}
