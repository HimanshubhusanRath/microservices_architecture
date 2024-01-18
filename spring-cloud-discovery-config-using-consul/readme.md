# This project demonstrates the HashiCorp's CONSUL for service discovery as well as distributed configuration with Spring Cloud

## Project Setup
* Service Discovery:
  * Consul (by HashiCorp) — [spring-cloud-starter-consul-discovery]
* Configuration Management:
  * Consul (by HashiCorp) — [spring-cloud-starter-consul-config]
* Routing:
  * Spring Cloud Gateway — [spring-cloud-starter-gateway]


### How to use Consul
* Installation:
* Run: 
  * ./consul agent -dev -node <machine-name>
* View members of consul:
  * ./consul members
* To add configurations for 'gateway-service', do the following:
  * Define the name in bootstrap.yml
    * spring:
      * application:
        * name: gateway-service
  * Add the configurations in Consul UI under Key/Value.
    * Key/Folder : config/<service-name>/data (e.g. config/gateway-service/data)
    * Value: Configurations in YAML format.


## Note:
* When the service will start, it will register itself in Consul and also reads the configurations from Consul. (Refer the bootstrap.yml in 'gateway-service' service to see this concept in action)
* Routes defined in the gateway-service:
    * id: caller-service-route
    * uri: lb://caller-service 
    * predicates:
    *   - Path=/caller/**
* This means if the request URL matches with (/caller/**), then redirect this to (http://caller-service) 

## Test the application:
* Use 'http://localhost:8091/caller' to access the caller service endpoint.




