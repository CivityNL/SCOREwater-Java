/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.gbg.bathingwater.routebuilder;

import nl.civity.scorewater.fiware.routebuilder.MqttRouteBuilder;
import nl.civity.scorewater.gbg.bathingwater.processor.BathingWaterGbgFiwareProcessor;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author basvanmeulebrouk
 */
@Component
public class BathingWaterGbgMqttRouteBuilder extends MqttRouteBuilder {
    
    @Value("${fiware.publish.contextbroker.url}")
    private String contextBrokerUrl;
    
    @Value("${fiware.publish.contextbroker.service}")
    private String contextBrokerService;

    @Override
    protected Processor createProcessor() {
        return new BathingWaterGbgFiwareProcessor(contextBrokerUrl, contextBrokerService, null);
    }
}
