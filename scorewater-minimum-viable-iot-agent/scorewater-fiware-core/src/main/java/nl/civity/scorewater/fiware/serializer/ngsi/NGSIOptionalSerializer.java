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
package nl.civity.scorewater.fiware.serializer.ngsi;

import com.fasterxml.jackson.core.JsonGenerator;
import org.json.JSONObject;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import nl.civity.scorewater.fiware.datamodel.Location;
import nl.civity.scorewater.fiware.serializer.OptionalSerializer;

public class NGSIOptionalSerializer implements OptionalSerializer {
    
    // @@ToDo use UTC formatter
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

    private static final Logger LOGGER = Logger.getLogger(NGSIOptionalSerializer.class.getName());

    public NGSIOptionalSerializer(){

    }

    @Override
    public void optionalString(JsonGenerator jg, String name, String string) throws IOException {
        if (string != null) {
            jg.writeObjectFieldStart(name);
            jg.writeStringField("value", string);
            jg.writeEndObject();
        }
    }

    @Override
    public void optionalString(JsonGenerator jg, String name, String string, ZonedDateTime timestamp) throws IOException {
        if (string != null) {
            jg.writeObjectFieldStart(name);
            jg.writeStringField("value", string);
            if(timestamp != null) {
                jg.writeObjectFieldStart("metadata");
                optionalTimestamp(jg, "timestamp", timestamp);
                jg.writeEndObject();
            }
            jg.writeEndObject();
        }
    }

    @Override
    public void optionalTimestamp(JsonGenerator jg, String name, ZonedDateTime timestamp) throws IOException {
        if (timestamp != null) {
            jg.writeObjectFieldStart(name);
            jg.writeStringField("type", "DateTime");
            jg.writeStringField("value", timestamp.format(FORMATTER));
            jg.writeEndObject();
        }
    }

    @Override
    public void optionalDouble(JsonGenerator jg, String name, Double d) throws IOException {
        if (d != null) {
            jg.writeObjectFieldStart(name);
            jg.writeNumberField("value", d);
            jg.writeEndObject();
        }
    }

    @Override
    public void optionalDouble(JsonGenerator jg, String name, Double d, ZonedDateTime timestamp) throws IOException {
        if (d != null) {
            jg.writeObjectFieldStart(name);
            jg.writeNumberField("value", d);
            if(timestamp != null) {
                jg.writeObjectFieldStart("metadata");
                optionalTimestamp(jg, "timestamp", timestamp);
                jg.writeEndObject();
            }
            jg.writeEndObject();
        }
    }

    @Override
    public void optionalBoolean(JsonGenerator jg, String name, Boolean b) throws IOException {
        if (b != null) {
            jg.writeObjectFieldStart(name);
            jg.writeBooleanField("value", b);
            jg.writeEndObject();
        }
    }

    @Override
    public void optionalStringArray(JsonGenerator jg, String name, String[] array) throws IOException {
        if (array != null) {
            jg.writeArrayFieldStart(name);
            for (String string : array) {
                jg.writeString(string);
            }
            jg.writeEndArray();
        }
    }

    @Override
    public void optionalLocation(JsonGenerator jg, String name, Location location) throws IOException {
        if (location != null) {
            jg.writeObjectFieldStart(name);
            jg.writeStringField("type", "geo:json");
            jg.writeObjectFieldStart("value");
            jg.writeStringField("type", "Point");
            jg.writeArrayFieldStart("coordinates");
            jg.writeNumber(location.getLon());
            jg.writeNumber(location.getLat());
            jg.writeEndArray();
            jg.writeEndObject();

            if(location.getProperties() != null){
             JSONObject properties = new JSONObject(location.getProperties());
                jg.writeObjectFieldStart("properties");
             properties.toMap().forEach((key,result) -> {
                 try {
                     jg.writeStringField(key,result.toString());
                 } catch (IOException e) {
                     LOGGER.warning(e.getMessage());
                 }
             });
                jg.writeEndObject();
            }

            jg.writeEndObject();
        }
    }

    @Override
    public void optionalInteger(JsonGenerator jg, String name, Integer value) throws IOException {
        if (value != null) {
            jg.writeObjectFieldStart(name);
            jg.writeNumberField("value", value);
            jg.writeEndObject();
        }
    }

    @Override
    public void optionalBoolean(JsonGenerator jg, String name, Boolean b, ZonedDateTime timestamp) throws IOException {
        if (b != null) {
            jg.writeObjectFieldStart(name);
            jg.writeBooleanField("value", b);
            this.optionalTimestamp(jg, "timestamp", timestamp);
            jg.writeEndObject();
        }
    }

    @Override
    public void optionalInteger(JsonGenerator jg, String name, Integer value, ZonedDateTime timestamp) throws IOException {
        if (value != null) {
            jg.writeObjectFieldStart(name);
            jg.writeNumberField("value", value);
            this.optionalTimestamp(jg, "timestamp", timestamp);
            jg.writeEndObject();
        }
    }
}
