/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.routebuilder;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author basvanmeulebrouk
 */
abstract class FiwareRouteBuilder extends RouteBuilder {
    
    @Value("${ckan.application.id}")
    private String applicationId;

    @Value("${fiware.publish.contextbroker.url}")
    private String contextBrokerUrl;
    
    @Value("${fiware.publish.contextbroker.service}")
    private String contextBrokerService;

    @Value("${spring.activemq.broker-url}")
    private String queueBrokerURL;

    @Value("${spring.activemq.concurrentconsumers}")
    private String concurrentConsumers;

    @Value("${spring.activemq.queue-name-prefix}")
    private String queueNamePrefix;

    protected abstract Processor createProcessor();

    protected String getQueueToEndPoint(String routeBuilderIdentifier) {
        return String.format(
                "activemq:queue:%s_%s_%s_queue?disableReplyTo=true&preserveMessageQos=true",
                this.queueNamePrefix,
                this.applicationId,
                routeBuilderIdentifier,
                queueBrokerURL
        );
    }

    protected String getQueueFromEndPoint(String routeBuilderIdentifier) {
        return String.format(
                "activemq:queue:%s_%s_%s_queue",
                this.queueNamePrefix,
                this.applicationId,
                routeBuilderIdentifier,
                queueBrokerURL,
                concurrentConsumers
        );
    }

    public String getContextBrokerUrl() {
        return contextBrokerUrl;
    }

    public String getContextBrokerService() {
        return contextBrokerService;
    }
}
