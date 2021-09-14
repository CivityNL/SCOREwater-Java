# Orion ContextBroker client

Wrapper around the Orion ContextBroker. Allows you to use the Orion ContextBroker from your Java code with little effort. Contains methods to read and update entities and to create, read, update and delete subscriptions. To use this library, check out the SCOREwater-Java repository from Github. The library depends on the SCOREwater REST client library which can be found in the same repository. Use Maven to install the library. 

```
cd <directory_where_you_want_to_clone>
git clone https://github.com/CivityNL/SCOREwater-Java.git
cd SCOREwater-Java/civity-rest-client
mvn clean install
cd SCOREwater-Java/orion-contextbroker-client
mvn clean install
```

The library should now be available in your local Maven repository. 
