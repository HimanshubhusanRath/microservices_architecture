package com.hr.gatewayservice.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.hr.gatewayservice.config.RedisHashComponent;
import com.hr.gatewayservice.constant.GatewayConstants;
import com.hr.gatewayservice.dto.ApiKey;
import com.hr.gatewayservice.util.MapperUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered{
	
	@Autowired
	private RedisHashComponent redisComponent;
	
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) 
	{
		// Get the api-key from the request header
		List<String> apiKeyHeaders = exchange.getRequest().getHeaders().get("gateway-key");
		// Get the route ID for the service from context. This ID refers to the ID for the target service based on the url matching filters defined in router config.
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		final String routeId = (null!=route) ? route.getId() : null;
		
		if(!isAuthorized(routeId, apiKeyHeaders))
		{
			  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not authorized to access this request. Check your gateway-key"); 
		}
		
		return chain.filter(exchange);
	}

	private boolean isAuthorized(String routeId, List<String> apiKeyHeaders) 
	{
		boolean isAuth = false;
		
		if(null!=routeId && !CollectionUtils.isEmpty(apiKeyHeaders))
		{
			final String apiKeyInRequest = apiKeyHeaders.get(0);
			// Get the service IDs for the given api key
			final ApiKey apiKey = MapperUtil.mapToType(redisComponent.hGet(GatewayConstants.RECORD_KEY, apiKeyInRequest), ApiKey.class);
			if(null!=apiKey && !CollectionUtils.isEmpty(apiKey.getServices()) && apiKey.getServices().contains(routeId))
			{
				isAuth = true;
			}
		}
		
		return isAuth;
	}

	
}
