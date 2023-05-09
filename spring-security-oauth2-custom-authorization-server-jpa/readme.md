# Custom oAuth2 Authorization server with JPA
* This project is a oAuth2 authorization server with the below customizations.
  * Database (JPA) entities:
    * Authority - to store user roles
    * Client - to store client registrations
    * SecurityUser - to store user information
    * Authorization - to store the authorization details for a client including tokens (access, refresh and id - tokens)

  * Configurations/Services:
    * JpaOauth2AuthorizationService - to use custom 'Authorization' entity for authorizations
    * JpaRegisteredClientRepository - to use custom 'Client' entity for client registrations 
    * JpaUserDetailsManager - to use custom 'SecurityUser' entity for getting user details

## Testing
### Create the test data in the database
* Run the application once so that Hibernate will generate the tables in the database.
* Execute the below SQLs to create entries in the tables. 
------
INSERT INTO authorities(authority) VALUES('ROLE_USER');
INSERT INTO authorities(authority) VALUES('ROLE_ADMIN');
INSERT INTO authorities(authority) VALUES('ROLE_DEVELOPER');
INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('Dev', '$2a$04$Ef7nckypldOGgWiCBcw91.UY8uNquwk2b5FnH/KfrZtTsKNxq7oJO', true, true, true, true);
INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('Admin', '$2a$04$Ef7nckypldOGgWiCBcw91.UY8uNquwk2b5FnH/KfrZtTsKNxq7oJO', true, true, true, true);
INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('User', '$2a$04$Ef7nckypldOGgWiCBcw91.UY8uNquwk2b5FnH/KfrZtTsKNxq7oJO', true, true, true, true);

INSERT INTO users_authorities(users_id, authorities_id) VALUES (1, 1);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (1, 2);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (1, 3);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (2, 1);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (2, 2);
INSERT INTO users_authorities(users_id, authorities_id) VALUES (3, 1); 
-----

INSERT INTO client(id, authorization_grant_types, client_authentication_methods, client_id, client_id_issued_at, client_name,
client_secret, client_secret_expires_at, client_settings, redirect_uris, scopes, token_settings)
VALUES('client-a', 'refresh_token,client_credentials,authorization_code', 'client_secret_basic',
'client', null, 'client-a', '$2a$04$KAIw6PrRcJNOYN5/dQJLcepDgtW/6bAKxJLuW7lQJnpBgyKfLEyI2', null,
'{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":true,"settings.client.require-authorization-consent":true}',
'http://insomnia,http://127.0.0.1:8080/login/oauth2/code/client', 'read,openid,profile',
'{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,
"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],
"settings.token.access-token-time-to-live":["java.time.Duration",86400.000000000],
"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",
"value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],
"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}');
------


* Use the below credentials to get tokens using a POSTMAN client as mentioned in the below diagram.
  * Credentials:
    * User-id: User
    * Password: pass
    
* Note:
  * If scope is mentioned as 'openid' --> All the three tokens (access, refresh and id token) are returned in the authorization response.
  * If scope is mentioned as 'profile' --> Only two tokens (access, refresh token) are returned in the response.
  * ID token is only returned in the response, if scope is OPENID

* ![Screen Shot 2023-05-09 at 6.51.44 AM.png](..%2F..%2F..%2F..%2F..%2F..%2FDesktop%2FScreen%20Shot%202023-05-09%20at%206.51.44%20AM.png)
