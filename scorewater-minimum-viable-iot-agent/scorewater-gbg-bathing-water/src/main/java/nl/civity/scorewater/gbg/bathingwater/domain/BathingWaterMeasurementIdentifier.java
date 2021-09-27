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
import java.util.Objects;

/**
 *
 * @author basvanmeulebrouk
 */
public class BathingWaterMeasurementIdentifier implements Serializable, Comparable {
    
    private String entityId;
    
    private ZonedDateTime recordingTimestamp;

    public BathingWaterMeasurementIdentifier() {
    }

    public BathingWaterMeasurementIdentifier(String entityId, ZonedDateTime recordingTimestamp) {
        this.entityId = entityId;
        this.recordingTimestamp = recordingTimestamp;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public ZonedDateTime getRecordingTimestamp() {
        return recordingTimestamp;
    }

    public void setRecordingTimestamp(ZonedDateTime recordingTimestamp) {
        this.recordingTimestamp = recordingTimestamp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.entityId);
        hash = 97 * hash + Objects.hashCode(this.recordingTimestamp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BathingWaterMeasurementIdentifier other = (BathingWaterMeasurementIdentifier) obj;
        if (!Objects.equals(this.entityId, other.getEntityId())) {
            return false;
        }
        if (!Objects.equals(this.recordingTimestamp, other.getRecordingTimestamp())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        int result = 0;
        
        if (o instanceof BathingWaterMeasurementIdentifier) {
            BathingWaterMeasurementIdentifier other = (BathingWaterMeasurementIdentifier) o;
            result = this.getEntityId().compareTo(other.getEntityId());
            if (result == 0) {
                result = this.getRecordingTimestamp().compareTo(other.getRecordingTimestamp());
            }
        }
        
        return result;
    }

    @Override
    public String toString() {
        return String.format("Entity ID [%s], recording timestamp [%s]", this.getEntityId(), this.getRecordingTimestamp());
    }
}
