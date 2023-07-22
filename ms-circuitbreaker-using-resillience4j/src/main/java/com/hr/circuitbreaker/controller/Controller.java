package com.hr.circuitbreaker.controller;

import com.hr.circuitbreaker.dto.Activity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private static final String REST_ENDPOINT = "https://www.boredapi.com/api/activity";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/activity/failure-handling")
    @CircuitBreaker(name = "myActivity",fallbackMethod = "fallbackActivity")
    public String getActivityWithFailureHanding()
    {
        final ResponseEntity<Activity> response = restTemplate.getForEntity(REST_ENDPOINT,Activity.class);
        final Activity activity = response.getBody();
        LOGGER.info("Activity received from API : "+activity.getActivity());
        return activity.getActivity();
    }

    @GetMapping("/activity/retry")
    @Retry(name = "retryMethod",fallbackMethod = "fallbackActivity")
    public String getActivityWithRetry()
    {
        LOGGER.info("Retrying at "+ Calendar.getInstance().getTime());
        final ResponseEntity<Activity> response = restTemplate.getForEntity(REST_ENDPOINT,Activity.class);
        final Activity activity = response.getBody();
        return activity.getActivity();
    }

    @GetMapping("/activity/rate-limit")
    @RateLimiter(name = "rateLimitMethod",fallbackMethod = "fallbackActivityForRateLimit")
    public ResponseEntity<String> getActivityWithRateLimit(@RequestParam("id")final int id)
    {
        final long startTime = System.currentTimeMillis();
        LOGGER.info("Request ID ["+id+"] Allowed to call API at : "+ Calendar.getInstance().getTime());
        final ResponseEntity<Activity> response = restTemplate.getForEntity(REST_ENDPOINT,Activity.class);
        final Activity activity = response.getBody();
        LOGGER.info("Request ID ["+id+"] ---> Total Time Taken : "+(System.currentTimeMillis() - startTime) +" ms");
        return ResponseEntity.ok(activity.getActivity());
    }

    private String fallbackActivity(Throwable throwable)
    {
        LOGGER.info(">>>>> FALLBACK");
        return "-- No activity available --";
    }

    private ResponseEntity<String> fallbackActivityForRateLimit(@RequestParam("id")final int id, Throwable throwable)
    {
        LOGGER.error("Request ID ["+id+"] >>>>> FALLBACK"+throwable.getLocalizedMessage());
        return new ResponseEntity<String>("-- No activity available --", HttpStatus.BAD_REQUEST);
    }

}
