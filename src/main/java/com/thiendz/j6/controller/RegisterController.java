package com.thiendz.j6.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thiendz.j6.dto.RegisterDTO;
import com.thiendz.j6.entity.Account;
import com.thiendz.j6.service.AccountService;
import com.thiendz.j6.utils.FormUtils;

@Controller
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	HttpServletRequest rq;
	@Autowired
	AccountService accountService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping
	public void index(Model model) {
		RegisterDTO registerDTO = new RegisterDTO();
		model.addAttribute("registerDTO", registerDTO);
	}

	@PostMapping
	public void register(Model model, @Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("error", "Bạn phải nhập đầy đủ thông tin!");
			return;
		}
		if(!FormUtils.comparePwd(registerDTO.getPassword(), registerDTO.getConfirmPassword())) {
			model.addAttribute("error", "Mật khẩu và Xác nhận mật khẩu không khớp!");
			return;
		}
		Account account = new Account(registerDTO);
		if(accountService.findByUsername(account.getUsername())!=null) {
			model.addAttribute("error", "Tài khoản đã tồn tại!");
			return;
		}
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setRole(0);
		account.setStatus(true);
		account.setTime(new Date());
		accountService.save(account);
		model.addAttribute("success", "Đăng kí thành công!");
		model.addAttribute("registerDTO", registerDTO);
	}
}
