/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.gbg.bathingwater.routebuilder;

import nl.civity.scorewater.fiware.routebuilder.MqttFiwareRouteBuilder;
import nl.civity.scorewater.gbg.bathingwater.processor.BathingWaterGbgFiwareProcessor;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 *
 * @author basvanmeulebrouk
 */
@Component
public class BathingWaterGbgMqttRouteBuilder extends MqttFiwareRouteBuilder {
    
    @Override
    protected Processor createProcessor() {
        return new BathingWaterGbgFiwareProcessor(this.getContextBrokerUrl(), this.getContextBrokerService(), null);
    }
}
