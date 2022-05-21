package com.hr.oauth.resource.server.controller;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

import java.net.URI;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.hr.common.dto.ServiceResponse;
import com.hr.oauth.resource.server.config.ServicesConfiguration;
import com.hr.oauth.resource.server.config.ServicesConfiguration.ServiceConfig;

public abstract class BaseController {

	private WebClient webClient;
	private ServicesConfiguration servicesConfig;
	
	protected BaseController(final WebClient webClient, final ServicesConfiguration servicesConfig)
	{
		this.webClient = webClient;
		this.servicesConfig = servicesConfig;
	}
	
	/**
	 * Call using JWT token
	 * 
	 * @param jwt
	 * @return
	 */
	protected ServiceResponse callServiceC(final Jwt jwt)
	{
	
		ServiceConfig serviceConfig = this.servicesConfig.getConfig("service-c");
		UriComponentsBuilder uriCompBuilder = UriComponentsBuilder.fromUriString(serviceConfig.getUrl());
		URI serviceUri = uriCompBuilder.build().toUri();
		
		return this.webClient
				.get()
				.uri(serviceUri)
				.headers(headers -> headers.setBearerAuth(jwt.getTokenValue()))
				.retrieve()
				.bodyToMono(ServiceResponse.class)
				.block();
		
	}
	
	/**
	 * Call using Client registration ID
	 * 
	 * @param clientRegistrationId
	 * @return
	 */
	protected ServiceResponse callServiceC(final String clientRegistrationId)
	{
	
		ServiceConfig serviceConfig = this.servicesConfig.getConfig("service-c");
		UriComponentsBuilder uriCompBuilder = UriComponentsBuilder.fromUriString(serviceConfig.getUrl());
		URI serviceUri = uriCompBuilder.build().toUri();
		
		return this.webClient
				.get()
				.uri(serviceUri)
				.attributes(clientRegistrationId(clientRegistrationId))
				.retrieve()
				.bodyToMono(ServiceResponse.class)
				.block();
	}
}
