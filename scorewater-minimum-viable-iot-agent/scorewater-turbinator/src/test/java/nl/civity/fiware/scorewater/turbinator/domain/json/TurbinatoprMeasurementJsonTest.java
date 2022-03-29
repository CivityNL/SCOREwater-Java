/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.civity.fiware.scorewater.turbinator.domain.json;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import nl.civity.fiware.scorewater.turbinator.domain.TurbinatorMeasurement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author basvanmeulebrouk
 */
public class TurbinatoprMeasurementJsonTest {
    
    protected JSONObject getJsonObjectFromResource(String fileName) throws URISyntaxException, IOException, JSONException {
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        String json = new String(bytes);
        return new JSONObject(json);
    }


    @Test
    public void testSomeMethod() throws URISyntaxException, IOException, JSONException {
        JSONObject json = this.getJsonObjectFromResource("tests/payload_01.json");
        
        Set<TurbinatorMeasurement> measurements = TurbinatorMeasurementJson.fromJsonObject(json);
        
        for (TurbinatorMeasurement measurement : measurements) {
            assertEquals("urn:uuid:77a90d86-2678-11ec-9621-0242ac130002", measurement.getPrimaryKey().getEntityId());
            assertEquals(26, measurement.getTurbidity());
            assertEquals(98.0, measurement.getWaterLevel(), 1);
            break;
        }
    }
}
