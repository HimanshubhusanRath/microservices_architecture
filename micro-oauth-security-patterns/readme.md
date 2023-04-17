# In this project, the below mechanisms are used

#### 1. Open ID Connect (OIDC) for AUTHENTICATION
#### 2. OAuth2 for AUTHORIZATION

### Oauth2 Grant Types (covered in this project) 
**1. authorization_code:** 
Here, the client asks the user (resource owner) to authorize the access to certain resources. The user is shown the login page where he enters his credentials for login. 
Once authenticated, he is asked to authorize the client to access the intended resource. Once the user authorizes, the client gets the authorization-code from the auth-server. 
Now, using this authorization code, the client gets the access token from the auth-server. This access token is used to access the intended resource.
This step needs user (resource owner) intervention. 

E.g. a user logging in by using his gmail account. 
During login, the system asks the user to access grant to the client (xyz.com) to read your profile information from Gmail.
[Analogy: Using your key to open your drawer and allowing others to put items inside]


**2. client_credentials:**
Here, the client is authenticated and authorized by using the credentials (client ID and SECRET). Here, the user (resource owner) does not come into picture. 
[Analogy: Sharing your key with others so that they can open your drawer and put items inside]


**PROJECT STRUCTURE:**
1. Authentication-Server
2. Client-application
3. Service-A
4. Service-B
5. Service-C

**COMPONENT DETAILS:**
1. Client-Application is completely secured and requires authentication for all urls except '/path-c' pattern.
2. Service-A is accessible for any authenticated request having a scope as 'authority-a'
3. Service-B is accessible for any authenticated request having a scope as 'authority-b'
4. Service-C is accessible for any authenticated request having a scope as 'authority-c'
5. Authentication-Server is responsible for providing authentication and authorization.


**COMPONENT COMMUNICATIONS:**

![Project-flow](https://user-images.githubusercontent.com/40859584/169707229-5704f904-f677-405e-8bd9-f2a533cbfe8a.png)


1. Authentication is carried out by the client ['loginclient'] available in Client-application.
2. Client-Application ==> Service-A
    - Using client [client-a], grant-type = authorization_code, scope=authority-a
        * Test URL : http://localhost:8080/path-a
3. Client-Application ==> Service-C
    - Using client [client-c], grant-type = authorization_code, scope=authority-c
        * Test URL : http://localhost:8080/path-c
4. Client-Application ==> Service-A & Service-B
    - Using client [client-ab], grant-type = authorization_code, scope=authority-a, authority-b
        * Test URL : http://localhost:8080/path-ab
5. Client-Application ==> Service-A, Service-B, Service-C (Service-C is not directly accessed rather it is accessed from Service-B)
    - **Flow-token-relay**
      1. Using the below client[client-abc], grant-type=authorization_code, scope=authority-a, authority-b, authority-c
      2. Here the client is having access to all the services (A,B & C). So, the token is simply passed on to the subsequent service calls.
        * Test URL : http://localhost:8080/path-abc?flow_type=token_relay
    - **Flow-client-credentials**
      1. Using client[client-ab], grant-type=authorization_code, scope=authority-a, authority-b.
      2. Here the client is having access only to service-A and service-B. 
      3. From service-B, we use a different client [client-c], grant-type=client_credentials, scope=authority-c
      4. Using this client, service-B accesses service-C. For this access, user authorization does not come into picture as we are using client credentials.
        * Test URL : http://localhost:8080/path-abc?flow_type=client_credentials


# Overview of the authentication & authorization flow 

![Authorization-DetailedFlow](https://user-images.githubusercontent.com/40859584/230719547-6a3d599b-3418-4a13-9090-178d2d9918ce.png)

## Note
- In the CLIENT-APP, only one client 'login_client' is used for AUTHENTICATION with scope as openid. 
  Other clients are used for AUTHORIZATION with different scopes e.g. authority-a / authority-b etc.

  AUTHENTICATION is not specific to an individual client rather it is specific to the CLIENT-APP (containing all clients).
  So, LOG IN happens only once (by 'login_client') per the CLIENT-APP (once done, it is applicable for all clients)


### How the auth-server & client-app remember the authentication ?
  - When login is successful, a JSESSIONID cookie is set by the auth-server
  - When the authorizedClient is created in the client-app as part of 'login/oauth2/code/*' call, a JESSIONID cookie is set by the client-app
  - These cookies are used to identify the request as authenticated.


