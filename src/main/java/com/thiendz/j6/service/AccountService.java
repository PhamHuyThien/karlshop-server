package com.thiendz.j6.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Account;

@Service
public interface AccountService {
	Account findByUsername(String username);
	Account save(Account account);
	Account login(String username, String password);
	List<Account> findAll(Pageable pageable);
	Account findById(int id);
}
