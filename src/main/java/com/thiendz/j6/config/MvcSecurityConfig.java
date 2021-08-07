package com.thiendz.j6.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(2)
public class MvcSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		final String[] ADMIN_ROLE_PATH = new String[] {
//			"/admin/**"	
//		};
//		httpSecurity.httpBasic();
//		httpSecurity.authorizeRequests().antMatchers(ADMIN_ROLE_PATH).access("hasRole('ROLE_ADMIN')");
//		httpSecurity.httpBasic().and().formLogin()
//	      .loginPage("/login")
//	      .loginProcessingUrl("/login")
//	      .defaultSuccessUrl("/",true)
//	      .failureUrl("/login");
	}
}
