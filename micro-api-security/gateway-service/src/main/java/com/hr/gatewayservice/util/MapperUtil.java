package com.hr.gatewayservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MapperUtil {

	final ObjectMapper mapper = new ObjectMapper();
	
	public <T> T mapToType(final Object value, final Class<T> clazz)
	{
		return mapper.convertValue(value, clazz);
	}
}
