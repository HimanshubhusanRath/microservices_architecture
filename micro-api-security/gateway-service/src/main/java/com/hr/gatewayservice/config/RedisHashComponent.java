package com.hr.gatewayservice.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.hr.gatewayservice.util.MapperUtil;

@Component
public class RedisHashComponent {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public void hSet(final String hashKey, final String key, final Object value)
	{
		final Map valueMap = MapperUtil.mapToType(value, Map.class);
		redisTemplate.opsForHash().put(hashKey, key, valueMap);
	}
	
	public Object hGet(final String hashKey, final String key)
	{
		return redisTemplate.opsForHash().get(hashKey, key);
	}
	
	public List<Object> getAll(final String hashKey)
	{ 
		return redisTemplate.opsForHash().values(hashKey);
	}
}
