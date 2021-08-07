package com.thiendz.j6.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Account;
import com.thiendz.j6.service.impl.UserDetailsImpl;

@Service
public class AuthenticateService {

	@Autowired
	AuthenticationManager authenticationManager;

	public Authentication auth(String username, String password) throws AuthenticationException{
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authToken);
	}

	public void setAuth(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public void clearAuth() {
		SecurityContextHolder.clearContext();		
	}
	
	public Account getAccount(Authentication authentication) {
		return getUserDetailsImpl(authentication).getAccount();
	}

	public UserDetailsImpl getUserDetailsImpl(Authentication authentication) {
		return (UserDetailsImpl) authentication.getPrincipal();
	}
}
