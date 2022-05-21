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
			.mvcMatcher("/service-c/**")
			.authorizeRequests(authorizeRequests -> 
					authorizeRequests.mvcMatchers("/service-c/**")
									 .access("hasAuthority('SCOPE_authority-c')")
									 .anyRequest()
									 .authenticated())
			.oauth2ResourceServer(oauth2Server -> oauth2Server.jwt());
			
		return http.build();
	}
}
