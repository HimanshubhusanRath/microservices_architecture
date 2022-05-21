package com.hr.oauth.client.controller;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.hr.common.dto.ServiceResponse;
import com.hr.oauth.client.config.ServicesConfiguration;
import com.hr.oauth.client.config.ServicesConfiguration.ServiceConfig;


@RestController
@RequestMapping("/path-c")
public class PathCController extends BaseController{

	protected PathCController(WebClient webClient, ServicesConfiguration servicesConfig) {
		super(webClient, servicesConfig);
	}

	@GetMapping
	@ResponseBody
	public ServiceResponse getUsers(OAuth2AuthenticationToken oauthAuthentication)
	{
		System.out.println("CALLING C-SERVICE USING client-credentials");
		ServiceResponse response = callServiceC("clientcredentials-c"); 
		return response;
	}
	
	/**
	 * Call using Client registration ID
	 * 
	 * @param clientRegistrationId
	 * @return
	 */
	protected ServiceResponse callServiceC(final String clientRegistrationId)
	{
	
		final ServiceConfig serviceConfig = this.servicesConfig.getConfig("service-c");
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
