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
import nl.civity.oauth.client.domain.UserInfo;

/**
 * OAuthClient with an access token. Another component is responsible for
 * getting (and refreshing) a token from the server. This client can (a) verify
 * the access token and (b) get userInfo using this access token.
 *
 * @author basvanmeulebrouk
 */
public class AccessTokenOAuthClient extends OAuthClient {

    private final String accessToken;

    /**
     * Constructor with an access token. Another component is responsible for
     * getting (and refreshing) a token from the server. This client can (a)
     * verify the access token and (b) get userInfo suing this access token
     *
     * @param openIdUrl URL of the open ID provider
     * @param clientId Client ID
     * @param clientSecret Client secret
     * @param accessToken Access token obtained from open ID provider
     */
    public AccessTokenOAuthClient(String openIdUrl, String clientId, String clientSecret, String accessToken) {
        super(openIdUrl, clientId, clientSecret);
        this.accessToken = accessToken;
    }

    public UserInfo introspect() throws OAuthClientException {
        Map<String, String> headers = createHeaders();
        
        HashMap<String, String> formData = new HashMap<>();
        formData.put(CLIENT_ID, this.getClientId());
        formData.put(CLIENT_SECRET, this.getClientSecret());
        formData.put(TOKEN, this.accessToken);
        
        return getUserInfoFromServer(this.getTokenIntrospectionEndpoint(), headers, formData);
    }
    
    protected UserInfo getUserInfoFromServer(String url, Map<String, String> headers, Map<String, String> formData) throws OAuthClientException {
        return UserInfo.fromJSON(postFormData(url, headers, formData));
    }
}
