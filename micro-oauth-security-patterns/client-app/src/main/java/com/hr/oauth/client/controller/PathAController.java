package com.hr.oauth.client.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.hr.common.dto.ServiceResponse;
import com.hr.oauth.client.config.ServicesConfiguration;

@RestController
@RequestMapping("/path-a")
public class PathAController extends BaseController{

	protected PathAController(WebClient webClient, ServicesConfiguration servicesConfig) {
		super(webClient, servicesConfig);
	}

	@GetMapping
	@ResponseBody
	public ServiceResponse getUsers(@AuthenticationPrincipal final OidcUser authenticatedUser, @RegisteredOAuth2AuthorizedClient("client-a") OAuth2AuthorizedClient authClient)
	{
		System.out.println("PATH-A Controller --> ID TOKEN >>> "+authenticatedUser.getIdToken().getTokenValue());
		System.out.println("PATH-A Controller --> ACCESS TOKEN >>> "+authClient.getAccessToken().getTokenValue());
		ServiceResponse response = callService(ServicesConfiguration.SERVICE_A, authClient);
		return response;
	}

}
