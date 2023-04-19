# Single Sign On with Spring Security + Oauth2 OpenID Connect

### Modules Details:
* Auth-server --> This is the authentication provider server which authenticates the clients using OpenID connect on top of OAuth2.
* client-app-1 ---> This is the first client application.
* client-app-2 ---> This is the second client application.


### How stuff works:
* Both these client applications are secured and grant access to their APIs only for authenticated users/requests.
* When user tries to access any one of these clients for the first time, the client can see that request as un-authenticated.
* So, the client redirects the user agent (end user's browser) to the auth-server.
* The auth-server shows the login page to the end user.
* The end user then authenticates himself by entering his credentials and then the user is considered as authenticated for client-app-1 and can access the resource (/path-ab) now.
* Right after that when the end user tries to access the other client (client-app-2)'s API, he need not log in again by entering his credentials as there exists a valid session for auth-server (as part of first log in). 
* This way, the user is logged in only once and can access both client's API because of the single sign on that just happened.


### Under The Hood:
* When user logs in as part of trying to access Client-App-1's API:
  * One cookie is created for AUTH-SERVER as part of log in which denotes the user's session crated at the AUTH-SERVER. 
  * One cookie is created for CLIENT-APP-1 for the session created for the user in CLIENT-APP-1 server.

* When user tries to access Client-App-2's API:
    * Existing cookie for AUTH-SERVER is available which identifies the user's session at AUTH-SERVER. So, no login is needed. 
    * One cookie is created for CLIENT-APP-2 for the session created for the user in CLIENT-APP-2 server.

Now onwards, when user tries to access each of these clients, the respective client specific cookies are considered to identify the client session and APIs are accessed by the end user. 



### Local Setup

* Please add the below entries in the /etc/hosts file to consider the domain names.

  *     127.0.0.1   auth-server
  *     127.0.0.1   client-a
  *     127.0.0.1   client-b



### Test

* Add the user details for authentication 
  * TBD


* Access the first client's API
  * https://client-a:8081/path-ab
* Access the second client's API
  * https://client-b:8082/path-ab

