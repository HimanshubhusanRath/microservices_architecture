package com.hr.springboot.examples.demo.customactuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthChecker implements HealthIndicator {
    @Override
    public Health health() {
        boolean isHealthy = true;
        if(isHealthy) {
            return Health.up().withDetail("user-service","Available").build();
        } else {
            return Health.down().withDetail("user-service","Unavailable").build();
        }
    }
}
