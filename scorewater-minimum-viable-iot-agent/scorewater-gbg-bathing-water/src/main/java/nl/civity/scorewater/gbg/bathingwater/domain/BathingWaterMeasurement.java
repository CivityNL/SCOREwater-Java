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
package nl.civity.scorewater.gbg.bathingwater.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import nl.civity.scorewater.fiware.datamodel.ObservedIdentifier;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basvanmeulebrouk
 */
public class BathingWaterMeasurement implements Serializable {

    private ObservedIdentifier primaryKey;
    private String sensorType;
    private String spreadingFactor;
    private String rssi;
    private String snr;
    private String gatewayIdentifier;
    private String fPort;
    private Double lon;
    private Double lat;
    private Double temperature;

    public BathingWaterMeasurement() {
    }

    public BathingWaterMeasurement(String entityId, ZonedDateTime recordingTimestamp, String sensorType, String spreadingFactor, String rssi, String snr, String gatewayIdentifier, String fPort, Double lon, Double lat, Double temperature) {
        this.primaryKey = new ObservedIdentifier(entityId, recordingTimestamp);
        this.sensorType = sensorType;
        this.spreadingFactor = spreadingFactor;
        this.rssi = rssi;
        this.snr = snr;
        this.gatewayIdentifier = gatewayIdentifier;
        this.fPort = fPort;
        this.lon = lon;
        this.lat = lat;
        this.temperature = temperature;
    }

    public ObservedIdentifier getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ObservedIdentifier primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSpreadingFactor() {
        return spreadingFactor;
    }

    public void setSpreadingFactor(String spreadingFactor) {
        this.spreadingFactor = spreadingFactor;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getGatewayIdentifier() {
        return gatewayIdentifier;
    }

    public void setGatewayIdentifier(String gatewayIdentifier) {
        this.gatewayIdentifier = gatewayIdentifier;
    }

    public String getfPort() {
        return fPort;
    }

    public void setfPort(String fPort) {
        this.fPort = fPort;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    
    public JSONObject toNgsi() {
        JSONObject result = new JSONObject();
        
        result.put("id", this.getPrimaryKey().getEntityId());
        result.put("type", "WaterQualityObserved");
        result.put("dataProvider", createJSONObject("City of Gothenburg"));
        result.put("dataObserved", createJSONObject(this.getPrimaryKey().getRecordingTimestamp()));
        result.put("temperature", createJSONObject(this.getTemperature()));
        result.put("location", createLocationJSONObject(this.getLon(), this.getLat()));
        
        return result;
    }
    
    private JSONObject createJSONObject(String value) {
        JSONObject result = new JSONObject();
        result.put("type", "Text");
        result.put("value", value);
        result.put("metadata", new JSONObject());
        return result;
    }
    
    private JSONObject createJSONObject(ZonedDateTime value) {
        JSONObject result = new JSONObject();
        result.put("type", "DateTime");
        result.put("value", value);
        result.put("metadata", new JSONObject());
        return result;
    }
    
    private JSONObject createJSONObject(Double value) {
        JSONObject result = new JSONObject();
        result.put("type", "Number");
        result.put("value", value);
        result.put("metadata", new JSONObject());
        return result;
    }
    
    private JSONObject createLocationJSONObject(Double lon, Double lat) {
        JSONObject result = new JSONObject();
        
        result.put("type", "geo:json");
        
        JSONObject valueJSONObject = new JSONObject();
        
        valueJSONObject.put("type", "Point");
        
        JSONArray coordinatesJSONArray = new JSONArray();
        coordinatesJSONArray.put(lon);
        coordinatesJSONArray.put(lat);
        valueJSONObject.put("coordinates", coordinatesJSONArray);
        
        result.put("value", valueJSONObject);
        
        result.put("metadata", new JSONObject());
        
        return result;
    }
}
