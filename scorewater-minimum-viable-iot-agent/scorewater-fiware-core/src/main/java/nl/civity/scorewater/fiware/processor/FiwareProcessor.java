/*
 * Copyright (c) 2022, Civity BV Zeist
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
package nl.civity.scorewater.fiware.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.logging.Logger;
import nl.civity.orion.contextbroker.OrionContextBroker;
import nl.civity.orion.contextbroker.OrionContextBrokerException;
import nl.civity.scorewater.fiware.datamodel.Observed;
import org.apache.camel.Processor;

/**
 *
 * @author basvanmeulebrouk
 */
public abstract class FiwareProcessor implements Processor {

    private final String contextBrokerUrl;

    private final String fiwareService;

    private final String fiwarePath;

    private static final Logger LOGGER = Logger.getLogger(FiwareProcessor.class.getName());

    public FiwareProcessor(String contextBrokerUrl, String fiwareService, String fiwarePath) {
        this.contextBrokerUrl = contextBrokerUrl;
        this.fiwareService = fiwareService;
        this.fiwarePath = fiwarePath;
    }

    protected void publishToContextBroker(Observed observed) throws FiwareProcessorException {
        try {
            OrionContextBroker contextBroker = new OrionContextBroker(this.contextBrokerUrl, this.fiwareService, this.fiwarePath);

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = this.getSerializerModule();
            if (module != null) {
                mapper.registerModule(module);
            }

            String data = mapper.writeValueAsString(observed).replace("'", "%27");
            
            contextBroker.updateEntity(data);
            
            LOGGER.info(String.format("Published data for entity [%s], timestamp [%s] to ContextBroker [%s].", observed.getEntityId(), observed.getRecordingTimestamp(), this.contextBrokerUrl));
        } catch (OrionContextBrokerException ex) {
            throw new FiwareProcessorException(String.format("Error publishing data for entity [%s], timestamp [%s] to ContextBroker [%s].", observed.getEntityId(), observed.getRecordingTimestamp(), this.contextBrokerUrl), ex);
        } catch (JsonProcessingException ex) {
            throw new FiwareProcessorException(String.format("Error converting data for entity [%s], timestamp [%s] to JSON.", observed.getEntityId(), observed.getRecordingTimestamp()), ex);
        }
    }
    
    protected abstract SimpleModule getSerializerModule();
}
