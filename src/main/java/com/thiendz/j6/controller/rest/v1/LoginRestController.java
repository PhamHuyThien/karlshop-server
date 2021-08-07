package com.thiendz.j6.controller.rest.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiendz.j6.dto.LoginDTO;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.entity.Account;
import com.thiendz.j6.jwt.JwtTokenProvider;
import com.thiendz.j6.service.AuthenticateService;
import com.thiendz.j6.service.impl.UserDetailsImpl;
import com.thiendz.j6.utils.FormUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/login")
public class LoginRestController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	AuthenticateService authenticateeService;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//======================================================================
	@PostMapping
	public ResponseDTO<Account> login(@Valid @RequestBody LoginDTO login, BindingResult bind) {
		ResponseDTO<Account> responseDTO = new ResponseDTO<>();
		if (bind.hasErrors()) {
			responseDTO.setMessage(FormUtils.toStringBindResultValid(bind));
			return responseDTO;
		}
		try {
			//
			Authentication authentication = authenticateeService.auth(login.getUsername(), login.getPassword());
			authenticateeService.setAuth(authentication);
			UserDetailsImpl userDetailsImpl = authenticateeService.getUserDetailsImpl(authentication);
			String jwtToken = jwtTokenProvider.generateToken(userDetailsImpl);
			//
			Account account = userDetailsImpl.getAccount();
			account.setPassword(null);
			//
			responseDTO.setStatus(1);
			responseDTO.setMessage("Đăng nhập thành công!");
			responseDTO.setToken(jwtToken);
			responseDTO.setData(account);
		} catch (Exception e) {
			responseDTO.setMessage("Tài khoản hoặc mật khẩu không chính xác!");		
		}
		return responseDTO;
	}
	//======================================================================
}
