package com.hr.oauth.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception
	{
		http
			.mvcMatcher("/serv-a/**")
			.authorizeRequests(authorizeRequests -> 
					authorizeRequests.mvcMatchers("/serv-a/**")
									 .access("hasAuthority('SCOPE_authority-a')")
									 .anyRequest()
									 .authenticated())
			.oauth2ResourceServer()
			.jwt();
			
		return http.build();
	}
}
