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
package nl.civity.scorewater.fiware.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.time.ZonedDateTime;
import nl.civity.scorewater.fiware.datamodel.Location;

/**
 *
 * @author basvanmeulebrouk
 */
public interface OptionalSerializer {

    public void optionalBoolean(JsonGenerator jg, String name, Boolean b) throws IOException;

    public void optionalBoolean(JsonGenerator jg, String name, Boolean b, ZonedDateTime timestamp) throws IOException;
    
    public void optionalDouble(JsonGenerator jg, String name, Double d) throws IOException;
    
    public void optionalDouble(JsonGenerator jg, String name, Double d, ZonedDateTime timestamp) throws IOException;

    public void optionalInteger(JsonGenerator jg, String name, Integer value) throws IOException;

    public void optionalInteger(JsonGenerator jg, String name, Integer value, ZonedDateTime timestamp) throws IOException;
    
    public void optionalString(JsonGenerator jg, String name, String string) throws IOException;
    
    public void optionalString(JsonGenerator jg, String name, String string, ZonedDateTime timestamp) throws IOException;

    public void optionalLocation(JsonGenerator jg, String name, Location location) throws IOException;
    
    public void optionalStringArray(JsonGenerator jg, String name, String[] array) throws IOException;
    
    public void optionalTimestamp(JsonGenerator jg, String name, ZonedDateTime timestamp) throws IOException;
}
