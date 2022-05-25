package com.hr.oauth.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception
	{
		http
			.mvcMatcher("/serv-b/**")
			.authorizeRequests(authorizeRequests -> 
					authorizeRequests.mvcMatchers("/serv-b/**")
									 .access("hasAuthority('SCOPE_authority-b')")
									 .anyRequest()
									 .authenticated())
			.oauth2ResourceServer(oauth2Server -> oauth2Server.jwt());
			
		return http.build();
	}
}
