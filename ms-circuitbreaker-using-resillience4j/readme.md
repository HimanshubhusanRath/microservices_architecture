# Resillience4j

In this project, the below features of Resillience4j are explored.
* Circuit-breaker
* Retry mechanism

### Circuit Breaker:
* Here the circuit is opened if there is a failure percentage more than the threshold.
* If circuit is CLOSED, then the response is fetched from the original source and if circuit is OPEN, then the response is served from the fallback source.
* Here the call is not blocked.

### Retry Mechanism:
* Here, if the original source is not available/serviceable, then a retry is made to the original source after the configured amount of time delay.
* After retrying for the configured retry count, if the original source is still not available, then the response is served from the fallback source.
* Here the call is blocked, till the configured number of retries are made, before serving from the fallback source.