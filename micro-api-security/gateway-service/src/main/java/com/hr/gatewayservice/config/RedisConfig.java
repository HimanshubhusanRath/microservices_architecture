package com.hr.gatewayservice.config;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

	private final static String REDIS_HOST = "spring.redis.host";
	private final static String REDIS_PORT = "spring.redis.port";
	
	@Autowired
	private Environment environment;
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory()
	{
		final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(
							Objects.requireNonNull(environment.getProperty(REDIS_HOST)),
							Integer.parseInt(Objects.requireNonNull(environment.getProperty(REDIS_PORT))));
		return new JedisConnectionFactory(redisConfig);
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate()
	{
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}
}
