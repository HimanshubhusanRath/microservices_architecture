package com.hr.springboot.oauth2.authserver.config;

import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;

import com.hr.springboot.oauth2.authserver.jose.JWKs;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration(proxyBeanMethods = false)
// DON'T USE THIS IMPORT ANNOTATION. IT CAUSES 401 WHILE ACCESSING LOGIN PAGE IN AUTH SERVER
//@Import(OAuth2AuthorizationServerConfiguration.class)
public class AuthorizationServerConfig {

	@Value("${server.port}")
	private String serverPort;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}
	
	/**
	 * Defines the filter chain for the authorization server
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Order(1)
	public SecurityFilterChain authServerSecurityFilterChain(final HttpSecurity http) throws Exception
	{
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http
				.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
				.oidc(Customizer.withDefaults()); // Enable OpenID Connect 1.0

		http
				// Redirect to the login page when not authenticated from the authorization endpoint
				.exceptionHandling((exceptions) -> exceptions
						.authenticationEntryPoint(
								new LoginUrlAuthenticationEntryPoint("/login"))
				)
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		return http.build();
	}
	
	@Bean
	public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder)
	{
		final RegisteredClient loginClient2 = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("loginclient-2")
				.clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // For PKCE, this should be none
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("http://client-b:8082/login/oauth2/code/login-client-2")
				.redirectUri("http://client-b:8082/authorized")
				.scope(OidcScopes.OPENID)
				.clientSettings(clientSettings())
				.build();


		return new InMemoryRegisteredClientRepository(loginClient2);
	}

	@Bean
	ClientSettings clientSettings()
	{
		return ClientSettings
				.builder()
				.requireAuthorizationConsent(true) // To ask the user for consent
				.requireProofKey(true) // To enable PKCE
				.build();
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	/*
	 * STANDARD CONFIGURATIONS FOR RSA-KEY
	 * 
	 */
	@Bean
	public Supplier<JWKSet> jwkSetProvider()
	{
		RSAKey rsaKey = JWKs.generateRSAKey();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return () -> jwkSet;
	}
	
	@Bean
    public JWKSource<SecurityContext> jwkSource(Supplier<JWKSet> jwkSetProvider) {
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSetProvider.get());
    }

    /**
     * Sets the authorization provider (auth server)
     * 
     * @return
     */
    @Bean
    public AuthorizationServerSettings providerSettings() {
    	System.out.println("AUTH SERVER PORT : "+serverPort);
        return AuthorizationServerSettings.builder().issuer("http://auth-server:"+serverPort).build();
    }
	
	
}
