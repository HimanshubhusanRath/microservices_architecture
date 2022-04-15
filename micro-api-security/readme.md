**Overview:**

1. Each microservice is identified by one key (name it SERVICE-KEY).
2. Now, different authorizing groups and the corresponding service accesses are defined by mapping between auth-key and these service keys.
   e.g. Student service key = CS-1
        Course service key = CR-1
   One authorizing group is defined as AUTH-1 which has access to both Student service and Course service. So, AUTH-1 : {CS-1, CR-1}
   Whereas another authorizing group is defined as AUTH-2 having access only to Course service. So, AUTH-2 : {CR-1}

**Request Authentication during despatch:**
1. When a client requests for a service api, it need to send this AUTH key in the header. e.g. AUTH-1.
2. The Gateway identifies the intended service from the request url pattern e.g. /api/course-service/** is mapped to the Course service. The Gateway knows the SERVICE-KEY for this service.
3. Now, in the auth filter, we get the service keys configured under this AUTH-1 key and check if the target service key is listed in these configured services.
4. If this is listed, then the request is allowed to be served or else, a 401 (UNAUTHORIZED) exception is thrown.
