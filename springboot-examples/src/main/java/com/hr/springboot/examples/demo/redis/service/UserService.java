package com.hr.springboot.examples.demo.redis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Cacheable(value = "users", key = "#userId")
    public String getUser(final String userId){
        // Stimulate latency
        try {
            Thread.sleep(10000);
        } catch(final Exception e){

        }
        return "USER-"+userId;
    }
}
