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
import nl.civity.oauth.client.domain.Token;

/**
 * OAuthClient with username and password. This client can get a token from the
 * OAuth server (and refresh a token if needed) using this username and
 * password.
 *
 * @author basvanmeulebrouk
 */
public class UsernamePasswordOAuthClient extends OAuthClient {

    private final String userName;

    private final String password;

    /**
     * Constructor with username and password. This client can get a token from
     * the OAuth server (and refresh a token if needed) using this username and
     * password.
     *
     * @param openIdUrl URL of open ID provider
     * @param clientId Client ID
     * @param clientSecret Client secret
     * @param userName Username of logged-in user
     * @param password Password of logged-in user
     */
    public UsernamePasswordOAuthClient(String openIdUrl, String clientId, String clientSecret, String userName, String password) {
        super(openIdUrl, clientId, clientSecret);
        this.password = password;
        this.userName = userName;
    }
    
    public Token getToken(Token token) throws OAuthClientException {
        Token result;
        
        if (token != null) {
            if (token.isRefreshExpired()) {
                result = this.getNewToken();
            } else {
                result = this.refreshToken(token);
            }
        } else {
            result = this.getNewToken();
        }
        
        return result;
    }

    protected Token getNewToken() throws OAuthClientException {
        Map<String, String> headers = createHeaders();

        HashMap<String, String> formData = new HashMap<>();
        formData.put(CLIENT_ID, this.getClientId());
        formData.put(CLIENT_SECRET, this.getClientSecret());
        formData.put(USERNAME, this.userName);
        formData.put(PASSWORD, this.password);
        formData.put(GRANT_TYPE, "password");
        formData.put(SCOPE, "openid");

        return getTokenFromServer(headers, formData);
    }

    protected Token refreshToken(Token token) throws OAuthClientException {
        Map<String, String> headers = createHeaders();

        HashMap<String, String> formData = new HashMap<>();
        formData.put(CLIENT_ID, this.getClientId());
        formData.put(CLIENT_SECRET, this.getClientSecret());
        formData.put(REFRESH_TOKEN, token.getRefreshToken());
        formData.put(GRANT_TYPE, "refresh_token");

        return getTokenFromServer(headers, formData);
    }

    protected Token getTokenFromServer(Map<String, String> headers, Map<String, String> formData) throws OAuthClientException {
        return Token.fromJSON(postFormData(this.getTokenEndpoint(), headers, formData));
    }
}
