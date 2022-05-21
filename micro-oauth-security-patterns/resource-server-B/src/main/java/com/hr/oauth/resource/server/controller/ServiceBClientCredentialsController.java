package com.hr.oauth.resource.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.hr.common.dto.ServiceResponse;
import com.hr.oauth.resource.server.config.ServicesConfiguration;

@RestController
@RequestMapping(path="/service-b", params = {"flow_type=client_credentials"})
public class ServiceBClientCredentialsController extends BaseController{

	protected ServiceBClientCredentialsController(WebClient webClient, ServicesConfiguration servicesConfig) {
		super(webClient, servicesConfig);
	}

	@GetMapping
	@ResponseBody
	public ServiceResponse serviceResponse(final JwtAuthenticationToken jwtAuthToken, HttpServletRequest httpRequest)
	{
		System.out.println(" ======================= SERVICE-C - USING 'CLIENT-CREDENTIALS' ============");
		ServiceResponse response = callServiceC("client-c");
		return response;
	}
	
}
