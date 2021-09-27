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
package nl.civity.scorewater.gbg.bathingwater.processor;

import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.Set;
import java.util.logging.Logger;
import nl.civity.scorewater.fiware.processor.FiwareProcessor;
import nl.civity.scorewater.fiware.datamodel.Location;
import nl.civity.scorewater.fiware.datamodel.environment.WaterQualityObserved;
import nl.civity.scorewater.fiware.datamodel.environment.serializer.NGSIWaterQualityObservedSerializer;
import nl.civity.scorewater.gbg.bathingwater.domain.BathingWaterMeasurement;
import nl.civity.scorewater.gbg.bathingwater.domain.json.BathingWaterMeasurementJson;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

/**
 *
 * @author basvanmeulebrouk
 */
public class BathingWaterGbgFiwareProcessor extends FiwareProcessor {

    private static final Logger LOGGER = Logger.getLogger(BathingWaterGbgFiwareProcessor.class.getName());

    public BathingWaterGbgFiwareProcessor(String contextBrokerUrl, String fiwareService, String fiwarePath) {
        super(contextBrokerUrl, fiwareService, fiwarePath);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();

        String data = in.getBody(String.class);
        
        LOGGER.info(String.format("Incoming! Data: %s", data));

        Set<BathingWaterMeasurement> bathingWaterMeasurements = BathingWaterMeasurementJson.fromJsonString(data);
        
        for (BathingWaterMeasurement bathingWaterMeasurement : bathingWaterMeasurements) {
            WaterQualityObserved waterQualityObserved = new WaterQualityObserved(
                    bathingWaterMeasurement.getPrimaryKey().getEntityId(), 
                    bathingWaterMeasurement.getPrimaryKey().getRecordingTimestamp(), 
                    "Göteborgs Stad", 
                    new Location(bathingWaterMeasurement.getLon(), bathingWaterMeasurement.getLat(), null), 
                    null
            );
            
            waterQualityObserved.setTemperature(bathingWaterMeasurement.getTemperature());
            
            this.publishToContextBroker(waterQualityObserved);            
        }
    }

    @Override
    protected SimpleModule getSerializerModule() {
        SimpleModule result = new SimpleModule();

        result.addSerializer(WaterQualityObserved.class, new NGSIWaterQualityObservedSerializer(WaterQualityObserved.class));
        
        return result;
    }
}
