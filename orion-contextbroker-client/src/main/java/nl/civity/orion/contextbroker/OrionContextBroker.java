/*
 * Copyright (c) 2021, Civity BV Zeist
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holder nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software without 
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nl.civity.orion.contextbroker;

import nl.civity.orion.contextbroker.restclient.OrionContextBrokerRestClient;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import nl.civity.orion.contextbroker.domain.Entity;
import nl.civity.orion.contextbroker.domain.EntityList;
import nl.civity.orion.contextbroker.domain.Subscription;
import nl.civity.orion.contextbroker.domain.SubscriptionList;
import nl.civity.rest.client.RestClient;
import nl.civity.rest.client.RestClientException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basvanmeulebrouk
 */
public class OrionContextBroker {

    private static final RestClient REST_CLIENT = new OrionContextBrokerRestClient();
    
    private static final Logger LOGGER = Logger.getLogger(OrionContextBroker.class.getName());

    protected static final String SUBSCRIPTIONS = "/v2/subscriptions";

    protected static final String ENTITIES = "/v2/entities";

    protected static final String FIWARE_SERVICE_PATH = "Fiware-ServicePath";

    protected static final String FIWARE_SERVICE = "Fiware-Service";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String APPLICATION_JSON = "application/json";

    public static final String TYPE = "type";

    private final String url;
    
    private final String fiwareService;
    
    private final String fiwareServicePath;

    public OrionContextBroker(String url, String fiwareService, String fiwareServicePath) {
        this.url = url.replaceAll("/$", "");
        this.fiwareService = fiwareService;
        this.fiwareServicePath = fiwareServicePath;
    }

    public EntityList getEntities() throws OrionContextBrokerException {
        EntityList result;

        try {
            JSONArray jsonArray = this.getJSONArray(this.url + ENTITIES);
            result = EntityList.fromJSON(jsonArray);
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error getting entities from [%s]: [%s]", this.url, ex.getMessage()), ex);
        }

        return result;
    }

    public EntityList getEntities(String type) throws OrionContextBrokerException {
        EntityList result;

        try {
            JSONArray jsonArray = this.getJSONArray(this.url + ENTITIES + "?" + TYPE + "=" + type);
            result = EntityList.fromJSON(jsonArray);
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error getting entities from [%s]: [%s]", this.url, ex.getMessage()), ex);
        }

        return result;
    }

    public Entity getEntity(String entityId) throws OrionContextBrokerException {
        Entity result;

        try {
            JSONObject jsonObject = this.getJSONObject(this.url + ENTITIES + "/" + entityId);
            result = Entity.fromJSON(jsonObject);
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error getting entity from [%s]: [%s]", this.url, ex.getMessage()), ex);
        }

        return result;
    }

    public void deleteEntity(String entityId) throws OrionContextBrokerException {
        try {
            String urlString = url + ENTITIES + "/" + entityId;

            Map<String, String> headers = this.createHeaders(false);
            Map<String, String> parameters = new HashMap<>();

            // 204 no content in case the entity exists
            // 404 in case the entity does not exist
            String location = REST_CLIENT.deleteHttp(urlString, headers, parameters, new int[]{204, 404});

            LOGGER.info(String.format("Location of deleted entity: %s", location));
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error upserting entity to %s", this.url), ex);
        }
    }


    public void updateEntity(Entity entity) throws OrionContextBrokerException {
        String data = entity.toString();

        updateEntity(data);
    }

    public void updateEntity(String data) throws OrionContextBrokerException {
        try {
            String urlString = url + ENTITIES + "?options=upsert";

            String location = REST_CLIENT.postHttp(urlString, this.createHeaders(true), this.createParameters(), 204, data);

            LOGGER.info(String.format("Location of upserted entity: [%s]", location));
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error upserting entity to  [%s]: [%s]", this.url, ex.getMessage()), ex);
        }
    }

    public String subscribe(Subscription subscription) throws OrionContextBrokerException {
        String subscriptionId;
        
        try {
            String data = subscription.toString();

            String urlString = this.getUrl() + SUBSCRIPTIONS;

            Map<String, String> parameters = createParameters();

            // Verify if the current notificationUrl is already subscribed
            SubscriptionList subscriptions = getSubscriptions();
            
            Subscription existing = subscriptions.findSubscriptionByNotificationUrl(subscription);

            if (existing == null) {
                // If not, create a new subscription
                String location = REST_CLIENT.postHttp(urlString, this.createHeaders(true), parameters, 201, data);
                subscriptionId = location.replace(SUBSCRIPTIONS + "/", "");
            } else {
                // Else, update existing subscription
                REST_CLIENT.patchHttp(urlString + "/" + existing.getSubscriptionId(), this.createHeaders(true), parameters, 204, data);
                subscriptionId = existing.getSubscriptionId();
            }
            
            subscription.setSubscriptionId(subscriptionId);
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error subscribing to [%s]", this.url), ex);
        }
        
        return subscriptionId;
    }

    public SubscriptionList getSubscriptions() throws OrionContextBrokerException {
        SubscriptionList result;
        
        try {
            String urlString = this.getUrl() + SUBSCRIPTIONS;

            JSONArray jsonArray = this.getJSONArray(urlString);

            result = SubscriptionList.fromJSON(jsonArray);
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error listing subscriptions for [%s]", this.getUrl()), ex);
        }

        return result;
    }

    public void unsubscribe(Subscription aSubscription) throws OrionContextBrokerException {
        String urlString = this.getUrl() + SUBSCRIPTIONS + "/" + aSubscription.getSubscriptionId();

        try {
            REST_CLIENT.deleteHttp(urlString, this.createHeaders(false), this.createParameters(), 200);
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error deleting subscription [%s] from [%s]", aSubscription.getSubscriptionId(), this.getUrl()), ex);
        }
    }

    protected String getUrl() {
        return url;
    }

    protected JSONObject getJSONObject(String url) throws RestClientException {
        String data = REST_CLIENT.getHttp(url, this.createHeaders(false), this.createParameters(), 200);
        return new JSONObject(data);
    }

    protected JSONArray getJSONArray(String url) throws RestClientException {
        String data = REST_CLIENT.getHttp(url, this.createHeaders(false), this.createParameters(), 200);
        return new JSONArray(data);
    }

    protected Map<String, String> createHeaders(boolean includeContentType) {
        Map<String, String> headers = new HashMap<>();

        if (includeContentType) {
            headers.put(CONTENT_TYPE, APPLICATION_JSON);
        }

        if (this.fiwareService != null) {
            headers.put(FIWARE_SERVICE, this.fiwareService);
        }
        
        if (this.fiwareServicePath != null) {
            headers.put(FIWARE_SERVICE_PATH, this.fiwareServicePath);
        }
        
        return headers;
    }

    protected Map<String, String> createParameters() {
        return new HashMap<>();
    }

    @Override
    public String toString() {
        return String.format("OrionContextBrokerClient [%s]", this.getUrl());
    }
}
