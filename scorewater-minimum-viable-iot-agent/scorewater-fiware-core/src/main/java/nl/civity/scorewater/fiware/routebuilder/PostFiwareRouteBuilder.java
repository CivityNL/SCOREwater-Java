/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.routebuilder;

import java.util.logging.Level;
import java.util.logging.Logger;
import nl.civity.scorewater.fiware.processor.FiwareProcessorException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author basvanmeulebrouk
 */
public class PostFiwareRouteBuilder extends FiwareRouteBuilder {
    
    @Value("${post.path}")
    private String path;
    
    private static final Logger LOGGER = Logger.getLogger(PostFiwareRouteBuilder.class.getName());

    @Override
    public void configure() throws Exception {
        LOGGER.log(Level.INFO, "Creating post entities call back for [{0}]", new Object[] {path});
        
        rest("/api")
                .post("/" + path)
                .to("direct:post:" + path);
        
        String routeId = "post_route_" + Integer.toString(String.format("path_%s", path).hashCode());
        
        from("direct:post:" + path)
                .log(LoggingLevel.INFO, "post entities to " + path + ", incoming message, queue " + routeId)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .to(getQueueToEndPoint(String.format("incoming_post_entities_%s", routeId)));
        
        from(getQueueFromEndPoint(String.format("incoming_post_entities_%s", routeId)))
                .log(LoggingLevel.INFO, "post entities to " + path + ", process, queue " + routeId)
                .process(this.createProcessor());
//                .onException(FiwareProcessorException.class)
//                .log(LoggingLevel.INFO, "post entities to " + path + ", error")
//                .to(errorQueueName);
    }

    @Override
    protected Processor createProcessor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
