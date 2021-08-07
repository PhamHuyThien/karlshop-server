package com.thiendz.j6.controller.rest.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiendz.j6.dto.ProfileDTO;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.entity.Account;
import com.thiendz.j6.jwt.JwtTokenProvider;
import com.thiendz.j6.service.AccountService;
import com.thiendz.j6.utils.FormUtils;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileRestController {
	private static final int PASSWORD_LENGTH_MIN = 6;
	private static final int PASSWORD_LENGTH_MAX = 30;
	@Autowired
	AccountService accountService;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping
	public ResponseDTO<Account> getProfile(@RequestHeader("authorization") String jwtToken) {
		ResponseDTO<Account> responseDTO = new ResponseDTO<>();
		int id = jwtTokenProvider.getUserIdFromJWT(jwtToken.substring(7));
		Account account = accountService.findById(id);
		account.setPassword(null);
		//
		responseDTO.setStatus(1);
		responseDTO.setMessage("Lấy thông tin tài khoản thành công!");
		responseDTO.setData(account);
		responseDTO.setToken(jwtToken.substring(7));
		//
		return responseDTO;
	}

	@PutMapping
	public ResponseDTO<Account> updateProfile(@Valid @RequestBody ProfileDTO profileDTO, BindingResult bindingResult,
			@RequestHeader("authorization") String jwtToken) {
		ResponseDTO<Account> responseDTO = new ResponseDTO<>();
		//
		if (bindingResult.hasErrors()) {
			responseDTO.setMessage(FormUtils.toStringBindResultValid(bindingResult));
			return responseDTO;
		}
		//
		int id = jwtTokenProvider.getUserIdFromJWT(jwtToken.substring(7));
		Account accountMain = accountService.findById(id);
		if (!profileDTO.getPasswordNew().equals("") || !profileDTO.getPasswordOld().equals("")) {			
			if (!passwordEncoder.matches(profileDTO.getPasswordOld(), accountMain.getPassword())) {
				responseDTO.setMessage("Mật khẩu cũ không chính xác!");
				return responseDTO;
			}
			//
			final String passwordNew = profileDTO.getPasswordNew();
			if (passwordNew.trim().equals("") || passwordNew.length() < PASSWORD_LENGTH_MIN
					|| passwordNew.length() > PASSWORD_LENGTH_MAX) {
				final String message = "Mật khẩu phải lớn hơn %d kí tự và nhỏ hơn %d kí tự!";
				responseDTO.setMessage(String.format(message, PASSWORD_LENGTH_MIN - 1, PASSWORD_LENGTH_MAX - 1));
				return responseDTO;
			}
		}
		//
		Account accountChange = new Account(profileDTO);
		accountChange.setUsername(accountMain.getUsername());
		if (accountChange.getPassword().trim().equals("")) {
			accountChange.setPassword(accountMain.getPassword());
		} else {
			accountChange.setPassword(passwordEncoder.encode(accountChange.getPassword()));
		}
		accountChange.setRole(accountMain.getRole());
		accountChange.setStatus(accountMain.getStatus());
		accountChange.setTime(accountMain.getTime());
		accountChange.setId(accountMain.getId());
		//
		accountMain = accountService.save(accountChange);
		//
		responseDTO.setStatus(accountMain == null ? 0 : 1);
		responseDTO.setMessage(accountMain == null ? "Cập nhật thất bại, hãy thử lại!" : "Cập nhật thành công!");
		if (accountMain != null) {
			accountMain.setPassword(null);
		}
		responseDTO.setData(accountMain);
		return responseDTO;
	}
}
