# OAuth Java client

Purpose of this library is to facilitate using OAuth2 identity providers in Java programs. It has been tested with the Keycloak OAuth2 identity provider. To use this library, check out the [SCOREwater-Java repository from Github](https://github.com/CivityNL/SCOREwater-Java). The library depends on the SCOREwater REST client library which can be found in the same repository. Use Maven to install the library.

```
cd <directory_where_you_want_to_clone>
git clone https://github.com/CivityNL/SCOREwater-Java.git
cd SCOREwater-Java/civity-rest-client
mvn clean install
cd SCOREwater-Java/oauth-client
mvn clean install
```

The library should now be available in your local Maven repository.

# Running the example program

The library contains an example program OAuthClientMain which demonstrates how to use the library. Spin up the containers defined in the docker-compose file in the docker folder. The docker-compose file contains a PostgreSQL database and a Keycloak instance using this database. Once the containers are up and running, navigate to [your local Keycloak](https://localhost:8443) using a web browser. Since Keycloak is using a self-signed certificate, you may have to explicity trust the certificate. Login to Keycloak (see the docker-compose file for the username and the password). 

1. Create a "scorewater" realm; 
2. Create a "fredrik" user with a password in this realm;
3. Create a client with client ID "oauth_client_test_private" and a client secret in this realm.

Import your self-signed certificate in Java's truststore before running the example program. Importing the self-signed certificate can be down by exporting it from your browser (to which it has been added when you accepted it) and importing it with following command:

```agsl
keytool -import -alias oauth_client_keycloak -keystore $JAVA_HOME/jre/lib/security/cacerts -file ~/Downloads/server.crt
```

You need administrative privileges to be able to do this. 