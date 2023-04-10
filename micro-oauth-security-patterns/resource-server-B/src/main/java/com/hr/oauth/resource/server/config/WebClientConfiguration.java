package com.hr.oauth.resource.server.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.JwtBearerOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

	@Bean
	OAuth2AuthorizedClientManager oauth2AuthClientManager(
			final ClientRegistrationRepository clientRegistrationRepository,
			final OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository)
	{
		final OAuth2AuthorizedClientProvider authorizedClientProvider = 
				OAuth2AuthorizedClientProviderBuilder.builder()
					.clientCredentials()
					.provider(new JwtBearerOAuth2AuthorizedClientProvider())
					.build();
		
		final DefaultOAuth2AuthorizedClientManager clientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,oAuth2AuthorizedClientRepository);
		clientManager.setAuthorizedClientProvider(authorizedClientProvider);
		clientManager.setContextAttributesMapper(contextAttributesMapper());
		return clientManager;
	}
	
	private Function<OAuth2AuthorizeRequest, Map<String, Object>> contextAttributesMapper() {
		return authorizedRequest -> 
		{
			Map<String, Object> contextAttributes = Collections.emptyMap();
			if(authorizedRequest.getPrincipal() instanceof JwtAuthenticationToken)
			{
				contextAttributes = new HashMap<String, Object>();
				contextAttributes.put("jwt_attribute_name", ((JwtAuthenticationToken) authorizedRequest.getPrincipal()).getToken());
			}
			
			return contextAttributes;
		};
	}

	@Bean
	WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		// We can use UnAuthenticatedServerOAuth2AuthorizedClientRepository if this no user flow comes in to picture (purely server-server communication)
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
		oauth2Client.setDefaultClientRegistrationId("client-c-exchange");
		return WebClient.builder()
				.apply(oauth2Client.oauth2Configuration())
				.build();
	}
	
}
