package com.thiendz.j6.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.thiendz.j6.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
	@NotBlank(message = "Tài khoản không được để trống!")
	@Length(min = 3, max = 30, message = "Tài khoản phải có độ dài từ 3 và không quá 30 kí tự!")
	private String username;
	@NotBlank(message = "Mật khẩu không được để trống!")
	@Length(min = 6, max = 30, message = "Mật khẩu phải có độ dài từ 6 và không quá 30 kí tự!")
	private String password;
	private Boolean rememberMe;
	
	public LoginDTO(Account account) {
		this.username = account.getUsername();
		this.password = account.getPassword();
	}
}
