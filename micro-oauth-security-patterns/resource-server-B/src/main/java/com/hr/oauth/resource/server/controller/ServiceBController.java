package com.hr.oauth.resource.server.controller;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hr.common.dto.ServiceResponse;

@RestController
@RequestMapping("/service-b")
public class ServiceBController {

	@GetMapping
	@ResponseBody
	public ServiceResponse serviceResponse(final JwtAuthenticationToken jwtAuthToken, HttpServletRequest httpRequest)
	{
		System.out.println("-- Service-B Controller --\n Access Token ====> "+jwtAuthToken.getToken().getTokenValue());
		ServiceResponse response = new ServiceResponse();
		response.setServiceName("service-b");
		response.setServiceUri(httpRequest.getRequestURL().toString());
		response.setJti(jwtAuthToken.getToken().getId());
		response.setSub(jwtAuthToken.getToken().getSubject());
		response.setAud(jwtAuthToken.getToken().getAudience());
		response.setAuthorities(jwtAuthToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		return response;
	}
	
}
