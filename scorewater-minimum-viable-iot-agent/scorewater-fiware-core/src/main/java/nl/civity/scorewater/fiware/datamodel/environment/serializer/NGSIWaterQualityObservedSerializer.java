package nl.civity.scorewater.fiware.datamodel.environment.serializer;

import nl.civity.scorewater.fiware.datamodel.environment.WaterQualityObserved;
import nl.civity.scorewater.fiware.serializer.MandatorySerializer;
import nl.civity.scorewater.fiware.serializer.OptionalSerializer;
import nl.civity.scorewater.fiware.serializer.ngsi.NGSIMandatorySerializer;
import nl.civity.scorewater.fiware.serializer.ngsi.NGSIOptionalSerializer;

public class NGSIWaterQualityObservedSerializer extends WaterQualityObservedSerializer {
    
    public NGSIWaterQualityObservedSerializer(Class<WaterQualityObserved> t) {
        super(t);
    }

    @Override
    protected MandatorySerializer createMandatorySerializer() {
        return new NGSIMandatorySerializer();
    }

    @Override
    protected OptionalSerializer createOptionalSerializer() {
        return new NGSIOptionalSerializer();
    }
}
