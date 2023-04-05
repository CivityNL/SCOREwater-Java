/*
 * Copyright (c) 2023, Civity BV Zeist
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
package nl.civity.oauth.client;

import java.util.HashMap;
import java.util.Map;
import nl.civity.rest.client.ApacheHttpRestClient;
import nl.civity.rest.client.RestClient;
import nl.civity.rest.client.RestClientException;
import org.json.JSONObject;

/**
 *
 * @author basvanmeulebrouk
 */
public class OAuthClient {
    
    private final String clientId;

    private final String clientSecret;

    private final String openIdUrl;

    private static final RestClient REST_CLIENT = new ApacheHttpRestClient();
    
    protected static final String CLIENT_ID = "client_id";
    protected static final String CLIENT_SECRET = "client_secret";
    protected static final String GRANT_TYPE = "grant_type";
    protected static final String PASSWORD = "password";
    protected static final String REFRESH_TOKEN = "refresh_token";
    protected static final String SCOPE = "scope";
    protected static final String TOKEN = "token";
    protected static final String USERNAME = "username";

    public OAuthClient(String openIdUrl, String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.openIdUrl = openIdUrl;
    }
    
    protected String getTokenEndpoint() {
        return this.getOpenIdUrl() + "token";
    }
    
    protected String getTokenIntrospectionEndpoint() {
        return this.getOpenIdUrl() + "token/introspect";
    }
    
    protected String getAuthorizeEndpoint() {
        return this.getOpenIdUrl() + "auth";
    }
    
    protected String getUserInfoEndpoint() {
        return this.getOpenIdUrl() + "userinfo";
    }
    
    protected String getUserInfoLogoutEndpoint() {
        return this.getOpenIdUrl() + "logout";
    }
    
    protected String getRedirectUri() {
        return this.getAuthorizeEndpoint();
    }

    protected String getOpenIdUrl() {
        String result = openIdUrl;
        
        if (! result.endsWith("/")) {
            result += "/";
        }
        
        return result;
    }

    protected String getClientId() {
        return clientId;
    }

    protected String getClientSecret() {
        return clientSecret;
    }
    
    protected JSONObject postFormData(String url, Map<String, String> headers, Map<String, String> formData) throws OAuthClientException {
        JSONObject result;
        
        Map<String, String> parameters = new HashMap<>();

        try {
            result = new JSONObject(REST_CLIENT.postHttpFormData(url, headers, parameters, 200, formData));
        } catch (RestClientException ex) {
            throw new OAuthClientException(String.format("Error posting form data to OAuth server endpoint [%s]", url), ex);
        }
        
        return result;
    }

    protected HashMap<String, String> createHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }
}
