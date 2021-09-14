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

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author basvanmeulebrouk
 */
public class ApacheHttpRestClientTest extends RestClientTest {

    private ApacheHttpRestClient instance;
    
    public ApacheHttpRestClientTest() {
    }

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.instance = new ApacheHttpRestClient();
    }

    /**
     * Test of deleteHttp method, of class ApacheHttpRestClient.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteHttp() throws Exception {
        System.out.println("deleteHttp");

        enqueueMockRequest(HELLO_WORLD, 204);
        String result = instance.deleteHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200, 202, 204});
        assertEquals(EMPTY_STRING, result);

        Exception exception = assertThrows(RestClientException.class, () -> {
            enqueueMockRequest(ERROR, 500);
            instance.deleteHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200, 202, 204});
        });
        assertTrue(exception.getMessage().contains(String.format("The server returned: [%s]", ERROR)));
    }

    /**
     * Test of getHttp method, of class ApacheHttpRestClient.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetHttp() throws Exception {
        System.out.println("getHttp");

        enqueueMockRequest(HELLO_WORLD, 200);
        String result = instance.getHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200});
        assertEquals(HELLO_WORLD, result);
        
        Exception exception = assertThrows(RestClientException.class, () -> {
            enqueueMockRequest(ERROR, 500);
            instance.getHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200});
        });
        assertTrue(exception.getMessage().contains(String.format("The server returned: [%s]", ERROR)));
    }

    /**
     * Test of patchHttp method, of class ApacheHttpRestClient.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPatchHttp() throws Exception {
        System.out.println("patchHttp");

        enqueueMockRequest(HELLO_WORLD, 200);
        String result = instance.patchHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200, 202, 204}, HELLO_WORLD);
        assertEquals(HELLO_WORLD, result);

        enqueueMockRequest(HELLO_WORLD, 204);
        result = instance.patchHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200, 202, 204}, HELLO_WORLD);
        assertEquals(EMPTY_STRING, result);

        Exception exception = assertThrows(RestClientException.class, () -> {
            enqueueMockRequest(ERROR, 500);
            instance.patchHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200, 202, 204}, HELLO_WORLD);
        });
        assertTrue(exception.getMessage().contains(String.format("The server returned: [%s]", ERROR)));
    }

    /**
     * Test of postHttp method, of class ApacheHttpRestClient.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPostHttp() throws Exception {
        System.out.println("postHttp");

        enqueueMockRequest(HELLO_WORLD, 201);
        String result = instance.postHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{201}, HELLO_WORLD);
        assertEquals(HELLO_WORLD, result);

        Exception exception = assertThrows(RestClientException.class, () -> {
            enqueueMockRequest(ERROR, 500);
            instance.postHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{201}, HELLO_WORLD);
        });
        assertTrue(exception.getMessage().contains(String.format("The server returned: [%s]", ERROR)));
    }

    /**
     * Test of putHttp method, of class ApacheHttpRestClient.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPutHttp() throws Exception {
        System.out.println("putHttp");

        enqueueMockRequest(HELLO_WORLD, 200);
        String result = instance.putHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200, 204}, HELLO_WORLD);
        assertEquals(HELLO_WORLD, result);  // This should return {"hello": "world"}, but for some reason it does not. 

        enqueueMockRequest(HELLO_WORLD, 204);
        result = instance.putHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200, 204}, HELLO_WORLD);
        assertEquals(EMPTY_STRING, result);  // This should return {"hello": "world"}, but for some reason it does not. 

        Exception exception = assertThrows(RestClientException.class, () -> {
            enqueueMockRequest(ERROR, 500);
            instance.putHttp(this.getMockWebServer().url("/").toString(), new HashMap<>(), new HashMap<>(), new int[]{200, 204}, HELLO_WORLD);
        });
        assertTrue(exception.getMessage().contains(String.format("The server returned: [%s]", ERROR)));
    }
}
