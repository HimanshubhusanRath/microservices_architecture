Project flow
----------------
1. A client-app calls to two microservices. Service-a and Service-b.
2. These two services are secured and requires a JWT token with proper scopes in order to allow access to their underlying services.
3. The client-app calls these two services via a gateway-service.
4. There is an global filter in the gateway-service which intercepts these service calls and checks for the existence of auth token in the header. From this place, we can do various operations e.g. validation of these tokens, new token generation (to be explored) and many more.


Steps
----------
* Install Consul and run the below command to start consul: 
<pre>
consul agent -dev -node <machine-name>
<br/>
e.g. consul agent -dev -node MY-MACHINE
</pre>
* Once consul is started, start the gateway-service and other services.


API Call
--------
1. Client-app to only service-a --> http://localhost:8080/path-a
2. Client-app to both service-a and service-b --> http://localhost:8080/path-ab

