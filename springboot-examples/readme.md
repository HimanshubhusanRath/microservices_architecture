# Custom Annotation:
* http://localhost:8080/custom-annotation/a --> Returns 'Slave:2.0- Data' as this is annotated by @UseSlave
* http://localhost:8080/custom-annotation/b --> Returns 'null-Data' as this is not annotated

# Event Handling:
* http://localhost:8080/event-handling/winner/teamA:
  * This endpoint creates an event.
  * There are 2 listeners to this event. 
    * **Synchronous** : Prints a message in the console before response is returned.  
    * **Asynchronous** : Prints a message in the console asynchronously after the response is returned.

# Custom Actuators:
* http://localhost:8080/actuator/health:
  * A custom health checker ```HealthIndicator``` is defined where we can check a 3rd party service (user-service)'s availability, 
    and we can mark the health of the server as UP or DOWN.
    ```
    "healthChecker": {
        "status": "UP",
        "details": {
            "user-service": "Available"
        }
    }
    ```
  * This information will be available in the actuator ```/health``` endpoint and can be used by Monitoring Tools e.g. Prometheus & Grafana setup.
* http://localhost:8080/actuator/custom-metrics:
  * This is a REST endpoint which uses the Micrometer library to get the real time health checks for the current server. 
  * In this example, we are getting the request count and average response time for the current server.
# Redis Integration in Spring Boot:
  * Using Lettuce redis-client
  

