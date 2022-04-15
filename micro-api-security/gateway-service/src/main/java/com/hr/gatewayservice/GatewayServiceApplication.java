package com.hr.gatewayservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hr.gatewayservice.config.RedisHashComponent;
import com.hr.gatewayservice.constant.GatewayConstants;
import com.hr.gatewayservice.dto.ApiKey;

@SpringBootApplication
public class GatewayServiceApplication {

	private final static String KEY1 = "ASDSA909ASD-ASD5T5G5G5G5-F4L6J3K45H3V43";
	private final static String KEY2 = "A8C83K38D8DJ3-FR88SDS7878-FDF4343FDF342DA";
	
	@Autowired
	private RedisHashComponent redisComponent;
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@PostConstruct
	public void initializeRedis()
	{
		final List<ApiKey> keys = new ArrayList();
		keys.add(new ApiKey(KEY1, Stream.of(GatewayConstants.COURSE_SERVICE_KEY, GatewayConstants.STUDENT_SERVICE_KEY).collect(Collectors.toList())));
		keys.add(new ApiKey(KEY2, Stream.of(GatewayConstants.COURSE_SERVICE_KEY).collect(Collectors.toList())));
		
		keys.forEach(key -> redisComponent.hSet(GatewayConstants.RECORD_KEY, key.getKey(), key));
	}
}
