package com.hr.springboot.oauth2.authserver.config;

import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

import com.hr.springboot.oauth2.authserver.jose.JWKs;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

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
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authServerSecurityFilterChain(final HttpSecurity http) throws Exception
	{
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		// If there is any specific configuration for Oauth2, we can implement that here but currently we are using the default configurations
		// for Oauth2
		return http.formLogin(Customizer.withDefaults()).build();
	}
	
	@Bean
	public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder)
	{
		final RegisteredClient loginClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("loginclient")
				.clientSecret(passwordEncoder.encode("secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/login-client")
				.redirectUri("http://127.0.0.1:8080/authorized")
				.scope(OidcScopes.OPENID)
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()) // To show consent screen to the user
				.build();

		// Configure both AUTHORIZATION_CODE as well as REFRESH_TOKEN so that the tokens would be refreshed automatically by the Client App's WebClient.
		final RegisteredClient clientA = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("client-a")
				.clientSecret(passwordEncoder.encode("secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("http://127.0.0.1:8080/path-a")
				.scope("authority-a")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()) // To show consent screen to the user
				.build();
		
		// Configure both AUTHORIZATION_CODE as well as REFRESH_TOKEN so that the tokens would be refreshed automatically by the Client App's WebClient.
		final RegisteredClient clientAB = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("client-ab")
				.clientSecret(passwordEncoder.encode("secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("http://127.0.0.1:8080/path-ab")
				.scope("authority-a")
				.scope("authority-b")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()) // To show consent screen to the user
				.build();

		// Configure both AUTHORIZATION_CODE as well as REFRESH_TOKEN so that the tokens would be refreshed automatically by the Client App's WebClient.
		final RegisteredClient clientABC = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("client-abc")
				.clientSecret(passwordEncoder.encode("secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("http://127.0.0.1:8080/path-abc")
				.scope("authority-a")
				.scope("authority-b")
				.scope("authority-c")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build()) // To show consent screen to the user
				.build();
		
		
		final RegisteredClient clientC = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("client-c")
				.clientSecret(passwordEncoder.encode("secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
				.scope("authority-c")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build()) // Consent is not required as this client will be called server-server
				.build();
		
		final RegisteredClient clientcredentialsC = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("clientcredentials-c")
				.clientSecret(passwordEncoder.encode("secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
				.scope("authority-c")
				.build();
		
		return new InMemoryRegisteredClientRepository(loginClient,clientA, clientAB, clientABC, clientC,clientcredentialsC);
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
    public ProviderSettings providerSettings() {
    	System.out.println("AUTH SERVER PORT : "+serverPort);
        return ProviderSettings.builder().issuer("http://auth-server:"+serverPort).build();
    }
	
	
}
