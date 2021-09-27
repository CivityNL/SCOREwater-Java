/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.processor;

/**
 *
 * @author basvanmeulebrouk
 */
public class FiwareProcessorException extends Exception {

    public FiwareProcessorException(String string) {
        super(string);
    }

    public FiwareProcessorException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
}
