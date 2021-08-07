package com.thiendz.j6.controller.admin;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thiendz.j6.dto.AccountDTO;
import com.thiendz.j6.entity.Account;
import com.thiendz.j6.service.AccountService;

@Controller
@RequestMapping("/admin/manager-account")
public class ManagerAccount {
	@Autowired
	AccountService accountService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping
	public void index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "col", defaultValue = "id") String col,
			@RequestParam(name = "type", defaultValue = "DESC") String type,
			@RequestParam(name = "edit", defaultValue = "-1") int edit) {
		//
		AccountDTO accountDTO = new AccountDTO();
		if (edit != -1) {
			Account account = accountService.findById(edit);
			if (account != null) {
				accountDTO = new AccountDTO(account);
			}
		}
		model.addAttribute(accountDTO);
		//
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(type), col);
		model.addAttribute("listAccounts", accountService.findAll(pageable));
		//
		model.addAttribute("edit", edit);
		model.addAttribute("type", type.equals("DESC") ? "ASC" : "DESC");
		model.addAttribute("col", col);
		model.addAttribute("size", size);
		model.addAttribute("page", page);
	}

	@PostMapping(params = "add")
	public void add(Model model, @Valid @ModelAttribute("accountDTO") AccountDTO accountDTO,
			BindingResult bindingResult, @RequestParam(name = "page", defaultValue = "0") int page) {
		model.addAttribute("page", page);
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Bạn phải nhập đày đủ thông tin trước!");
			return;
		}
		String type = "Thêm mới";
		if (accountDTO.getId() != null) {
			type = "Chỉnh sửa";
		}
		Account account = new Account(accountDTO);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setTime(new Date());
		boolean error = accountService.save(account) == null;
		type = type + " " + (error ? "thất bại!" : "thành công!");
		model.addAttribute(error ? "error" : "success", type);
	}
}
