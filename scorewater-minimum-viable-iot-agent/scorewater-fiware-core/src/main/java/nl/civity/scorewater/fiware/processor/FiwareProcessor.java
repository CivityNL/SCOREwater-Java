/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.logging.Logger;
import nl.civity.orion.contextbroker.OrionContextBroker;
import nl.civity.orion.contextbroker.OrionContextBrokerException;
import nl.civity.scorewater.fiware.datamodel.Observed;
import org.apache.camel.Processor;

/**
 *
 * @author basvanmeulebrouk
 */
public abstract class FiwareProcessor implements Processor {

    private final String contextBrokerUrl;

    private final String fiwareService;

    private final String fiwarePath;

    private static final Logger LOGGER = Logger.getLogger(FiwareProcessor.class.getName());

    public FiwareProcessor(String contextBrokerUrl, String fiwareService, String fiwarePath) {
        this.contextBrokerUrl = contextBrokerUrl;
        this.fiwareService = fiwareService;
        this.fiwarePath = fiwarePath;
    }

    protected void publishToContextBroker(Observed observed) throws FiwareProcessorException {
        try {
            OrionContextBroker contextBroker = new OrionContextBroker(this.contextBrokerUrl, this.fiwareService, this.fiwarePath);

            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = this.getSerializerModule();
            if (module != null) {
                mapper.registerModule(module);
            }

            String data = mapper.writeValueAsString(observed).replace("'", "%27");
            
            contextBroker.updateEntity(data);
            
            LOGGER.info(String.format("Published data for entity [%s], timestamp [%s] to ContextBroker [%s].", observed.getEntityId(), observed.getRecordingTimestamp(), this.contextBrokerUrl));
        } catch (OrionContextBrokerException ex) {
            throw new FiwareProcessorException(String.format("Error publishing data for entity [%s], timestamp [%s] to ContextBroker [%s].", observed.getEntityId(), observed.getRecordingTimestamp(), this.contextBrokerUrl), ex);
        } catch (JsonProcessingException ex) {
            throw new FiwareProcessorException(String.format("Error converting data for entity [%s], timestamp [%s] to JSON.", observed.getEntityId(), observed.getRecordingTimestamp()), ex);
        }
    }
    
    protected abstract SimpleModule getSerializerModule();
}
