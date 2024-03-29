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
package nl.civity.orion.contextbroker.domain;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author niekarends
 */
public class Attribute {

    private String name;
    private String type;
    private Object value;
    private List<Metadata> metadata = new ArrayList<>();

    public Attribute(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(JSONObject value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setValue(Geometry value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Metadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<Metadata> metadata) {
        this.metadata = metadata;
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        
        result.put("type", this.type);
        result.put("value", this.value);

//        JSONObject metadataJSONObject = new JSONObject();
//        for (Metadata m : this.metadata) {
//            metadataJSONObject.put(m.getName(), m.toJSON());
//        }
//        result.put("metadata", metadataJSONObject);
        
        return result;
    }

    public void addMetadata(Metadata metadata) {
        this.metadata.add(metadata);
    }

    @Override
    public String toString() {
        return String.format("Attribute %s = %s", this.name, this.value);
    }
}
