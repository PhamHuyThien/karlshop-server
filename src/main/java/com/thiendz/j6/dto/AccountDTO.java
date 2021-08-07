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
public class AccountDTO {
	private Integer id;
	@NotBlank(message = "Tài khoản không được để trống!")
	private String username;
	@NotBlank(message = "Mật khẩu không được để trống!")
	private String password;
	@NotBlank(message = "Họ không được để trống!")
	private String firstname;
	@NotBlank(message = "Tên không được để trống!")
	private String lastname;
	@Email(message = "Email không đúng định dạng!")
	@NotBlank(message = "Email không được để trống!")
	private String email;
	@NotNull(message = "Quyền không được để trống!")
	private Integer role;
	@NotNull(message = "Tình trạng không được để trống!")
	private Boolean status;
	
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.username = account.getUsername();
		this.password = account.getPassword();
		this.firstname = account.getFirstname();
		this.lastname = account.getLastname();
		this.email = account.getEmail();
		this.role = account.getRole();
		this.status = account.getStatus();
	}
}
