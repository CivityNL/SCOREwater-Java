/*
 * Copyright (c) 2022, Civity BV Zeist
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
package nl.civity.fiware.scorewater.turbinator.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import nl.civity.scorewater.fiware.datamodel.ObservedIdentifier;

/**
 *
 * @author basvanmeulebrouk
 */
@Entity
public class TurbinatorMeasurement implements Serializable, Comparable<TurbinatorMeasurement> {

    @Id
    private ObservedIdentifier primaryKey;
    private Integer turbidity;
    private Double waterLevel;

    public TurbinatorMeasurement() {
    }

    public TurbinatorMeasurement(String entityId, ZonedDateTime recordingTimestamp, Integer turbidity, Double waterLevel) {
        this.primaryKey = new ObservedIdentifier(entityId, recordingTimestamp);
        this.turbidity = turbidity;
        this.waterLevel = waterLevel;
    }

    public ObservedIdentifier getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ObservedIdentifier primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(Integer turbidity) {
        this.turbidity = turbidity;
    }

    public Double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(Double waterLevel) {
        this.waterLevel = waterLevel;
    }

    @Override
    public int compareTo(TurbinatorMeasurement that) {
        return this.getPrimaryKey().compareTo(that.getPrimaryKey());
    }
}
