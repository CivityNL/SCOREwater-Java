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
package nl.civity.rest.client;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.ParseException;
import org.apache.http.entity.StringEntity;

/**
 * Implementation of RestClient interface using Apache http components library
 * @author basvanmeulebrouk
 */
public class ApacheHttpRestClient extends AbstractRestClient {

    private static final Logger LOGGER = Logger.getLogger(ApacheHttpRestClient.class.getName());

    @Override
    public String deleteHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int[] expectedReturnCodes) throws RestClientException {
        String result;

        try {
            HttpDelete httpDelete = new HttpDelete(buildURI(uri, parameters));
            setHeaders(httpDelete, headers);
            result = this.execute(httpDelete, expectedReturnCodes);
        } catch (URISyntaxException e) {
            throw new RestClientException("Failed to build a HTTP delete request", e);
        }

        return result;
    }

    @Override
    public String getHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int[] expectedReturnCodes) throws RestClientException {
        String result;

        try {
            HttpGet httpGet = new HttpGet(buildURI(uri, parameters));
            setHeaders(httpGet, headers);
            result = this.execute(httpGet, expectedReturnCodes);
        } catch (URISyntaxException e) {
            throw new RestClientException("Failed to build a HTTP get request", e);
        }

        return result;
    }

    @Override
    public String patchHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int[] expectedReturnCodes, String body) throws RestClientException {
        String result;

        try {
            HttpPatch httpPatch = new HttpPatch(buildURI(uri, parameters));
            setHeaders(httpPatch, headers);
            httpPatch.setEntity(this.bodyToHttpEntity(body));
            result = this.execute(httpPatch, expectedReturnCodes);
        } catch (URISyntaxException e) {
            throw new RestClientException("Failed to build a HTTP patch request", e);
        }

        return result;
    }

    @Override
    public String postHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int[] expectedReturnCodes, String body) throws RestClientException {
        String result;

        try {
            HttpPost httpPost = new HttpPost(buildURI(uri, parameters));
            setHeaders(httpPost, headers);
            httpPost.setEntity(this.bodyToHttpEntity(body));
            result = this.execute(httpPost, expectedReturnCodes);
        } catch (URISyntaxException e) {
            throw new RestClientException("Failed to build a HTTP post request", e);
        }

        return result;
    }

    @Override
    public String putHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int[] expectedReturnCodes, String body) throws RestClientException {
        String result;

        try {
            HttpPut httpPut = new HttpPut(buildURI(uri, parameters));
            setHeaders(httpPut, headers);
            httpPut.setEntity(this.bodyToHttpEntity(body));
            result = this.execute(httpPut, expectedReturnCodes);
        } catch (URISyntaxException e) {
            throw new RestClientException("Failed to build a HTTP put request", e);
        }

        return result;
    }

    protected String checkResponse(CloseableHttpClient httpClient, HttpRequestBase httpRequest, int[] expectedReturnCodes) throws IOException, RestClientException {
        String result;

        try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
            result = getResponse(response);
            
            checkResponseCode(response, expectedReturnCodes, httpRequest, result);

            LOGGER.log(Level.INFO, "Successfully executed HTTP request: {0}", httpRequest.getURI().toASCIIString());
        }

        return result;
    }

    protected String getResponse(final CloseableHttpResponse response) throws IOException, ParseException {
        String result = "";
        
        if (response.getEntity() != null) {
            result = (EntityUtils.toString(response.getEntity()));
        }
        
        return result;
    }

    protected void checkResponseCode(final CloseableHttpResponse response, int[] expectedReturnCodes, HttpRequestBase httpRequest, String result) throws RestClientException {
        int responseCode = response.getStatusLine().getStatusCode();
        
        boolean found = false;
        
        for (Integer expectedReturnCode : expectedReturnCodes) {
            if (responseCode == expectedReturnCode) {
                found = true;
                break;
            }
        }
        
        if (!found) {
            throw new RestClientException(String.format("Error [%s] executing request to [%s]. The server returned: [%s]", response.getStatusLine(), httpRequest.getURI().toASCIIString(), result));
        }
    }

    protected void setHeaders(HttpRequestBase httpRequest, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            httpRequest.setHeader(key, value);
        }
    }

    protected URI buildURI(String uri, Map<String, String> parameters) throws URISyntaxException {
        URIBuilder result = new URIBuilder(uri);

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            result.addParameter(key, value);
        }

        return result.build();
    }

    protected String execute(HttpRequestBase httpRequest, int[] expectedReturnCodes) throws RestClientException {
        String result;

        try {
            try (PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(); CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build()) {
                result = checkResponse(httpClient, httpRequest, expectedReturnCodes);
            }
        } catch (IOException ex) {
            throw new RestClientException("Error executing request", ex);
        }

        return result;
    }

    protected HttpEntity bodyToHttpEntity(String body) throws RestClientException {
        try {
            return new StringEntity(body);
        } catch (UnsupportedEncodingException ex) {
            throw new RestClientException(String.format("Unsupported encoding while creating HttpEntity from String [%s]", body), ex);
        }
    }
}
