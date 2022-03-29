/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.fiware.scorewater.turbinator.routebuilder;

import nl.civity.fiware.scorewater.turbinator.domain.da.TurbinatorMeasurementRepository;
import nl.civity.fiware.scorewater.turbinator.processor.TurbinatorProcessor;
import nl.civity.scorewater.fiware.routebuilder.MqttRouteBuilder;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author basvanmeulebrouk
 */
@Component
public class TurbinatorMqttRouteBuilder  extends MqttRouteBuilder {
    
    @Autowired
    private TurbinatorMeasurementRepository turbinatorMeasurementRepository;
    
    @Override
    protected Processor createProcessor() {
        return new TurbinatorProcessor(this.getContextBrokerUrl(), this.getContextBrokerService(), null, this.turbinatorMeasurementRepository);
    }
}
