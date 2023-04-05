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

import org.json.JSONObject;

/**
 * @author basvanmeulebrouk
 */
public class UserInfo {

    public static final String ACTIVE = "active";
    public static final String EMAIL = "email";
    public static final String EMAIL_VERIFIED = "email_verified";
    public static final String FAMILY_NAME = "family_name";
    public static final String GIVEN_NAME = "given_name";
    public static final String NAME = "name";
    public static final String PREFERRED_USERNAME = "preferred_username";
    public static final String SCOPE = "scope";
    public static final String SESSION_STATE = "session_state";
    public static final String USER_NAME = "username";

    private String sessionState;
    private String name;
    private String givenName;
    private String familyName;
    private String preferredUsername;
    private String email;
    private Boolean emailVerified;
    private Boolean active;

    public UserInfo(Boolean active) {
        this.active = active;
    }

    public UserInfo(String sessionState, String name, String givenName, String familyName, String preferredUsername, String email, Boolean emailVerified, Boolean active) {
        this.sessionState = sessionState;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.preferredUsername = preferredUsername;
        this.email = email;
        this.emailVerified = emailVerified;
        this.active = active;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPreferredUsername() {
        return preferredUsername;
    }

    public void setPreferredUsername(String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public static UserInfo fromJSON(JSONObject userInfoJSONObject) {
        String sessionState = getOptionalString(userInfoJSONObject, SESSION_STATE);
        String name = getOptionalString(userInfoJSONObject, NAME);
        String givenName = getOptionalString(userInfoJSONObject, GIVEN_NAME);
        String familyName = getOptionalString(userInfoJSONObject, FAMILY_NAME);
        String preferredUsername = getOptionalString(userInfoJSONObject, PREFERRED_USERNAME);
        String email = getOptionalString(userInfoJSONObject, EMAIL);
        Boolean emailVerified = getOptionalBoolean(userInfoJSONObject, EMAIL_VERIFIED);
        Boolean active = userInfoJSONObject.getBoolean(ACTIVE);

        return new UserInfo(sessionState, name, givenName, familyName, preferredUsername, email, emailVerified, active);
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();

        result.put(SESSION_STATE, this.getSessionState());
        result.put(NAME, this.getName());
        result.put(GIVEN_NAME, this.getGivenName());
        result.put(FAMILY_NAME, this.getFamilyName());
        result.put(PREFERRED_USERNAME, this.getPreferredUsername());
        result.put(EMAIL, this.getEmail());
        result.put(EMAIL_VERIFIED, this.getEmailVerified());
        result.put(ACTIVE, this.getActive());

        return result;
    }

    protected static Boolean getOptionalBoolean(JSONObject jsonObject, String tagName) {
        Boolean result = null;

        if (jsonObject.has(tagName)) {
            result = jsonObject.getBoolean(tagName);
        }

        return result;
    }

    protected static String getOptionalString(JSONObject jsonObject, String tagName) {
        String result = null;

        if (jsonObject.has(tagName)) {
            result = jsonObject.getString(tagName);
        }

        return result;
    }

    @Override
    public String toString() {
        return "UserInfo{" + "sessionState=" + sessionState + ", name=" + name + ", givenName=" + givenName + ", familyName=" + familyName + ", preferredUsername=" + preferredUsername + ", email=" + email + ", emailVerified=" + emailVerified + ", active=" + active + '}';
    }
}
