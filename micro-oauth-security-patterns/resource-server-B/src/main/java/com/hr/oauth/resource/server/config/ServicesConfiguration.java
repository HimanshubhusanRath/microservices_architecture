package com.hr.oauth.resource.server.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.configs")
public class ServicesConfiguration {

	public static final String SERVICE_A = "service-a";
	public static final String SERVICE_B = "service-b";
	
	
	private Map<String, ServiceConfig> services;
	
	public Map<String, ServiceConfig> getServices() {
		return this.services;
	}

	public void setServices(Map<String, ServiceConfig> services) {
		this.services = services;
	}
	
	public ServiceConfig getConfig(final String serviceId)
	{
		return services.get(serviceId);
	}

	public static class ServiceConfig
	{
		private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
		
	}
}
