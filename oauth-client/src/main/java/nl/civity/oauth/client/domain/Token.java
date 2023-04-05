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
package nl.civity.oauth.client.domain;

import java.beans.Transient;
import java.time.ZonedDateTime;
import java.util.Base64;

import org.json.JSONObject;

/**
 *
 * @author basvanmeulebrouk
 */
public class Token {

    public static final Base64.Decoder DECODER = Base64.getUrlDecoder();
    private String accessToken;
    private int expiresIn;
    private int refreshExpiresIn;
    private String refreshToken;
    private String tokenType;
    private int notBeforePolicy;
    private String sessionState;
    private String scope;

    private final ZonedDateTime createdAt;

    public Token(String accessToken, int expiresIn, int refreshExpiresIn, String refreshToken, String tokenType, int notBeforePolicy, String sessionState, String scope) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshExpiresIn = refreshExpiresIn;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.notBeforePolicy = notBeforePolicy;
        this.sessionState = sessionState;
        this.scope = scope;
        
        this.createdAt = ZonedDateTime.now();
    }
    
    public boolean isExpired() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiresAt = this.createdAt.plusSeconds(this.expiresIn);
        return expiresAt.isAfter(now);
    }

    public boolean isRefreshExpired() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime refreshExpiresAt = this.createdAt.plusSeconds(this.refreshExpiresIn);
        return refreshExpiresAt.isAfter(now);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(int refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getNotBeforePolicy() {
        return notBeforePolicy;
    }

    public void setNotBeforePolicy(int notBeforePolicy) {
        this.notBeforePolicy = notBeforePolicy;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public static Token fromJSON(JSONObject tokenJSONObject) {
        String accessToken = tokenJSONObject.getString("access_token");
        int expiresIn = tokenJSONObject.getInt("expires_in");
        int refreshExpiresIn = tokenJSONObject.getInt("refresh_expires_in");
        String refreshToken = tokenJSONObject.getString("refresh_token");
        String tokenType = tokenJSONObject.getString("token_type");
        int notBeforePolicy = tokenJSONObject.getInt("not-before-policy");
        String sessionState = tokenJSONObject.getString("session_state");
        String scope = tokenJSONObject.getString("scope");
        
        return new Token(accessToken, expiresIn, refreshExpiresIn, refreshToken, tokenType, notBeforePolicy, sessionState, scope);
    }

    @Transient
    public String getHeader() {
        String[] parts = split();
        return new String(DECODER.decode(parts[0]));
    }

    @Transient
    public String getPayload() {
        String[] parts = split();
        return new String(DECODER.decode(parts[1]));
    }

    private String[] split() {
        return this.getAccessToken().split("\\.");
    }

    @Override
    public String toString() {
        return "Token{" + "accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", refreshExpiresIn=" + refreshExpiresIn + ", refreshToken=" + refreshToken + ", tokenType=" + tokenType + ", notBeforePolicy=" + notBeforePolicy + ", sessionState=" + sessionState + ", scope=" + scope + ", createdAt=" + createdAt + '}';
    }
}
