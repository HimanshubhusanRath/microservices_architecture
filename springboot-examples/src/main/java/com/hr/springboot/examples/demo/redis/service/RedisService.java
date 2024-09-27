package com.hr.springboot.examples.demo.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveData(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getData(final String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(final String key){
        redisTemplate.delete(key);
    }

}
