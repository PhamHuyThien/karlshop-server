package com.thiendz.j6.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.thiendz.j6.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDTO {
	@NotBlank(message = "Tài khoản không được để trống!")
	@Length(min = 3, max = 30, message = "Tài khoản phải có độ dài từ 3 và không quá 30 kí tự!")
	private String username;
	@NotBlank(message = "Mật khẩu không được để trống!")
	@Length(min = 6, max = 30, message = "Mật khẩu phải có độ dài từ 6 và không quá 30 kí tự!")
	private String password;
	@Length(min = 6, max = 30, message = "Nhập lại mật khẩu phải có độ dài từ 6 và không quá 30 kí tự!")
	private String confirmPassword;
	@NotBlank(message = "Tên không được để trống!")
	private String firstname;
	@NotBlank(message = "Họ không được để trống!")
	private String lastname;
	@NotBlank(message = "Email không được để trống!")
	@Email(message = "Email không đúng định dạng!")
	private String email;
	
	public RegisterDTO(Account account) {
		this.username = account.getUsername();
		this.password = account.getPassword();
		this.firstname = account.getFirstname();
		this.lastname = account.getLastname();
		this.email = account.getEmail();
	}

}
