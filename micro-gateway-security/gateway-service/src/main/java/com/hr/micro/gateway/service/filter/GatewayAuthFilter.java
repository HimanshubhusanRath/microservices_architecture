package com.hr.micro.gateway.service.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GatewayAuthFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// Get the api-key from the request header
		List<String> apiKeyHeaders = exchange.getRequest().getHeaders().get("Authorization");
		// Get the route ID for the service from context. This ID refers to the ID for
		// the target service based on the url matching filters defined in router
		// config.
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		final String routeId = (null != route) ? route.getId() : null;

		if (!isAuthorized(routeId, apiKeyHeaders)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
					"You are not authorized to access this request. Check your gateway-key");
		}

		return chain.filter(exchange);
	}

	/**
	 * Currently we are simply returning true from this method.
	 * 
	 * We can also call the auth-server apis to validate the token in the header.
	 * We can also create another new token if required. 
	 * 
	 * THIS PART NEEDS TO BE EXPLORED MORE
	 * 
	 * 
	 * @param routeId
	 * @param apiKeyHeaders
	 * @return
	 */
	private boolean isAuthorized(String routeId, List<String> apiKeyHeaders) {
		return true;
	}

}
