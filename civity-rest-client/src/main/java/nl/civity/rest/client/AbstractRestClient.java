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

import java.util.Map;

/**
 *
 * @author basvanmeulebrouk
 */
public abstract class AbstractRestClient implements RestClient {

    @Override
    public String deleteHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int expectedReturnCode) throws RestClientException {
        return this.deleteHttp(uri, headers, parameters, new int[]{expectedReturnCode});
    }

    @Override
    public String getHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int expectedReturnCode) throws RestClientException {
        return this.getHttp(uri, headers, parameters, new int[]{expectedReturnCode});
    }

    @Override
    public String patchHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int expectedReturnCode, String body) throws RestClientException {
        return this.patchHttp(uri, headers, parameters, new int[]{expectedReturnCode}, body);
    }

    @Override
    public String postHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int expectedReturnCode, String body) throws RestClientException {
        return this.postHttp(uri, headers, parameters, new int[]{expectedReturnCode}, body);
    }

    @Override
    public String postHttpFormData(String uri, Map<String, String> headers, Map<String, String> parameters, int expectedReturnCode, Map<String, String> formData) throws RestClientException {
        return this.postHttpFormData(uri, headers, parameters, new int[]{expectedReturnCode}, formData);
    }

    @Override
    public String putHttp(String uri, Map<String, String> headers, Map<String, String> parameters, int expectedReturnCode, String body) throws RestClientException {
        return this.putHttp(uri, headers, parameters, new int[]{expectedReturnCode}, body);
    }
    
    
}
