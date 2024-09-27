package com.hr.springboot.examples.demo.redis.controller;

import com.hr.springboot.examples.demo.redis.service.RedisService;
import com.hr.springboot.examples.demo.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis/example")
public class RedisExampleController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @GetMapping("/get-user")
    public ResponseEntity<String> getUser(final String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/save")
    public void saveData(final String key, final String value) {
        redisService.saveData(key, value);
    }

    @GetMapping("/get")
    public String getData(final String key) {
        return redisService.getData(key);
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteData(final String key) {
        redisService.deleteData(key);
        return ResponseEntity.ok("Deleted");
    }
}
