/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware;

import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author basvanmeulebrouk
 */
@Configuration
public class DataStoreNamingStrategyConfiguration {

    @Value("${ckan.application.id}")
    private String applicationId;
    
    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private String defaultSchema;

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new DataStoreNamingStrategy(applicationId, defaultSchema);
    }
}
