/*
 * Copyright (c) 2021, Civity
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

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;

/**
 * Based on this tutorial: <a href="https://www.baeldung.com/spring-mocking-webclient">...</a>
 * @author basvanmeulebrouk
 */
abstract class RestClientTest {
    
    private MockWebServer mockWebServer;

    protected static final String HELLO_WORLD = "{\"hello\": \"world\"}";

    protected static final String ERROR = "{\"error\": \"something went wrong\"}";

    protected static final String EMPTY_STRING = "";

    public RestClientTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        this.mockWebServer = new MockWebServer();
    }

    protected void enqueueMockRequest(String data, int responseCode) {
        mockWebServer
                .enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(data)
                .setResponseCode(responseCode));
    }


    public MockWebServer getMockWebServer() {
        return mockWebServer;
    }
}
