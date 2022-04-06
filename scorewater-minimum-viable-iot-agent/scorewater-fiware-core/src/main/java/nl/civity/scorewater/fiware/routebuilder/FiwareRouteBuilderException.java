/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.routebuilder;

/**
 *
 * @author basvanmeulebrouk
 */
public class MqttRouteBuilderException extends Exception {

    public MqttRouteBuilderException(String string) {
        super(string);
    }

    public MqttRouteBuilderException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
}
