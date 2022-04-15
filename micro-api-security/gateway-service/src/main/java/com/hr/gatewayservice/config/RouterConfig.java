package com.hr.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hr.gatewayservice.constant.GatewayConstants;

@Configuration
public class RouterConfig {

	@Bean 
	public RouteLocator routeLocator(final RouteLocatorBuilder builder)
	{
		return builder.routes()
				// Define the route ID, the filter url pattern and the destination uri for student service 
				.route(GatewayConstants.STUDENT_SERVICE_KEY, 
						r->r.path("/api/student-service/**")
						.filters(f -> f.stripPrefix(2)).uri("http://localhost:8081"))
				// Define the route ID, the filter url pattern and the destination uri for course service
				.route(GatewayConstants.COURSE_SERVICE_KEY,
						r->r.path("/api/course-service/**")
						.filters(f->f.stripPrefix(2)).uri("http://localhost:8082")
			).build();
						
	}
}
