package com.hr.oauth.client2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
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
		return (web) -> web.ignoring().requestMatchers("/assets/**", "/webjars/**");
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http, final ClientRegistrationRepository clientRegistrationRepository) throws Exception
	{
		final String base_uri = OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
		final DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, base_uri);
		// Enable authorization code with PKCE
		resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());

		http
			.cors()
			.and()
			.csrf()
			.disable()
			.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
			.oauth2Login(oauth2login ->
					{
						oauth2login.loginPage("/oauth2/authorization/login-client-2").permitAll();
						oauth2login.authorizationEndpoint().authorizationRequestResolver(resolver);
					})
			.oauth2Client(Customizer.withDefaults());
		
		return http.build();
	}
}
