/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.routebuilder;

import java.util.logging.Logger;
import org.apache.camel.LoggingLevel;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author basvanmeulebrouk
 */
public abstract class MqttFiwareRouteBuilder extends FiwareRouteBuilder {

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

    private static final Logger LOGGER = Logger.getLogger(MqttFiwareRouteBuilder.class.getName());

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
                .onException(FiwareRouteBuilderException.class)
                .log(LoggingLevel.INFO, "MQTT entities, error")
                .to(getQueueToEndPoint("incoming_mqtt_entities_error"));
    }
}
