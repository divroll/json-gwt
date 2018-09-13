/*
 * Divroll, Platform for Hosting Static Sites
 * Copyright 2018, Divroll, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.json;

import com.google.gwt.json.client.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

/**
 * @author <a href="mailto:kerby@divroll.com">Kerby Martino</a>
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class JSONObject {

    public static JSONNull NULL = JSONNull.getInstance();

    private com.google.gwt.json.client.JSONObject jsonObject;

    public JSONObject() {
        this.jsonObject = new com.google.gwt.json.client.JSONObject();
    }

    public JSONObject(String json) {
        this.jsonObject = JSONParser.parseStrict(json).isObject();
    }

    public JSONObject(com.google.gwt.json.client.JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Object get(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        JSONValue jsonValue = jsonObject.get(key);
        if(jsonValue.isObject() != null) {
            return new JSONObject(jsonValue.isObject());
        } else if(jsonValue.isArray() != null) {
            return new JSONArray(jsonValue.isArray());
        } else if(jsonValue.isNull() != null) {
            return null;
        } else if(jsonValue != null && jsonValue.isBoolean() != null) {
            return jsonValue.isBoolean().booleanValue();
        } else if(jsonValue != null && jsonValue.isString() != null) {
            return jsonValue.isString().stringValue();
        } else if(jsonValue != null && jsonValue.isNumber() != null) {
            return jsonValue.isNumber().doubleValue();
        }
        return jsonValue;
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        return new JSONObject(jsonObject.get(key).isObject());
    }

    public BigDecimal getBigDecimal(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        double value = jsonObject.get(key).isNumber().doubleValue();
        return BigDecimal.valueOf(value);
    }

    public BigInteger getBigInteger(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        double value = jsonObject.get(key).isNumber().doubleValue();
        return BigInteger.valueOf(Double.valueOf(value).longValue());
    }

    public Boolean getBoolean(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        return jsonObject.get(key).isBoolean().booleanValue();
    }

    public Double getDouble(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        return jsonObject.get(key).isNumber().doubleValue();
    }

    public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) throws JSONException {
        throw new IllegalArgumentException("Not yet implemented");
    }

    public Float getFloat(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        return Float.valueOf(Double.valueOf(jsonObject.get(key).isNumber().doubleValue()).floatValue());
    }

    public Integer getInt(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        return Integer.valueOf(Double.valueOf(jsonObject.get(key).isNumber().doubleValue()).intValue());
    }

    public JSONArray getJSONArray(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        com.google.gwt.json.client.JSONArray jsonArray = jsonObject.get(key).isArray();
        if(jsonArray != null) {
            return new JSONArray(jsonArray);
        } else {
            return null;
        }
    }

    public Long getLong(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        return Long.valueOf(Double.valueOf(jsonObject.get(key).isNumber().doubleValue()).longValue());
    }

    public Number getNumber(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        return Double.valueOf(Double.valueOf(jsonObject.get(key).isNumber().doubleValue()).doubleValue());
    }

    public String getString(String key) throws JSONException {
        if(jsonObject == null || jsonObject.get(key) == null) {
            return null;
        }
        return jsonObject.get(key).isString().stringValue();
    }

    public JSONObject put(String key, boolean value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        jsonObject.put(key, JSONBoolean.getInstance(value));
        return this;
    }

    public JSONObject put(String key, Boolean value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, JSONBoolean.getInstance(value));
        return this;
    }

    public JSONObject put(String key, double value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        jsonObject.put(key, new JSONNumber(value));
        return this;

    }

    public JSONObject put(String key, Double value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, new JSONNumber(value));
        return this;

    }

    public JSONObject put(String key, float value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        jsonObject.put(key, new JSONNumber(value));
        return this;

    }

    public JSONObject put(String key, Float value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, new JSONNumber(value));
        return this;

    }

    public JSONObject put(String key, int value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        jsonObject.put(key, new JSONNumber(value));
        return this;

    }

    public JSONObject put(String key, Integer value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, new JSONNumber(value));
        return this;

    }

    public JSONObject put(String key, long value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        jsonObject.put(key, new JSONNumber(value));
        return this;

    }

    public JSONObject put(String key, Long value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, new JSONNumber(value));
        return this;

    }

    public JSONObject put(String key, String value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, new JSONString(value));
        return this;

    }

    public JSONObject put(String key, JSONArray value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, value.asJSONArray());
        return this;
    }

    public JSONObject put(String key, JSONObject value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, value.asJSONObject());
        return this;
    }

    public JSONObject put(String key, JSONNull value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        jsonObject.put(key, value);
        return this;
    }

    public JSONObject put(String key, Object value) throws JSONException {
        if(jsonObject == null) {
            jsonObject = new com.google.gwt.json.client.JSONObject();
        }
        if(value == null) {
            jsonObject.put(key, JSONNull.getInstance());
            return this;
        }
        if(value != null) {
            if(value instanceof Boolean || value.getClass().getName().equals(boolean.class.getName())){
                jsonObject.put(key, JSONBoolean.getInstance((Boolean) value));
            } else if(value instanceof String){
                jsonObject.put(key, new JSONString((String) value));
            } else if(value instanceof Double || value.getClass().getName().equals(double.class.getName())){
                jsonObject.put(key, new JSONNumber((Double) value));
            } else if(value instanceof Float || value.getClass().getName().equals(float.class.getName())){
                jsonObject.put(key, new JSONNumber((Float) value));
            } else if(value instanceof Long || value.getClass().getName().equals(long.class.getName())){
                jsonObject.put(key, new JSONNumber((Long) value));
            } else if(value instanceof Integer || value.getClass().getName().equals(double.class.getName())){
                jsonObject.put(key, new JSONNumber((Integer) value));
            } else if(value instanceof JSONArray){
                JSONArray jsonArray = (JSONArray) value;
                jsonObject.put(key, (jsonArray.asJSONArray()));
            } else if(value instanceof JSONObject){
                JSONObject jso = (JSONObject) value;
                jsonObject.put(key, jso.asJSONObject());
            } else if(value instanceof JSONNull) {
                jsonObject.put(key, (JSONNull) value);
            } else if(value instanceof com.google.gwt.json.client.JSONValue) {
                jsonObject.put(key, (com.google.gwt.json.client.JSONValue) value);
            } else if(value instanceof com.google.gwt.json.client.JSONObject) {
                jsonObject.put(key, (com.google.gwt.json.client.JSONObject) value);
            } else if(value instanceof com.google.gwt.json.client.JSONArray) {
                jsonObject.put(key, (com.google.gwt.json.client.JSONArray) value);
            } else {
                throw new IllegalArgumentException("Object type " + value.getClass().getName() + " is not supported.");
            }
            return this;
        } else {
            jsonObject.put(key, JSONObject.NULL);
            return this;
        }
    }


    public Set<String> keySet() {
        return jsonObject.keySet();
    }

    public com.google.gwt.json.client.JSONObject asJSONObject() {
        return this.jsonObject;
    }

    @Override
    public String toString() {
        return this.jsonObject.toString();
    }
}
