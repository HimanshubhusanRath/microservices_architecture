**Overview:**

**1.Services:**

1. Config-server loads the configs from Github config repo.
2. Config-client reads the configs from this Config-server.

**2.Config Update**

1. The Config-server and Config-client - both are connected to a Kafka topic ('springCloudBus' -- automatically created by Spring boot).
2. Whenever there is a config update in Github, we do a manual POST request to the '/actuator/busrefresh' endpoint of the Config-server instance. Upon refresh, this Config-server creates an event ('RefreshRemoteApplicationEvent') and publish this to the above Kafka topic.
3. The Config-client - which has already subscribed to this topic - gets this event from this Kafka queue and refreshes itself (processes '/actuator/refresh' endpoint). As part of this refresh, Config-client fetches the configs again from Config-server (in turn, Config-server fetches the latest configs from GitHub).
4. Communications between these services and Kafka is done by using Spring Cloud Bus.

NOTE:
* In this project, the service discovery part (using CONSUL) is not demonstrated.
* To make these services discoverable, we need to add the CONSUL dependency for them.
* However, service discovery configurations are already mentioned in these services.
