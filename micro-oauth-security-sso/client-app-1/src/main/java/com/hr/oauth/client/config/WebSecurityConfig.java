package com.hr.oauth.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/assets/**", "/webjars/**");
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception
	{
		http
			.cors()
			.and()
			.csrf()
			.disable()
			//.anonymous().disable()
			.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
			.oauth2Login(oauth2login -> oauth2login
											.loginPage("/oauth2/authorization/login-client-1")
											//.failureUrl("/login/error")
											.permitAll()
						)
			.oauth2Client(Customizer.withDefaults());
		
		return http.build();
	}
}
