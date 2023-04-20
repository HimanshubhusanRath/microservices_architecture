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



# Some extracts from the client-application's log

### 1. End user tries to access client application's resource (http://client-a:8081/path-ab)

- o.s.security.web.FilterChainProxy        : Securing GET /path-ab
- o.s.security.web.FilterChainProxy        : Invoking OAuth2AuthorizationRequestRedirectFilter (6/15)
- o.s.security.web.FilterChainProxy        : Invoking OAuth2AuthorizationRequestRedirectFilter (7/15)
- o.s.security.web.FilterChainProxy        : Invoking OAuth2LoginAuthenticationFilter (8/15)
- o.s.security.web.FilterChainProxy        : Invoking AnonymousAuthenticationFilter (11/15)
- o.s.s.w.a.AnonymousAuthenticationFilter  : Set SecurityContextHolder to AnonymousAuthenticationToken [Principal=anonymousUser, Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=null], Granted Authorities=[ROLE_ANONYMOUS]]
- o.s.s.w.a.i.FilterSecurityInterceptor    : Did not re-authenticate AnonymousAuthenticationToken [Principal=anonymousUser, Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=null], Granted Authorities=[ROLE_ANONYMOUS]] before authorizing
- o.s.s.w.a.i.FilterSecurityInterceptor    : Authorizing filter invocation [GET /path-ab] with attributes [authenticated]
- o.s.s.w.a.expression.WebExpressionVoter  : Voted to deny authorization
- o.s.s.w.a.i.FilterSecurityInterceptor    : Failed to authorize filter invocation [GET /path-ab] with attributes [authenticated] using AffirmativeBased [DecisionVoters=[org.springframework.security.web.access.expression.WebExpressionVoter@1dd11ddf], AllowIfAllAbstainDecisions=false]
- o.s.s.w.a.ExceptionTranslationFilter     : Sending AnonymousAuthenticationToken [Principal=anonymousUser, Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=null], Granted Authorities=[ROLE_ANONYMOUS]] to authentication entry point since access is denied

### 2. Access is denied for Anonymous Authentication Token (created by default) as we have configured login flow and the user needs to be authenticated
- org.springframework.security.access.AccessDeniedException: Access is denied

### 3. oAuth2 Flow kicks in and the client redirects to the auth-server for authentication for AUTHORIZATION_CODE grant
- o.s.s.web.DefaultRedirectStrategy        : Redirecting to http://client-a:8081/oauth2/authorization/login-client-1
- o.s.security.web.FilterChainProxy        : Securing GET /oauth2/authorization/login-client-1
- o.s.security.web.FilterChainProxy        : Invoking OAuth2AuthorizationRequestRedirectFilter (6/15)
- o.s.s.web.DefaultRedirectStrategy        : Redirecting to http://auth-server:9000/oauth2/authorize?response_type=code&client_id=loginclient-1&scope=openid&state=9LKd55ZgR2C01tQ686pzlaxAYBhLSrhT31bHPg6bvPU%3D&redirect_uri=http://client-a:8081/login/oauth2/code/login-client-1&nonce=wPYHPuFNApP5X8FYnY_G-wznxs8afnULg2Bw04NPcF4

### 4. Log In page is shown to the customer by the auth-server
### End user successfully logs in
### Post login, auth-server redirects to the client with authorization code

- o.s.security.web.FilterChainProxy        : Securing GET /login/oauth2/code/login-client-1?code=E-HMJwGSqh0HS-CDkzy94sirzJCpsHm2nZA1e21Lo2ElWGv0Rs-aLLar3AfCw1eDM7z5xgPs1u2hPdTsq8ruOTJ87kNaOwX8YvcKartR1nfZBSfUTASFFNgdMySRglOr&state=9LKd55ZgR2C01tQ686pzlaxAYBhLSrhT31bHPg6bvPU%3D
- o.s.security.web.FilterChainProxy        : Invoking OAuth2AuthorizationRequestRedirectFilter (6/15)
- o.s.security.web.FilterChainProxy        : Invoking OAuth2AuthorizationRequestRedirectFilter (7/15)
- o.s.security.web.FilterChainProxy        : Invoking OAuth2LoginAuthenticationFilter (8/15)
- o.s.s.authentication.ProviderManager     : Authenticating request with OAuth2LoginAuthenticationProvider (1/4)
- o.s.s.authentication.ProviderManager     : Authenticating request with OidcAuthorizationCodeAuthenticationProvider (2/4)

### 5. Client connects to the auth-server to get the tokens using the authorization code

