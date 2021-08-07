package com.thiendz.j6.controller.rest.v1;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiendz.j6.dto.RegisterDTO;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.entity.Account;
import com.thiendz.j6.service.AccountService;
import com.thiendz.j6.utils.FormUtils;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/register")
public class RegisterRestController {
	@Autowired
	AccountService accountService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping
	public ResponseDTO<Account> login(@Valid @RequestBody RegisterDTO registerDTO, BindingResult bind) {
		ResponseDTO<Account> responseDTO = new ResponseDTO<>();
		if (bind.hasErrors()) {
			responseDTO.setMessage(FormUtils.toStringBindResultValid(bind));
			return responseDTO;
		}
		if(!FormUtils.comparePwd(registerDTO.getPassword(), registerDTO.getConfirmPassword())) {
			responseDTO.setMessage("Mật khẩu và Nhập lại mật khẩu không khớp!");
			return responseDTO;
		}
		if(accountService.findByUsername(registerDTO.getUsername())!=null) {
			responseDTO.setMessage("Tài khoản này đã tồn tại, thử 1 tài khoản khác!");
			return responseDTO;
		}
		Account account = new Account(registerDTO);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setRole(0);
		account.setTime(new Date());
		account.setStatus(true);
		account = accountService.save(account);
		responseDTO.setStatus(account == null ? 0 : 1);
		responseDTO.setMessage(account == null ? "Tạo tài khoản thất bại, hãy thử lại!" : "Tạo tài khoản thành công!");
		if (account != null) {
			account.setPassword(null);
		}
		responseDTO.setData(account);
		return responseDTO;
	}

}
