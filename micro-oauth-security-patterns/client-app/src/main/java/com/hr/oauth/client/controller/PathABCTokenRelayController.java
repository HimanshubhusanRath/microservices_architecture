package com.hr.oauth.client.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.hr.common.dto.ServiceResponse;
import com.hr.oauth.client.config.ServicesConfiguration;

@RestController
@RequestMapping(path="/path-abc", params = "flow_type=token_relay")
public class PathABCTokenRelayController extends BaseController{

	private static final String FLOW_TYPE_PARAMETER = "flow_type";
	private static final String FLOW_TYPE_TOKEN_RELAY = "token_relay";
	
	
	protected PathABCTokenRelayController(WebClient webClient, ServicesConfiguration servicesConfig) {
		super(webClient, servicesConfig);
	}

	@GetMapping
	@ResponseBody
	public List<ServiceResponse> getUsers(@RegisteredOAuth2AuthorizedClient("client-abc") OAuth2AuthorizedClient authClient)
	{
		ServiceResponse responseA = callService(ServicesConfiguration.SERVICE_A, authClient);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.put(FLOW_TYPE_PARAMETER, Collections.singletonList(FLOW_TYPE_TOKEN_RELAY));
		
		ServiceResponse responseB = callService(ServicesConfiguration.SERVICE_B, authClient,params);
		return Arrays.asList(responseA, responseB);
	}

}
