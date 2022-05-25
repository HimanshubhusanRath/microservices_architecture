package com.hr.oauth.client.controller;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import java.net.URI;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.hr.common.dto.ServiceResponse;
import com.hr.oauth.client.config.ServicesConfiguration;
import com.hr.oauth.client.config.ServicesConfiguration.ServiceConfig;

public abstract class BaseController {

	protected WebClient webClient;
	protected ServicesConfiguration servicesConfig;
	
	protected BaseController(final WebClient webClient, final ServicesConfiguration servicesConfig)
	{
		this.webClient = webClient;
		this.servicesConfig = servicesConfig;
	}
	
	protected ServiceResponse callService(final String serviceID, final OAuth2AuthorizedClient authorizedClient)
	{
		return callService(serviceID, authorizedClient, new LinkedMultiValueMap<String, String>());
	}
	
	protected ServiceResponse callService(final String serviceID, final OAuth2AuthorizedClient authorizedClient, final MultiValueMap<String, String> params)
	{
	
		ServiceConfig serviceConfig = this.servicesConfig.getConfig(serviceID);
		UriComponentsBuilder uriCompBuilder = UriComponentsBuilder.fromUriString(serviceConfig.getUrl());
		
		if(!params.isEmpty())
		{
			uriCompBuilder.queryParams(params);
		}
		
		URI serviceUri = uriCompBuilder.build().toUri();
		
		return this.webClient
				.get()
				.uri(serviceUri)
				.attributes(oauth2AuthorizedClient(authorizedClient))
				.retrieve()
				.bodyToMono(ServiceResponse.class)
				.block();
		
	}
}
