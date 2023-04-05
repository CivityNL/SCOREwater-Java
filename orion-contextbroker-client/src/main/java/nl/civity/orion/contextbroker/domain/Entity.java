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

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

/**
 *
 * @author niekarends
 */
public class Entity {

    public static final String DATE_RECORDED = "dateRecorded";
    private String id;

    private ZonedDateTime dateObserved;

    private String type;

    private final List<Attribute> attributes = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(Entity.class.getName());

    protected static final String ID = "id";
    protected static final String TYPE = "type";
    protected static final String VALUE = "value";

    public Entity(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public Attribute findAttribute(String name) {
        Attribute result = null;

        for (int i = 0; i < this.numAttributes(); i++) {
            Attribute a = this.getAttribute(i);
            if (a.getName().equalsIgnoreCase(name)) {
                result = a;
                break;
            }
        }

        return result;
    }

    public Attribute getAttribute(int index) {
        return this.attributes.get(index);
    }

    public int numAttributes() {
        return this.attributes.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void putAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();

        result.put(ID, this.id);

        result.put(TYPE, this.type);

        for (Attribute a : this.attributes) {
            result.put(a.getName(), a.toJSON());
        }

        return result;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

    public static Entity fromJSON(JSONObject jsonObject) {
        String id = jsonObject.getString(ID);

        String type = "unknown";
        if (jsonObject.has(TYPE)) {
            type = jsonObject.getString(TYPE);
        }

        Entity result = new Entity(id, type);

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                JSONObject valueJSONObject = (JSONObject) value;

                if (valueJSONObject.has(TYPE)) {
                    Object o = valueJSONObject.get(TYPE);
                    if (o instanceof String) {
                        String valueType = valueJSONObject.getString(TYPE);

                        List<Attribute> attributes = new ArrayList<>();

                        if (!valueJSONObject.isNull(VALUE)) {
                            if (valueType.equalsIgnoreCase("Text")) {
                                attributes.add(new Attribute(key, valueType, valueJSONObject.getString(VALUE)));
                            } else if (valueType.equalsIgnoreCase("Integer")) {
                                attributes.add(new Attribute(key, valueType, valueJSONObject.getInt(VALUE)));
                            } else if (valueType.equalsIgnoreCase("Boolean")) {
                                attributes.add(new Attribute(key, valueType, valueJSONObject.getBoolean(VALUE)));
                            } else if (valueType.equalsIgnoreCase("Number")) {
                                attributes.add(new Attribute(key, valueType, valueJSONObject.getDouble(VALUE)));
                            } else if (valueType.equalsIgnoreCase("DateTime")) {
                                attributes.add(new Attribute(key, valueType, ZonedDateTime.parse(valueJSONObject.getString(VALUE))));
                            } else if (valueType.equalsIgnoreCase("geo:json")) {
                                attributes.add(new Attribute(key, valueType, jsonObjectToGeometry(valueJSONObject.getJSONObject(VALUE))));
                            } else if (valueType.equalsIgnoreCase("StructuredValue")) {
                                attributes = structuredValueToAttributes(valueJSONObject.get(VALUE), key + "_");
                            } else {
                                LOGGER.info(String.format("Entity.fromJSON - please add support for value type %s, key %s", valueType, key));
                            }
                        } else {
                            LOGGER.info(String.format("Entity.fromJSON - value for key %s (%s) is null", key, valueType));
                        }

                        for (Attribute attribute : attributes) {
                            result.addAttribute(attribute);
                        }
                    } else {
                        LOGGER.info(String.format("Key: type is not a string in [%s]", valueJSONObject));
                    }
                } else {
                    LOGGER.info(String.format("Key: type, not found in [%s]", valueJSONObject));
                }
            }
        }

        return result;
    }

    protected static List<Attribute> structuredValueToAttributes(Object object, String prefix) {
        ArrayList<Attribute> result = new ArrayList<>();

        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            result.addAll(jsonObjectToAttributes(jsonObject, prefix));
        } else if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            result.add(new Attribute(prefix.replaceAll("_$", ""), "array", jsonArray.toString().replace("[", "").replace("]", "")));
        } else {
            LOGGER.info(String.format("Entity.structuredValueToAttributes - Please add support for [%s], key [%s]", object.getClass().getName(), prefix));
        }

        return result;
    }

    protected static ArrayList<Attribute> jsonObjectToAttributes(JSONObject jsonObject, String prefix) throws JSONException {
        ArrayList<Attribute> result = new ArrayList<>();

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value != null) {
                final String next_prefix = prefix + key;
                if (value instanceof String) {
                    result.add(new Attribute(next_prefix, "Text", value));
                } else if (value instanceof Boolean) {
                    result.add(new Attribute(next_prefix, "Boolean", value));
                } else if (value instanceof Integer) {
                    result.add(new Attribute(next_prefix, "Integer", value));
                } else if (value instanceof Double) {
                    result.add(new Attribute(next_prefix, "Double", value));
                } else if (value instanceof BigDecimal) {
                    result.add(new Attribute(next_prefix, "Double", value));
                } else if (value instanceof JSONObject) {
                    result.addAll(jsonObjectToAttributes((JSONObject) value, next_prefix + "_"));
                } else if (value instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) value;
                    for (Object object : jsonArray) {
                        if (object instanceof JSONObject) {
                            result.addAll(jsonObjectToAttributes((JSONObject) object, next_prefix + "_"));
                        }
                    }
                } else {
                    LOGGER.info(String.format("Entity.jsonObjectToAttributes - Please add support for %s, key %s", value.getClass().getName(), key));
                }
            }
        }

        return result;
    }

    protected static Geometry jsonObjectToGeometry(JSONObject jsonObject) {
        Geometry result = null;

        GeometryFactory gf = new GeometryFactory();

        String t = jsonObject.getString(TYPE);
        if (t.equalsIgnoreCase("Point")) {
            JSONArray jsonArray = jsonObject.getJSONArray("coordinates");
            result = gf.createPoint(new Coordinate(jsonArray.getDouble(1), jsonArray.getDouble(0)));
            result.setSRID(4326);
        } else {
            LOGGER.info(String.format("Entity.jsonObjectToGeometry - Please add support for geometry type %s", t));
        }

        return result;
    }
}
