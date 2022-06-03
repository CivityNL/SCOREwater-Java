/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

/**
 *
 * @author basvanmeulebrouk
 */
@Component
public class DataStoreNamingStrategy implements PhysicalNamingStrategy {

    private final String applicationId;
    private final String schemaName;

    public DataStoreNamingStrategy(String applicationId, String schemaName) {
        this.applicationId = applicationId;
        this.schemaName = schemaName;
    }

    @Override
    public Identifier toPhysicalCatalogName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSchemaName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return Identifier.toIdentifier(this.schemaName);
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        // Append application ID to be able to distinguish between tables for 
        // different applications. 
        String tableName = convertToSnakeCase(identifier).getText();
        tableName += "_" + this.applicationId;
        return Identifier.toIdentifier(tableName);
    }

    private Identifier convertToSnakeCase(final Identifier identifier) {
        Identifier result = null;
        
        if (identifier != null) {
            final String regex = "([a-z])([A-Z])";
            final String replacement = "$1_$2";
            final String newName = identifier.getText()
                    .replaceAll(regex, replacement)
                    .toLowerCase();
            
            result = Identifier.toIdentifier(newName);
        }

        return result;
    }
}
