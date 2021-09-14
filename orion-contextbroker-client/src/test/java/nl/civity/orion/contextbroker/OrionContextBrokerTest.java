/*
 * Copyright (c) 2021, basvanmeulebrouk
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
package nl.civity.orion.contextbroker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import nl.civity.orion.contextbroker.domain.Attribute;
import nl.civity.orion.contextbroker.domain.Entity;
import nl.civity.orion.contextbroker.domain.EntityList;
import nl.civity.orion.contextbroker.domain.Subscription;
import nl.civity.orion.contextbroker.domain.SubscriptionList;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author basvanmeulebrouk
 */
public class OrionContextBrokerTest {
    
    private MockWebServer mockWebServer;

    public OrionContextBrokerTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        this.mockWebServer = new MockWebServer();
    }

    /**
     * Test of getEntities method, of class OrionContextBroker.
     * @throws java.lang.Exception
     */
    @org.junit.jupiter.api.Test
    public void testGetEntities() throws Exception {
        System.out.println("getEntities");
        
        String data = this.getData("tests/get_entities_response.json");
        
        this.enqueueMockRequest(data, 200, null, null);
        
        OrionContextBroker contextBroker = new OrionContextBroker(this.getMockWebServer().url("/").toString(), null, null);
        
        EntityList entities = contextBroker.getEntities();
        
        assertEquals(4, entities.size());
        
        for (Entity entity : entities) {
            // assertEquals("70b3d580a010f2ca", entity.getId());  have to sort the list to make sure entities are always in the same order
            assertEquals("WaterQualityObserved", entity.getType());
            Attribute temperatureAttribute = entity.findAttribute("temperature");
            assertNotNull(temperatureAttribute);
            // assertEquals(17.9375, temperatureAttribute.getValue());
            break;
        }
    }

    /**
     * Test of updateEntity method, of class OrionContextBroker.
     * @throws java.lang.Exception
     */
    @org.junit.jupiter.api.Test
    public void testUpdateEntity_Entity() throws Exception {
    }

    /**
     * Test of updateEntity method, of class OrionContextBroker.
     * @throws java.lang.Exception
     */
    @org.junit.jupiter.api.Test
    public void testUpdateEntity_String() throws Exception {
    }

    /**
     * Test of subscribe method, of class OrionContextBroker.
     * @throws java.lang.Exception
     */
    @org.junit.jupiter.api.Test
    public void testSubscribe() throws Exception {
        System.out.println("subscribe");
        
        OrionContextBroker contextBroker = new OrionContextBroker(this.getMockWebServer().url("/").toString(), null, null);

        // List subscriptions
        String data = this.getData("tests/get_subscriptions_response.json");
        this.enqueueMockRequest(data, 200, null, null);
        
        // Create subscription
        this.enqueueMockRequest("", 201, "location", "/v2/subscriptions/fubezfihov1");

        Subscription subscription = new Subscription(null, "Test subscription", "http://www.example.com", "status");
        
        String subscriptionId = contextBroker.subscribe(subscription);
        
        assertEquals("fubezfihov1", subscriptionId);
    }

    /**
     * Test of listSubscriptions method, of class OrionContextBroker.
     * @throws java.lang.Exception
     */
    @org.junit.jupiter.api.Test
    public void testListSubscriptions() throws Exception {
        System.out.println("listSubscriptions");
        
        String data = this.getData("tests/get_subscriptions_response.json");
        
        this.enqueueMockRequest(data, 200, null, null);
        
        OrionContextBroker contextBroker = new OrionContextBroker(this.getMockWebServer().url("/").toString(), null, null);
        
        SubscriptionList subscriptions = contextBroker.getSubscriptions();
        
        assertEquals(1, subscriptions.size());
        
        for (Subscription subscription : subscriptions) {
            assertEquals("613f3af5d5213490de5302a4", subscription.getSubscriptionId());
            break;
        }
    }

    /**
     * Test of unsubscribe method, of class OrionContextBroker.
     * @throws java.lang.Exception
     */
    @org.junit.jupiter.api.Test
    public void testUnsubscribe() throws Exception {
    }

    protected String getData(String fileName) throws URISyntaxException, IOException {
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        String result = new String(bytes);
        return result;
    }

    protected void enqueueMockRequest(String data, int responseCode, String headerName, String headerValue) {
        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(data)
                .setResponseCode(responseCode);
        
        if (headerName != null && headerValue != null) {
            mockResponse.setHeader(headerName, headerValue);
        }
        
        mockWebServer.enqueue(mockResponse);
    }


    public MockWebServer getMockWebServer() {
        return mockWebServer;
    }
}
