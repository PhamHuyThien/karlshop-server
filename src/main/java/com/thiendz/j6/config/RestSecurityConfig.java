package com.thiendz.j6.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiendz.j6.dto.ResponseDTO;
import com.thiendz.j6.filter.JwtAuthenticationFilter;
import com.thiendz.j6.service.impl.UserDetailsImpl;
import com.thiendz.j6.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// Get AuthenticationManager bean
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		final String[] NOT_AUTHENTICATE_API = new String[] { "/api/v1/login", "/api/v1/register",
				"/api/v1/category/**" };
//		final String[] API_ADMIN_ACCESS = new String[] { "", "", };
		httpSecurity.cors().and().csrf().disable();
//		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.authorizeRequests().antMatchers(NOT_AUTHENTICATE_API).permitAll();
		httpSecurity.authorizeRequests().antMatchers("/api/v1/**").authenticated();
//		httpSecurity.authorizeRequests().antMatchers(API_ADMIN_ACCESS).hasRole("ROLE_ADMIN");
		//
		httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		//
		httpSecurity.exceptionHandling().authenticationEntryPoint((rq, rp, e) -> {
			ResponseDTO<String> responseDTO = new ResponseDTO<>();
			responseDTO.setStatus(-403);
			responseDTO.setMessage("Bạn chưa đăng nhập!");
			rp.setHeader("content-type", "application/json;charset=UTF-8");
			rp.getWriter().write(new ObjectMapper().writeValueAsString(responseDTO));
		});
		httpSecurity.exceptionHandling().accessDeniedHandler((rq, rp, e) -> {
			ResponseDTO<String> responseDTO = new ResponseDTO<>();
			responseDTO.setStatus(-401);
			responseDTO.setMessage("Bạn không có quyền làm điều này!");
			rp.setHeader("content-type", "application/json;charset=UTF-8");
			rp.getWriter().write(new ObjectMapper().writeValueAsString(responseDTO));
		});

		//
		httpSecurity.httpBasic();
		httpSecurity.authorizeRequests().antMatchers("/", "/login**", "/register**", "/css/**", "/js/**", "/img/**", "/fonts/**")
				.permitAll();
		httpSecurity.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
		httpSecurity.authorizeRequests().anyRequest().authenticated();
		//
		httpSecurity.formLogin().loginPage("/login**").defaultSuccessUrl("/").failureUrl("/login?error").permitAll();
		//
		httpSecurity.logout().logoutUrl("/logout").logoutSuccessUrl("/").clearAuthentication(true);
		//
	}
}
