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

import java.util.logging.Level;
import java.util.logging.Logger;
import nl.civity.oauth.client.domain.Token;
import nl.civity.oauth.client.domain.UserInfo;

/**
 *
 * @author basvanmeulebrouk
 */
public class OAuthClientMain {

    private static final Logger LOGGER = Logger.getLogger(OAuthClientMain.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OAuthClientDescription oAuthClientDescription = new OAuthClientDescription(
                "https://localhost:8443/auth/",
                "oauth_client_test_private",
                "udxyCnheSoMVpqTE5Vq8XHHIJLkHxla2"
        );

        UsernamePasswordOAuthClient usernamePasswordOAuthClient = oAuthClientDescription.createUsernamePasswordOAuthClient(
                "fredrik",
                "qwerty1024"
        );

        try {
            Token token = usernamePasswordOAuthClient.getToken(null);

            LOGGER.log(Level.INFO, token.getAccessToken());
            LOGGER.log(Level.INFO, token.getHeader());
            LOGGER.log(Level.INFO, token.getPayload());

            AccessTokenOAuthClient accessTokenOAuthClient = oAuthClientDescription.createAccessTokenOAuthClient(
                    token.getAccessToken()
            );

            UserInfo userInfo = accessTokenOAuthClient.introspect();

            LOGGER.log(Level.INFO, userInfo.toJSON().toString());
        } catch (OAuthClientException ex) {
            LOGGER.log(Level.SEVERE, "Error in OAuth request. ", ex);
        }
    }
}
