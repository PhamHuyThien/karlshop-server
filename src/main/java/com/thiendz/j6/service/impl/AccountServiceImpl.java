package com.thiendz.j6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiendz.j6.dao.AccountDAO;
import com.thiendz.j6.entity.Account;
import com.thiendz.j6.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDAO accountDAO;

	@Override
	public Account findByUsername(String username) {
		return accountDAO.findByUsername(username);
	}

	@Override
	public Account save(Account account) {
		return accountDAO.save(account);
	}

	@Override
	public Account login(String username, String password) {
		return accountDAO.login(username, password);
	}

	@Override
	public List<Account> findAll(Pageable pageable) {
		Page<Account> pageAccount = accountDAO.findAll(pageable);
		return pageAccount.toList();
	}

	@Override
	public Account findById(int id) {
		Optional<Account> option = accountDAO.findById(id);
		return option.isPresent() ? option.get() : null;
	}

}
