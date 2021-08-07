package com.thiendz.j6.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thiendz.j6.service.AuthenticateService;

@Controller
@RequestMapping("/logout")
public class LogoutController {
	@Autowired
	HttpServletRequest rq;
	@Autowired
	AuthenticateService authenticateService;
	
//	@GetMapping
	public String logout() {
		rq.getSession().removeAttribute("account");
		authenticateService.clearAuth();
		return "redirect:/";
	}
}
