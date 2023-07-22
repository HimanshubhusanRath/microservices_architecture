# Resillience4j

In this project, the below features of Resillience4j are explored.
* Circuit-breaker
* Retry mechanism
* Rate Limiter

### Circuit Breaker:
* Here the circuit is opened if there is a failure percentage more than the threshold.
* If circuit is CLOSED, then the response is fetched from the original source and if circuit is OPEN, then the response is served from the fallback source.
* Here the call is not blocked.

### Retry Mechanism:
* Here, if the original source is not available/serviceable, then a retry is made to the original source after the configured amount of time delay.
* After retrying for the configured retry count, if the original source is still not available, then the response is served from the fallback source.
* Here the call is blocked, till the configured number of retries are made, before serving from the fallback source.

### Rate Limiter:
* Here, we define with in a configured span of time (e.g. 10 seconds), how many requests (e.g. 30) are allowed to be executed.
* Only the allowed requests will get a chance to execute whereas the non-allowed requests will be served with the fall-back method.
* Rate Limiter Algorithm:
  * AtomicRateLimiter algorithm is used here.
  * This can be considered a variation of 'Token Bucket' algorithm.
  * This algorithm divides the entire duration into equal partitions of Nano-seconds (CYCLE) and applies rate limiting logic.
* NOTE:
  * The below events are triggered as part of this activity.
      * Successfully acquired the permission [Approved request]
      * Failure in acquiring the permission [Denied request]
  * These events can be consumed/accessed by using the EventPublisher in RateLimiter.
