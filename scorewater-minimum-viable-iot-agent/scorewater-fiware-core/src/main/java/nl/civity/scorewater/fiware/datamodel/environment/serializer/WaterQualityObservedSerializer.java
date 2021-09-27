/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.fiware.datamodel.environment.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import nl.civity.scorewater.fiware.datamodel.environment.WaterQualityObserved;
import nl.civity.scorewater.fiware.serializer.MandatorySerializer;
import nl.civity.scorewater.fiware.serializer.OptionalSerializer;
import nl.civity.scorewater.fiware.serializer.ngsi.NGSIMandatorySerializer;
import nl.civity.scorewater.fiware.serializer.ngsi.NGSIOptionalSerializer;

/**
 *
 * @author basvanmeulebrouk
 */
public abstract class WaterQualityObservedSerializer extends StdSerializer<WaterQualityObserved> {
    
    private final MandatorySerializer mandatorySerializer;
    
    private final OptionalSerializer optionalSerializer;

    public WaterQualityObservedSerializer(Class<WaterQualityObserved> t) {
        super(t);
        this.mandatorySerializer = this.createMandatorySerializer();
        this.optionalSerializer = this.createOptionalSerializer();
    }

    @Override
    public void serialize(WaterQualityObserved t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeStartObject();
        
        this.mandatorySerializer.mandatoryString(jg, "id", t.getEntityId());
        this.mandatorySerializer.mandatoryString(jg, "type", t.getType());
        
        this.optionalSerializer.optionalString(jg, "dataProvider", t.getDataProvider());
        this.optionalSerializer.optionalTimestamp(jg,"dateModified", t.getDateModified());
        this.optionalSerializer.optionalTimestamp(jg,"dateCreated", t.getDateCreated());
        this.optionalSerializer.optionalLocation(jg, "location", t.getLocation());
        this.optionalSerializer.optionalString(jg, "address", t.getAddress());
        this.optionalSerializer.optionalString(jg, "refPointOfInterest", t.getRefPointOfInterest());
        this.optionalSerializer.optionalTimestamp(jg,"dateObserved", t.getDateObserved());
        this.optionalSerializer.optionalString(jg, "source", t.getSource());
        this.optionalSerializer.optionalDouble(jg, "temperature", t.getTemperature(), t.getTemperatureTimestamp());
        this.optionalSerializer.optionalDouble(jg, "conductivity", t.getConductivity(), t.getConductivityTimestamp());
        this.optionalSerializer.optionalDouble(jg, "conductance", t.getConductance(), t.getConductanceTimestamp());
        this.optionalSerializer.optionalDouble(jg, "tss", t.getTss(), t.getTssTimestamp());
        this.optionalSerializer.optionalDouble(jg, "tds", t.getTds(), t.getTdsTimestamp());
        this.optionalSerializer.optionalDouble(jg, "turbidity", t.getTurbidity(), t.getTurbidityZonedDateTime());
        this.optionalSerializer.optionalDouble(jg, "salinity", t.getSalinity(), t.getSalinityZonedDateTime());
        this.optionalSerializer.optionalDouble(jg, "pH", t.getPh(), t.getPhZonedDateTime());
        this.optionalSerializer.optionalDouble(jg, "orp", t.getOrp(), t.getOrpZonedDateTime());
        this.optionalSerializer.optionalStringArray(jg, "measurand", t.getMeasurand());
        this.optionalSerializer.optionalDouble(jg, "O2", t.getO2(), t.getO2Timestamp());
        this.optionalSerializer.optionalDouble(jg, "Chla", t.getChla(), t.getChlaTimestamp());
        this.optionalSerializer.optionalDouble(jg, "PE", t.getPE(), t.getPETimestamp());
        this.optionalSerializer.optionalDouble(jg, "PC", t.getPC(), t.getPCTimestamp());
        this.optionalSerializer.optionalDouble(jg, "NH4", t.getNH4(), t.getNH4Timestamp());
        this.optionalSerializer.optionalDouble(jg, "NH3", t.getNH3(), t.getNH3Timestamp());
        this.optionalSerializer.optionalDouble(jg, "Cl-", t.getCl(), t.getClTimestamp());
        this.optionalSerializer.optionalDouble(jg, "NO3", t.getNO3(), t.getNO3Timestamp());
        
        jg.writeEndObject();
    }
    
    protected MandatorySerializer createMandatorySerializer() {
        return new NGSIMandatorySerializer();
    }
    
    protected OptionalSerializer createOptionalSerializer() {
        return new NGSIOptionalSerializer();
    }
}