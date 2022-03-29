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
package nl.civity.fiware.scorewater.turbinator.processor;

import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.civity.fiware.scorewater.turbinator.domain.TurbinatorMeasurement;
import nl.civity.fiware.scorewater.turbinator.domain.da.TurbinatorMeasurementRepository;
import nl.civity.fiware.scorewater.turbinator.domain.json.TurbinatorMeasurementJson;
import nl.civity.scorewater.fiware.processor.FiwareProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

/**
 *
 * @author basvanmeulebrouk
 */
public class TurbinatorProcessor extends FiwareProcessor {
    
    private final TurbinatorMeasurementRepository turbinatorMeasurementRepository;

    private static final Logger LOGGER = Logger.getLogger(TurbinatorProcessor.class.getName());

    public TurbinatorProcessor(String contextBrokerUrl, String fiwareService, String fiwarePath, TurbinatorMeasurementRepository turbinatorMeasurementRepository) {
        super(contextBrokerUrl, fiwareService, fiwarePath);
        this.turbinatorMeasurementRepository = turbinatorMeasurementRepository;
    }

    @Override
    protected SimpleModule getSerializerModule() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();

        String data = in.getBody(String.class);
        
        LOGGER.log(Level.INFO, "Payload from Turbinator: [{0}]", new Object[]{data});
        
        Set<TurbinatorMeasurement> measurements = TurbinatorMeasurementJson.fromJsonString(data);        
        
        
    }
}
