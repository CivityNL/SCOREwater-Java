/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.routebuilder;

import java.util.logging.Logger;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author basvanmeulebrouk
 */
public abstract class MqttRouteBuilder extends RouteBuilder {

    @Value("${ckan.application.id}")
    private String applicationId;

    @Value("${fiware.publish.contextbroker.url}")
    private String contextBrokerUrl;
    
    @Value("${fiware.publish.contextbroker.service}")
    private String contextBrokerService;

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.broker.port}")
    private String port;

    @Value("${mqtt.broker.username}")
    private String userName;

    @Value("${mqtt.broker.password}")
    private String password;

    @Value("${mqtt.broker.topic}")
    private String topic;

    @Value("${spring.activemq.broker-url}")
    private String queueBrokerURL;

    @Value("${spring.activemq.concurrentconsumers}")
    private String concurrentConsumers;

    @Value("${spring.activemq.queue-name-prefix}")
    private String queueNamePrefix;

    private static final Logger LOGGER = Logger.getLogger(MqttRouteBuilder.class.getName());

    @Override
    public void configure() throws Exception {
        LOGGER.info(String.format("Creating MQTT route: broker URL %s, port %s, topic %s", brokerUrl, port, topic));

        String routeId = "mqtt_route_" + Integer.toString(String.format("broker_url_%s_port_%s_topic_%s_username_%s_password_%s", brokerUrl, port, topic, userName, password).hashCode());

        from(String.format("paho:%s?brokerUrl=%s:%s&password=%s&userName=%s", topic, brokerUrl, port, password, userName))
                .routeId(routeId)
                .log(LoggingLevel.INFO, "MQTT, incoming message")
                .to(getQueueToEndPoint(String.format("incoming_mqtt_entities_%s", routeId)));

        from(getQueueFromEndPoint(String.format("incoming_mqtt_entities_%s", routeId)))
                .log(LoggingLevel.INFO, "MQTT entities, process")
                .process(this.createProcessor())
                .onException(MqttRouteBuilderException.class)
                .log(LoggingLevel.INFO, "MQTT entities, error")
                .to(getQueueToEndPoint("incoming_mqtt_entities_error"));
    }

    protected abstract Processor createProcessor();

    protected String getQueueToEndPoint(String routeBuilderIdentifier) {
        return String.format(
                "activemq:queue:%s_%s_%s_queue?disableReplyTo=true&preserveMessageQos=true", //?brokerURL=%s&exchangePattern=InOnly&autoStartup=false&trustAllPackages=true", 
                this.queueNamePrefix,
                this.applicationId,
                routeBuilderIdentifier,
                queueBrokerURL
        );
    }

    protected String getQueueFromEndPoint(String routeBuilderIdentifier) {
        return String.format(
                "activemq:queue:%s_%s_%s_queue", // ?brokerURL=%s&concurrentConsumers=%s", 
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
