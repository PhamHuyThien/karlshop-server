package com.thiendz.j6.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.thiendz.j6.entity.Account;

@Service
public interface AccountDAO extends JpaRepository<Account, Integer>{
	@Query("SELECT a FROM Account a WHERE a.username=?1")
	Account findByUsername(String username);
	
	@Query("SELECT a FROM Account a WHERE a.username=?1 AND a.password=?2")
	Account login(String username, String password);
}
