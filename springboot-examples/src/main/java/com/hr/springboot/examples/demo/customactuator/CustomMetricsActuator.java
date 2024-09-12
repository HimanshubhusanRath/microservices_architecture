package com.hr.springboot.examples.demo.customactuator;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/actuator/custom-metrics")
public class CustomMetricsActuator {

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getLoadBalancerMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        // Retrieve request count and response times using built-in Micrometer metrics
        metrics.put("requestCount", getRequestCount());
        metrics.put("averageResponseTime", getAverageResponseTime());

        return ResponseEntity.ok(metrics);
    }

    private long getRequestCount() {
        // Use Timer to get total request count
        Timer timer = meterRegistry.find("http.server.requests").timer();
        return timer != null ? (long) timer.count() : 0;
    }

    private double getAverageResponseTime() {
        // Use Timer to get average response time
        Timer timer = meterRegistry.find("http.server.requests").timer();
        return timer != null ? timer.mean(TimeUnit.MILLISECONDS) : 0.0;
    }

}
