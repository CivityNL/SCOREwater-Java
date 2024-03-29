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

import java.util.HashSet;
import nl.civity.orion.contextbroker.OrionContextBrokerException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basvanmeulebrouk
 */
public class EntityList extends HashSet<Entity> {

    public Entity findEntityById(Entity entity) {
        Entity result = null;

        for (Entity e : this) {
            if (e.getId().equalsIgnoreCase(entity.getId())) {
                result = e;
                break;
            }
        }

        return result;
    }

    public JSONArray toJSON() {
        JSONArray result = new JSONArray();

        for (Entity entity : this) {
            result.put(entity.toJSON());
        }

        return result;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

    public static EntityList fromJSON(JSONArray jsonArray) throws OrionContextBrokerException {
        EntityList result = new EntityList();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object object = jsonArray.get(i);
            if (object instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) object;
                Entity entity = Entity.fromJSON(jsonObject);
                result.add(entity);
            }
        }

        return result;
    }
}
