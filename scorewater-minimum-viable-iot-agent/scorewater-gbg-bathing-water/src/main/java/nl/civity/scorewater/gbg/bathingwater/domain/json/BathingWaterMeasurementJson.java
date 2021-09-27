/*
 * Copyright (c) 2021, Civity BV Zeist
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holder nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software without 
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nl.civity.scorewater.gbg.bathingwater.domain.json;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import nl.civity.scorewater.gbg.bathingwater.domain.BathingWaterMeasurement;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basvanmeulebrouk
 */
public class BathingWaterMeasurementJson {
    
    public static Set<BathingWaterMeasurement> fromJsonString(String data) {
        JSONArray jsonArray = new JSONArray(data);
        return fromJsonArray(jsonArray);
    }
    
    public static Set<BathingWaterMeasurement> fromJsonArray(JSONArray jsonArray) {
        Set<BathingWaterMeasurement> result = new HashSet<>();
        
        for (int i = 0; i < jsonArray.length(); i ++) {
            result.add(fromJsonObject(jsonArray.getJSONObject(i)));
        }
        
        return result;
    }
    
    protected static BathingWaterMeasurement fromJsonObject(JSONObject jsonObject) {
        String entityId = jsonObject.getString("devEui");
        String sensorType = jsonObject.getString("sensorType");
        ZonedDateTime recordingTimestamp = ZonedDateTime.parse(jsonObject.getString("timestamp"));
        String spreadingFactor = jsonObject.getString("spreadingFactor");
        String rssi = jsonObject.getString("rssi");
        String snr = jsonObject.getString("snr");
        String gatewayIdentifier = jsonObject.getString("gatewayIdentifier");
        String fPort = jsonObject.getString("fPort");
        Double lon = jsonObject.getDouble("longitude"); 
        Double lat = jsonObject.getDouble("latitude");
        
        // If it wasn't for the payload bytes, this deserializer could have been a standard one.
        String payload = jsonObject.getString("payload");
        double temperature = payloadToTemperature(payload);

        BathingWaterMeasurement result = new BathingWaterMeasurement(entityId, recordingTimestamp, sensorType, spreadingFactor, rssi, snr, gatewayIdentifier, fPort, lon, lat, temperature);
        
        return result;
    }
    
    protected static double payloadToTemperature(String payload) {
        String hexaDecimal = payload.substring(payload.length() - 4, payload.length());
        int decimal = Integer.parseInt(hexaDecimal, 16);
        return decimal / 16.0;
    }
}
