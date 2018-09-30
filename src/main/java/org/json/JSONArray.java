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

/**
 * @author <a href="mailto:kerby@divroll.com">Kerby Martino</a>
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class JSONArray {

    com.google.gwt.json.client.JSONArray jsonArray;

    public JSONArray() {
        this.jsonArray = new com.google.gwt.json.client.JSONArray();
    }

    public JSONArray(com.google.gwt.json.client.JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
    public Object get(int index) throws JSONException {
        com.google.gwt.json.client.JSONValue jsonValue= jsonArray.get(index);
        if(jsonValue == null) {
            return null;
        }
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
    public BigDecimal getBigDecimal (int index) throws JSONException {
        if(jsonArray == null && jsonArray.get(index)!= null) {
            return  null;
        }
        double value = jsonArray.get(index).isNumber().doubleValue();
        return BigDecimal.valueOf(value);
    }
    public BigInteger getBigInteger (int index) throws JSONException {
        if(jsonArray == null && jsonArray.get(index)!= null) {
            return  null;
        }
        double value = jsonArray.get(index).isNumber().doubleValue();
        return BigInteger.valueOf(Double.valueOf(value).longValue());
    }
    public boolean getBoolean(int index) throws JSONException {
        return jsonArray.get(index).isBoolean().booleanValue();
    }
    public double getDouble(int index) throws JSONException {
        double value = jsonArray.get(index).isNumber().doubleValue();
        return Double.valueOf(value);
    }
    public <E extends Enum<E>> E getEnum(Class<E> clazz, int index) throws JSONException {
        throw new IllegalArgumentException("Not yet implemented");
    }
    public float getFloat(int index) throws JSONException {
        double value = jsonArray.get(index).isNumber().doubleValue();
        return Float.valueOf(Double.valueOf(value).longValue());
    }
    public int getInt(int index) throws JSONException {
        double value = jsonArray.get(index).isNumber().doubleValue();
        return Integer.valueOf(Double.valueOf(value).intValue());
    }
    public JSONArray getJSONArray(int index) throws JSONException {
        if(jsonArray == null && jsonArray.get(index)!= null && jsonArray.get(index).isArray() != null) {
            return  null;
        }
        com.google.gwt.json.client.JSONArray array = jsonArray.get(index).isArray();
        return new JSONArray(array);
    }
    public JSONObject getJSONObject(int index) throws JSONException {
        if(jsonArray == null && jsonArray.get(index)!= null && jsonArray.get(index).isObject() != null) {
            return  null;
        }
        com.google.gwt.json.client.JSONObject jsonObject = jsonArray.get(index).isObject();
        return new JSONObject(jsonObject);
    }
    public long getLong(int index) throws JSONException {
        double value = jsonArray.get(index).isNumber().doubleValue();
        return Long.valueOf(Double.valueOf(value).longValue());
    }
    public Number getNumber(int index) throws JSONException {
        if(jsonArray == null && jsonArray.get(index)!= null) {
            return  null;
        }
        double value = jsonArray.get(index).isNumber().doubleValue();
        return Double.valueOf(value);
    }
    public String getString(int index) throws JSONException {
        if(jsonArray == null && jsonArray.get(index)!= null) {
            return  null;
        }
        if(jsonArray.get(index).isString() == null) {
            return null;
        }
        return jsonArray.get(index).isString().stringValue();
    }
    public boolean isEmpty() {
        return jsonArray.size() == 0;
    }
    public boolean isNull(int index) {
        return (jsonArray.get(index).isNull() != null) ? true : false;
    }

    public JSONArray put(boolean value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        jsonArray.set(jsonArray.size(), JSONBoolean.getInstance(value));
        return this;
    }

    public JSONArray put(Boolean value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        jsonArray.set(jsonArray.size(), JSONBoolean.getInstance(value));
        return this;
    }

    public JSONArray put(double value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        jsonArray.set(jsonArray.size(), new JSONNumber(value));
        return this;

    }

    public JSONArray put(Double value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        jsonArray.set(jsonArray.size(), new JSONNumber(value));
        return this;

    }

    public JSONArray put(float value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        jsonArray.set(jsonArray.size(), new JSONNumber(value));
        return this;

    }

    public JSONArray put(Float value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        jsonArray.set(jsonArray.size(), new JSONNumber(value));
        return this;

    }

    public JSONArray put(int value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        jsonArray.set(jsonArray.size(), new JSONNumber(value));
        return this;

    }

    public JSONArray put(Integer value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        jsonArray.set(jsonArray.size(), new JSONNumber(value));
        return this;

    }

    public JSONArray put(long value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        jsonArray.set(jsonArray.size(), new JSONNumber(value));
        return this;

    }

    public JSONArray put(Long value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        jsonArray.set(jsonArray.size(), new JSONNumber(value));
        return this;

    }


    public JSONArray put(JSONArray value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        jsonArray.set(jsonArray.size(), value.asJSONArray());
        return this;
    }

    public JSONArray put(JSONObject value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        jsonArray.set(jsonArray.size(), value.asJSONObject());
        return this;
    }

    public JSONArray put(JSONNull value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        jsonArray.set(jsonArray.size(), value);
        return this;
    }

    public JSONArray put(Object value) throws JSONException {
        if(jsonArray == null) {
            jsonArray = new com.google.gwt.json.client.JSONArray();
        }
        if(value == null) {
            jsonArray.set(jsonArray.size(), JSONNull.getInstance());
            return this;
        }
        if(value != null) {
            if(value instanceof Boolean || value.getClass().getName().equals(boolean.class.getName())){
                jsonArray.set(jsonArray.size(), JSONBoolean.getInstance((Boolean) value));
            } else if(value instanceof String){
                jsonArray.set(jsonArray.size(), new JSONString((String) value));
            } else if(value instanceof Double || value.getClass().getName().equals(double.class.getName())){
                jsonArray.set(jsonArray.size(), new JSONNumber((Double) value));
            } else if(value instanceof Float || value.getClass().getName().equals(float.class.getName())){
                jsonArray.set(jsonArray.size(), new JSONNumber((Float) value));
            } else if(value instanceof Long || value.getClass().getName().equals(long.class.getName())){
                jsonArray.set(jsonArray.size(), new JSONNumber((Long) value));
            } else if(value instanceof Integer || value.getClass().getName().equals(double.class.getName())){
                jsonArray.set(jsonArray.size(), new JSONNumber((Integer) value));
            } else if(value instanceof JSONArray){
                JSONArray jsonArray = (JSONArray) value;
                this.jsonArray.set(this.jsonArray.size(), (jsonArray.asJSONArray()));
            } else if(value instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) value;
                jsonArray.set(jsonArray.size(), jsonObject.asJSONObject());
            } else if(value instanceof JSONNull) {
                jsonArray.set(jsonArray.size(), (JSONNull) value);
            } else {
                throw new IllegalArgumentException("Object type " + value.getClass().getName() + " is not supported.");
            }
            return this;
        } else {
            jsonArray.set(jsonArray.size(), JSONObject.NULL);
            return this;
        }
    }

    public int length() {
        return jsonArray.size();
    }

    public com.google.gwt.json.client.JSONArray asJSONArray() {
        return this.jsonArray;
    }

    @Override
    public String toString() {
        return this.jsonArray.toString();
    }

}
