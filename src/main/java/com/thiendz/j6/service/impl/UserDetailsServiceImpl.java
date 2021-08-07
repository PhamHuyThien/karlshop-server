package com.thiendz.j6.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Account;
import com.thiendz.j6.service.AccountService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountService.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetailsImpl userDetailsDTO = new UserDetailsImpl(account);
		return (UserDetails) userDetailsDTO;
	}

	public UserDetails loadUserById(Integer userId) {
		Account account = accountService.findById(userId);
		if (account == null) {
			throw new UsernameNotFoundException(userId + "");
		}
		UserDetailsImpl userDetailsDTO = new UserDetailsImpl(account);
		return (UserDetails) userDetailsDTO;
	}

}
