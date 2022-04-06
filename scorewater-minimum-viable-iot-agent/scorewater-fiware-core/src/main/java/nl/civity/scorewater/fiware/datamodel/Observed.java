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
package nl.civity.scorewater.fiware.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 *
 * @author basvanmeulebrouk
 */
@MappedSuperclass
public class Observed  implements Serializable, Comparable<Observed> {

    @Id
    ObservedIdentifier primaryKey;
    
    // To be able to query for DATE(recordingTime)
    protected LocalDate recordingDate;

    // Entity type. It must be equal to the type of datamodel, for example WaterQualityObserved.
    protected String type;

    // Specifies the URL to information about the provider of this information
    // - Attribute type: Property. URL
    // - Optional
    protected String dataProvider;

    // Last update timestamp of this entity.
    // - Attribute type: Property. DateTime
    // - Read-Only. Automatically generated.
    protected ZonedDateTime dateModified;

    // Entity's creation timestamp.
    // - Attribute type: Property. DateTime
    // - Read-Only. Automatically generated.
    protected ZonedDateTime dateCreated;

    // Location where measurements have been taken, represented by GeoJSON Point.
    // - Attribute type: GeoProperty. geo:json.
    // - Normative References:https://tools.ietf.org/html/draft-ietf-geojson-03
    // - Mandatory if address is not present.
    protected Location location; //should be geo:json

    // Civic address where the measurement is taken.
    // - Attribute type: Property. Address
    // - Normative References: https://schema.org/address
    // - Mandatory if location is not present.
    protected String address;

    // A reference to a point of interest associated to this observation.
    // - Attribute type: Property. Reference to an entity of type PointOfInterest
    // - Optional
    protected String refPointOfInterest; // PointOfInterest

    // The date and time of this observation in ISO8601 UTC format. It can be represented by an specific time instant or by an ISO8601 interval.
    // - Attribute type: Property. DateTime or an ISO8601 interval represented as Text.
    // - Mandatory
    // protected ZonedDateTime dateObserved;
    // A sequence of characters giving the source of the entity data.
    // - Attribute type: Property. Text or URL
    // - Optional
    protected String source;

    @Transient
    protected DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_INSTANT;

    public Observed() {
        this.dateModified = ZonedDateTime.now();
        this.dateCreated = ZonedDateTime.now();
    }

    public Observed(String entityId, ZonedDateTime dateObserved, String type, String dataProvider, Location location, String address) {
        this.primaryKey = new ObservedIdentifier(entityId, dateObserved);
        this.type = type;
        this.dataProvider = dataProvider;
        this.dateModified = ZonedDateTime.now();
        this.dateCreated = ZonedDateTime.now();
        this.location = location;
        this.address = address;
    }

    public ObservedIdentifier getPrimaryKey() {
        return primaryKey;
    }

    protected void setPrimaryKey(ObservedIdentifier primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    public String getEntityId() {
        return this.primaryKey.getEntityId();
    }
    
    public void setEntityId(String entityId) {
        this.primaryKey.setEntityId(entityId);
    }

    public ZonedDateTime getRecordingTimestamp() {
        return this.primaryKey.getRecordingTimestamp();
    }

    public void setRecordingTimestamp(ZonedDateTime recordingTimestamp) {
        this.primaryKey.setRecordingTimestamp(recordingTimestamp);
    }

    public LocalDate getRecordingDate() {
        return recordingDate;
    }

    public void setRecordingDate(LocalDate recordingDate) {
        this.recordingDate = recordingDate;
    }
    
    public ZonedDateTime getDateObserved() {
        return this.primaryKey.getRecordingTimestamp();
    }
    
    public void setDateObserved(ZonedDateTime dateObserved) {
        this.primaryKey.setRecordingTimestamp(dateObserved);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        setDateModified();
        this.type = type;
    }

    public String getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(String dataProvider) {
        setDateModified();
        this.dataProvider = dataProvider;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        setDateModified();
        this.location = location;
    }

    public void setLocation(double lat, double lon, String properties) {
        location = new Location(lat, lon, properties);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        setDateModified();
        this.address = address;
    }

    public String getRefPointOfInterest() {
        return refPointOfInterest;
    }

    public void setRefPointOfInterest(String refPointOfInterest) {
        setDateModified();
        this.refPointOfInterest = refPointOfInterest;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        setDateModified();
        this.source = source;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public int compareTo(Observed o) {
        return this.getPrimaryKey().compareTo(o.getPrimaryKey());
    }

    public void setDateModified() {
        this.dateModified = ZonedDateTime.now();
    }
}
