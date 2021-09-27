/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.scorewater.gbg.bathingwater.domain.json;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import nl.civity.scorewater.gbg.bathingwater.domain.BathingWaterMeasurement;
import org.json.JSONArray;
import org.json.JSONException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author basvanmeulebrouk
 */
public class BathingWaterMeasurementJsonTest {
    
    public BathingWaterMeasurementJsonTest() {
    }

    protected JSONArray getJsonArrayFromResource(String fileName) throws URISyntaxException, IOException, JSONException {
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        String json = new String(bytes);
        return new JSONArray(json);
    }

    @Test
    public void testSomeMethod() throws URISyntaxException, IOException, JSONException {
        JSONArray json = this.getJsonArrayFromResource("tests/payload_01.json");
        
        Set<BathingWaterMeasurement> measurements = BathingWaterMeasurementJson.fromJsonArray(json);
        
        for (BathingWaterMeasurement measurement : measurements) {
            assertEquals("70b3d580a010f25f", measurement.getPrimaryKey().getEntityId());
            assertEquals("2021-05-12T07:27:10.226Z", measurement.getPrimaryKey().getRecordingTimestamp().toString());
            assertEquals(25.0, measurement.getTemperature(), 1);
        }
    }
    
    @Test
    public void testExtractTemperature() {
        Double temperature = BathingWaterMeasurementJson.payloadToTemperature("01FE5B8134018F");
        assertEquals(24.9575, temperature, 4);
    }
}
