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
package nl.civity.orion.contextbroker.domain;

import java.util.ArrayList;
import nl.civity.orion.contextbroker.OrionContextBrokerException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author basvanmeulebrouk
 */
public class Subscription {

    private final ArrayList<String> ids = new ArrayList<>();

    private String subscriptionId;
    private String description;
    private String idPattern;
    private String notificationUrl;
    private String status;
    private String type;

    public Subscription(String subscriptionId, String description, String notificationUrl, String status) {
        this.subscriptionId = subscriptionId;
        this.description = description;
        this.idPattern = ".*";
        this.notificationUrl = notificationUrl;
        this.status = status;
        this.type = null;
    }

    public void addId(String id) {
        this.ids.add(id);
    }

    public String getId(int index) {
        return this.ids.get(index);
    }

    public int numIds() {
        return this.ids.size();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdPattern() {
        return idPattern;
    }

    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject toJSON() {
        JSONObject notification = new JSONObject();
        notification.put("http", createHttpJSONObject());

        JSONObject subject = new JSONObject();
        subject.put("entities", createEntitiesJSONObject());

        JSONObject result = new JSONObject();

        result.put("description", this.getDescription());
        result.put("status", this.getStatus());
        result.put("subject", subject);
        result.put(NOTIFICATION, notification);
        result.put("throttling", 0);

        return result;
    }

    protected JSONObject createHttpJSONObject() throws JSONException {
        JSONObject http = new JSONObject();
        http.put("url", this.getNotificationUrl());
        return http;
    }

    protected JSONArray createEntitiesJSONObject() throws JSONException {
        JSONArray entities = new JSONArray();

        if (this.numIds() > 0) {
            // Either include the list of ID's...
            for (String id : this.ids) {
                JSONObject entity = new JSONObject();
                entity.put("id", id);
                entities.put(entity);
            }
        } else {
            // ... or the ID pattern and the type. 
            JSONObject entity = new JSONObject();
            entity.put("idPattern", this.idPattern);
            if (this.type != null) {
                entity.put("type", this.type);
            }
            entities.put(entity);
        }

        return entities;
    }

    public static Subscription fromJSON(JSONObject jsonObject) throws OrionContextBrokerException {
        String description = jsonObject.getString("description");
        String id = jsonObject.getString("id");
        String status = jsonObject.getString("status");
        String notificationUrl = notificationUrlFromJSON(jsonObject);

        Subscription result = new Subscription(id, description, notificationUrl, status);

        return result;
    }

    private static String notificationUrlFromJSON(JSONObject jsonObject) throws JSONException, OrionContextBrokerException {
        String result = null;

        if (jsonObject.has(NOTIFICATION)) {
            Object notificationObject = jsonObject.get(NOTIFICATION);
            if (notificationObject instanceof JSONObject) {
                JSONObject notificationJSONObject = (JSONObject) notificationObject;
                Object httpObject = notificationJSONObject.get("http");
                if (httpObject instanceof JSONObject) {
                    JSONObject httpJSONObject = (JSONObject) httpObject;
                    result = httpJSONObject.getString("url");
                } else {
                    throw new OrionContextBrokerException(String.format("%s is not a %s", httpObject.getClass().getName(), JSONObject.class.getName()));
                }
            } else {
                throw new OrionContextBrokerException(String.format("%s is not a %s", notificationObject.getClass().getName(), JSONObject.class.getName()));
            }
        } else {
            throw new OrionContextBrokerException(String.format("Node [%s] not found in [%s]. This is not a notification JSON object", NOTIFICATION, jsonObject.toString()));
        }

        return result;
    }
    protected static final String NOTIFICATION = "notification";

    @Override
    public String toString() {
        return this.toJSON().toString();
    }
}
