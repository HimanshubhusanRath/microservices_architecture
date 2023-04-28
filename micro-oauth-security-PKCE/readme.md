# Spring Security with OAuth2 OpenID Connect using Authorization code with PKCE

### Modules Details:
* Auth-server --> This is the authentication provider server which authenticates the clients using OpenID connect on top of OAuth2.
* client-app ---> This is the client application.

## How to enable Authorization code with PKCE flow 
### Authorization Server side configuration:
* In the client registrations, do the below:
  * Set the authentication_method as 'none'
  * Set the authorization grant type as 'authorization_code'
  * Client secret need not be mentioned



### Client side configuration:
##### In the client registration (application.yml), do the below:
  * client_authentication_method: none
  * Client secret need not be mentioned
  * authorization-grant-type: authorization_code


### Local Setup

* Please add the below entries in the /etc/hosts file to consider the domain names.

  *     127.0.0.1   auth-server
  *     127.0.0.1   client-a
  *     127.0.0.1   client-b



### Test

* Add the user details for authentication 
  * TBD

  
* Access the second client's API
  * https://client-b:8082/path-ab