- o.s.web.client.RestTemplate              : HTTP POST http://auth-server:9000/oauth2/token
- o.s.web.client.RestTemplate              : Accept=[application/json, application/*+json]
- o.s.web.client.RestTemplate              : Writing [{grant_type=[authorization_code], code=[E-HMJwGSqh0HS-CDkzy94sirzJCpsHm2nZA1e21Lo2ElWGv0Rs-aLLar3AfCw1eDM7z5xgPs1u2hPdTsq8ruOTJ87kNaOwX8YvcKartR1nfZBSfUTASFFNgdMySRglOr], redirect_uri=[http://client-a:8081/login/oauth2/code/login-client-1]}] as "application/x-www-form-urlencoded;charset=UTF-8"
- o.s.web.client.RestTemplate              : Response 200 OK
- o.s.web.client.RestTemplate              : Reading to [org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse] as "application/json;charset=UTF-8"

### 6. Client connects to the auth-server to get the JWS algorithm for token validation
----------------------------------------------------------------------------------

- o.s.web.client.RestTemplate              : HTTP GET http://auth-server:9000/oauth2/jwks
- o.s.web.client.RestTemplate              : Accept=[text/plain, application/json, application/*+json, */*]
- o.s.web.client.RestTemplate              : Response 200 OK
- o.s.web.client.RestTemplate              : Reading to [java.lang.String] as "application/json;charset=ISO-8859-1"

### 7. Auth token is validated at the client successfully and session is created at the client.

