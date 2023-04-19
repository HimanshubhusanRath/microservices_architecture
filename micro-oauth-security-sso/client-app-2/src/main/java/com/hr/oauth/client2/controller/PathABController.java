package com.hr.oauth.client2.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/path-ab")
public class PathABController {

	@GetMapping
	@ResponseBody
	public String getUsers(@AuthenticationPrincipal OidcUser oidcUser, OAuth2AuthenticationToken authToken)
	{
		{
			System.out.println("ID Token >> "+oidcUser.getIdToken().getTokenValue());
			System.out.println("Nonce in ID TOKEN >> "+oidcUser.getIdToken().getNonce());
			System.out.println("AUTH TOKEN >> "+authToken.toString());
			return authToken.toString();
		}
	}

}
