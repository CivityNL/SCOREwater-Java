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
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.civity.orion.contextbroker.domain.Entity;
import nl.civity.orion.contextbroker.domain.EntityList;
import nl.civity.orion.contextbroker.domain.Subscription;
import nl.civity.orion.contextbroker.domain.SubscriptionList;
import nl.civity.rest.client.ApacheHttpRestClient;
import nl.civity.rest.client.RestClient;
import nl.civity.rest.client.RestClientException;
import org.json.JSONArray;

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

    protected static final String CONTENT_TYPE = "Content-Type";

    protected static final String APPLICATION_JSON = "application/json";

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

    public void updateEntity(Entity entity) throws OrionContextBrokerException {
        String data = entity.toString();

        updateEntity(data);
    }

    public void updateEntity(String data) throws OrionContextBrokerException {
        try {
            String urlString = url + ENTITIES + "?options=upsert";

            String location = REST_CLIENT.postHttp(urlString, this.createHeaders(), this.createParameters(), 204, data);

            LOGGER.info(String.format("Location of upserted entity: [%s]", location));
        } catch (RestClientException ex) {
            throw new OrionContextBrokerException(String.format("Error upserting entity to  [%s]: [%s]", this.url, ex.getMessage()), ex);
        }
    }

    public String subscribe(Subscription subscription) throws OrionContextBrokerException {
        String subscriptionId = null;
        
        try {
            String data = subscription.toString();

            String urlString = this.getUrl() + SUBSCRIPTIONS;

            Map<String, String> parameters = createParameters();

            // Verify if the current notificationUrl is already subscribed
            SubscriptionList subscriptions = getSubscriptions();
            
            Subscription existing = subscriptions.findSubscriptionByNotificationUrl(subscription);

            if (existing == null) {
                // If not, create a new subscription
                String location = REST_CLIENT.postHttp(urlString, this.createHeaders(), parameters, 201, data);
                subscriptionId = location.replace(SUBSCRIPTIONS + "/", "");
            } else {
                // Else, update existing subscription
                REST_CLIENT.patchHttp(urlString + "/" + existing.getSubscriptionId(), this.createHeaders(), parameters, 204, data);
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
            REST_CLIENT.deleteHttp(urlString, this.createHeaders(), this.createParameters(), 200);
        } catch (RestClientException ex) {
            Logger.getLogger(OrionContextBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String getUrl() {
        return url;
    }
    
    protected JSONArray getJSONArray(String url) throws RestClientException {
        String data = REST_CLIENT.getHttp(url, this.createHeaders(), this.createParameters(), 200);
        
        JSONArray result = new JSONArray(data);
        
        return result;
    }

    protected Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        
        if (this.fiwareService != null) {
            headers.put(FIWARE_SERVICE, this.fiwareService);
        }
        
        if (this.fiwareServicePath != null) {
            headers.put(FIWARE_SERVICE_PATH, this.fiwareServicePath);
        }
        
        return headers;
    }

    protected Map<String, String> createParameters() {
        Map<String, String> parameters = new HashMap<>();
        
        return parameters;
    }
}