- o.s.CompositeSessionAuthenticationStrategy : Preparing session with ChangeSessionIdAuthenticationStrategy (1/1)
- o.s.ChangeSessionIdAuthenticationStrategy : Changed session id from 6F9AD118911EAD9B8B8524974BE1CDC6
- o.s.o.c.w.OAuth2LoginAuthenticationFilter : Set SecurityContextHolder to OAuth2AuthenticationToken [Principal=Name: [a@a.com], Granted Authorities: [[ROLE_USER, SCOPE_openid]], User Attributes: [{sub=a@a.com, aud=[loginclient-1], azp=loginclient-1, iss=http://auth-server:9000, exp=2023-04-20T05:44:08Z, iat=2023-04-20T05:14:08Z, nonce=wPYHPuFNApP5X8FYnY_G-wznxs8afnULg2Bw04NPcF4}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=6F9AD118911EAD9B8B8524974BE1CDC6], Granted Authorities=[ROLE_USER, SCOPE_openid]]
- o.s.s.web.DefaultRedirectStrategy        : Redirecting to http://client-a:8081/path-ab
- o.s.security.web.FilterChainProxy        : Securing GET /path-ab
- o.s.security.web.FilterChainProxy        : Invoking SecurityContextPersistenceFilter (2/15)
- w.c.HttpSessionSecurityContextRepository : Retrieved SecurityContextImpl [Authentication=OAuth2AuthenticationToken [Principal=Name: [a@a.com], Granted Authorities: [[ROLE_USER, SCOPE_openid]], User Attributes: [{sub=a@a.com, aud=[loginclient-1], azp=loginclient-1, iss=http://auth-server:9000, exp=2023-04-20T05:44:08Z, iat=2023-04-20T05:14:08Z, nonce=wPYHPuFNApP5X8FYnY_G-wznxs8afnULg2Bw04NPcF4}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=6F9AD118911EAD9B8B8524974BE1CDC6], Granted Authorities=[ROLE_USER, SCOPE_openid]]] from SPRING_SECURITY_CONTEXT
- s.s.w.c.SecurityContextPersistenceFilter : Set SecurityContextHolder to SecurityContextImpl [Authentication=OAuth2AuthenticationToken [Principal=Name: [a@a.com], Granted Authorities: [[ROLE_USER, SCOPE_openid]], User Attributes: [{sub=a@a.com, aud=[loginclient-1], azp=loginclient-1, iss=http://auth-server:9000, exp=2023-04-20T05:44:08Z, iat=2023-04-20T05:14:08Z, nonce=wPYHPuFNApP5X8FYnY_G-wznxs8afnULg2Bw04NPcF4}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=6F9AD118911EAD9B8B8524974BE1CDC6], Granted Authorities=[ROLE_USER, SCOPE_openid]]]
- o.s.security.web.FilterChainProxy        : Invoking OAuth2AuthorizationRequestRedirectFilter (6/15)
- o.s.security.web.FilterChainProxy        : Invoking OAuth2AuthorizationRequestRedirectFilter (7/15)
- o.s.security.web.FilterChainProxy        : Invoking OAuth2LoginAuthenticationFilter (8/15)
- o.s.s.w.s.HttpSessionRequestCache        : Loaded matching saved request http://client-a:8081/path-ab
- o.s.s.w.a.AnonymousAuthenticationFilter  : Did not set SecurityContextHolder since already authenticated OAuth2AuthenticationToken [Principal=Name: [a@a.com], Granted Authorities: [[ROLE_USER, SCOPE_openid]], User Attributes: [{sub=a@a.com, aud=[loginclient-1], azp=loginclient-1, iss=http://auth-server:9000, exp=2023-04-20T05:44:08Z, iat=2023-04-20T05:14:08Z, nonce=wPYHPuFNApP5X8FYnY_G-wznxs8afnULg2Bw04NPcF4}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=6F9AD118911EAD9B8B8524974BE1CDC6], Granted Authorities=[ROLE_USER, SCOPE_openid]]
- o.s.security.web.FilterChainProxy        : Invoking OAuth2AuthorizationCodeGrantFilter (12/15)
- o.s.security.web.FilterChainProxy        : Invoking SessionManagementFilter (13/15)
- o.s.security.web.FilterChainProxy        : Invoking ExceptionTranslationFilter (14/15)
- o.s.security.web.FilterChainProxy        : Invoking FilterSecurityInterceptor (15/15)
- o.s.s.w.a.i.FilterSecurityInterceptor    : Did not re-authenticate OAuth2AuthenticationToken [Principal=Name: [a@a.com], Granted Authorities: [[ROLE_USER, SCOPE_openid]], User Attributes: [{sub=a@a.com, aud=[loginclient-1], azp=loginclient-1, iss=http://auth-server:9000, exp=2023-04-20T05:44:08Z, iat=2023-04-20T05:14:08Z, nonce=wPYHPuFNApP5X8FYnY_G-wznxs8afnULg2Bw04NPcF4}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=6F9AD118911EAD9B8B8524974BE1CDC6], Granted Authorities=[ROLE_USER, SCOPE_openid]] before authorizing
- o.s.s.w.a.i.FilterSecurityInterceptor    : Authorizing filter invocation [GET /path-ab] with attributes [authenticated]
- o.s.s.w.a.i.FilterSecurityInterceptor    : Authorized filter invocation [GET /path-ab] with attributes [authenticated]

### 8. Finally the resource (/path-ab) is accessed successfully

- o.s.security.web.FilterChainProxy        : Secured GET /path-ab

- ID Token >> eyJraWQiOiIzMDRiMWI5MC1lNzAxLTQ0NDktOTZmOS00MWY4NmE0YjE4MTgiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhQGEuY29tIiwiYXVkIjoibG9naW5jbGllbnQtMSIsImF6cCI6ImxvZ2luY2xpZW50LTEiLCJpc3MiOiJodHRwOlwvXC9hdXRoLXNlcnZlcjo5MDAwIiwiZXhwIjoxNjgxOTY5NDQ4LCJpYXQiOjE2ODE5Njc2NDgsIm5vbmNlIjoid1BZSFB1Rk5BcFA1WDhGWW5ZX0ctd3pueHM4YWZuVUxnMkJ3MDROUGNGNCJ9.aaFiawU5M0t24r2Rs1_TqOJYu13OTV1XPR8LllSftMCNBvAQLWTRvfQhgXiOn0T0EWQ3seW0DjaMLCgxpIwVKbgvgMJa-sg6rLuEbc1uSwvaPtOePIc7Lb9qi9St_jKoxpvdmE5ULFdqin2dtIVaWwfKLk77ZMabeutQBgc4Vnfaxwr_ZBZ0uZzBlpGKPXKK0RBfGJ3P9viEnd-Jkw1h20mVqwS3v_pyauiPEzPCKX5-p-iv6WPnWaF0g16fPphEMWje0yYiTu-dkb5W-Jk2oSlzpEWEvs7qMnRvgv3Akq0GCT5EXwD4BXLPfit9FO4BpoGfoUxKa4flZe40hG8ySw
- Nonce in ID TOKEN >> wPYHPuFNApP5X8FYnY_G-wznxs8afnULg2Bw04NPcF4
- AUTH TOKEN >> OAuth2AuthenticationToken [Principal=Name: [a@a.com], Granted Authorities: [[ROLE_USER, SCOPE_openid]], User Attributes: [{sub=a@a.com, aud=[loginclient-1], azp=loginclient-1, iss=http://auth-server:9000, exp=2023-04-20T05:44:08Z, iat=2023-04-20T05:14:08Z, nonce=wPYHPuFNApP5X8FYnY_G-wznxs8afnULg2Bw04NPcF4}], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=127.0.0.1, SessionId=6F9AD118911EAD9B8B8524974BE1CDC6], Granted Authorities=[ROLE_USER, SCOPE_openid]]

