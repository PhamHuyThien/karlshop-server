package com.thiendz.j6.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thiendz.j6.dto.LoginDTO;
import com.thiendz.j6.entity.Account;
import com.thiendz.j6.jwt.JwtTokenProvider;
import com.thiendz.j6.service.AccountService;
import com.thiendz.j6.service.AuthenticateService;


@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	AccountService accountService;
	@Autowired
	HttpServletRequest rq;
	//
	@Autowired
	AuthenticateService authenticateeService;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@GetMapping
	public void index(Model model) {
		LoginDTO loginDTO = new LoginDTO();
		model.addAttribute("loginDTO", loginDTO);
	}

//	@PostMapping
	public void login(Model model, @Valid @ModelAttribute("loginDTO") LoginDTO loginDTO, BindingResult bindingResult) {
		//
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Bạn phải nhập đầy đủ thông tin trước!");
			return;
		}
		//
		Account account = null;
		try {
			Authentication auth = authenticateeService.auth(loginDTO.getUsername(), loginDTO.getPassword());
			authenticateeService.setAuth(auth);
			account = authenticateeService.getAccount(auth);
		} catch (Exception e) {
			model.addAttribute("error", "Tài khoản mật khẩu sai!");
			return;
		}
		//
		if(account.getRole()==0) {
			model.addAttribute("error", "Chỉ có quản trị viên mới có thể truy cập!");
			return;
		}
		//
		rq.getSession().setAttribute("account", account);
		model.addAttribute("success", "Đăng nhập thành công!");
	}
}
