package com.hr.oauth.client.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.hr.common.dto.ServiceResponse;
import com.hr.oauth.client.config.ServicesConfiguration;

@RestController
@RequestMapping("/path-ab")
public class PathABController extends BaseController{

	protected PathABController(WebClient webClient, ServicesConfiguration servicesConfig) {
		super(webClient, servicesConfig);
	}

	@GetMapping
	@ResponseBody
	public List<ServiceResponse> getUsers(@RegisteredOAuth2AuthorizedClient("client-ab") OAuth2AuthorizedClient authClient, OAuth2AuthenticationToken oauthAuthentication)
	{
		ServiceResponse responseA = callService(ServicesConfiguration.SERVICE_A, authClient);
		ServiceResponse responseB = callService(ServicesConfiguration.SERVICE_B, authClient);
		return Arrays.asList(responseA, responseB);
	}

}
