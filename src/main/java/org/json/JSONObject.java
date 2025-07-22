/*
 * Divroll, Platform for Hosting Static Sites
 * Copyright 2025, Divroll, and individual contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.json;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

/**
 * Wrapper for GWT {@link com.google.gwt.json.client.JSONObject}, providing
 * convenient getter and setter methods for various data types.
 *
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

  public JSONObject(String json) throws JSONException {
    try {
      this.jsonObject = JSONParser.parseStrict(json).isObject();
      if (this.jsonObject == null) {
        throw new JSONException("JSONObject text must begin with '{'");
      }
    } catch (Exception e) {
      throw new JSONException("Invalid JSON string", e);
    }
  }

  public JSONObject(com.google.gwt.json.client.JSONObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  private String getType(JSONValue jsonValue) {
    if (jsonValue.isObject() != null) {
      return "JSONObject";
    } else if (jsonValue.isArray() != null) {
      return "JSONArray";
    } else if (jsonValue.isNull() != null) {
      return "JSONNull";
    } else if (jsonValue.isBoolean() != null) {
      return "Boolean";
    } else if (jsonValue.isString() != null) {
      return "String";
    } else if (jsonValue.isNumber() != null) {
      return "Number";
    }
    return "Unknown";
  }

  public Object get(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isObject() != null) {
      return new JSONObject(jsonValue.isObject());
    } else if (jsonValue.isArray() != null) {
      return new JSONArray(jsonValue.isArray());
    } else if (jsonValue.isNull() != null) {
      return null;
    } else if (jsonValue != null && jsonValue.isBoolean() != null) {
      return jsonValue.isBoolean().booleanValue();
    } else if (jsonValue != null && jsonValue.isString() != null) {
      return jsonValue.isString().stringValue();
    } else if (jsonValue != null && jsonValue.isNumber() != null) {
      return jsonValue.isNumber().doubleValue();
    }
    return jsonValue;
  }

  public JSONObject getJSONObject(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    com.google.gwt.json.client.JSONObject jsonObjectObject = jsonValue.isObject();
    if (jsonObjectObject != null) {
      return new JSONObject(jsonObjectObject);
    } else {
      throw new JSONException("JSONObject[" + key + "] is not a JSONObject. Found: " + getType(jsonValue));
    }
  }

  public BigDecimal getBigDecimal(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isNumber() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a number. Found: " + getType(jsonValue));
    }
    double value = jsonValue.isNumber().doubleValue();
    return BigDecimal.valueOf(value);
  }

  public BigInteger getBigInteger(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isNumber() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a number. Found: " + getType(jsonValue));
    }
    double value = jsonValue.isNumber().doubleValue();
    return BigInteger.valueOf(Double.valueOf(value).longValue());
  }

  public boolean getBoolean(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isBoolean() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a Boolean. Found: " + getType(jsonValue));
    }
    return jsonValue.isBoolean().booleanValue();
  }

  public double getDouble(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isNumber() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a number. Found: " + getType(jsonValue));
    }
    return jsonValue.isNumber().doubleValue();
  }

  public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) throws JSONException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public float getFloat(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isNumber() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a number. Found: " + getType(jsonValue));
    }
    return (float) jsonValue.isNumber().doubleValue();
  }

  public int getInt(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isNumber() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a number. Found: " + getType(jsonValue));
    }
    return (int) jsonValue.isNumber().doubleValue();
  }

  public JSONArray getJSONArray(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    com.google.gwt.json.client.JSONArray jsonArray = jsonValue.isArray();
    if (jsonArray != null) {
      return new JSONArray(jsonArray);
    } else {
      throw new JSONException("JSONObject[" + key + "] is not a JSONArray. Found: " + getType(jsonValue));
    }
  }

  public long getLong(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isNumber() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a number. Found: " + getType(jsonValue));
    }
    return (long) jsonValue.isNumber().doubleValue();
  }

  public Number getNumber(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isNumber() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a number. Found: " + getType(jsonValue));
    }
    return jsonValue.isNumber().doubleValue();
  }

  public String getString(String key) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      throw new JSONException("JSONObject[" + key + "] not found.");
    }
    JSONValue jsonValue = jsonObject.get(key);
    if (jsonValue.isString() == null) {
      throw new JSONException("JSONObject[" + key + "] is not a string. Found: " + getType(jsonValue));
    }
    return jsonValue.isString().stringValue();
  }

  public boolean has(String key) {
    if (key == null) {
      return false;
    }
    return jsonObject != null && jsonObject.get(key) != null;
  }

  public boolean isNull(String key) {
    if (key == null) {
      return false;
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      return false;
    }
    return jsonObject.get(key).isNull() != null;
  }

  public JSONObject put(String key, boolean value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    jsonObject.put(key, JSONBoolean.getInstance(value));
    return this;
  }

  public JSONObject put(String key, Boolean value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }
    jsonObject.put(key, JSONBoolean.getInstance(value));
    return this;
  }

  public JSONObject put(String key, double value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    jsonObject.put(key, new JSONNumber(value));
    return this;
  }

  public JSONObject put(String key, Double value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonObject.put(key, new JSONNumber(value));
    return this;
  }

  public JSONObject put(String key, float value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (Float.isNaN(value) || Float.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    jsonObject.put(key, new JSONNumber(value));
    return this;
  }

  public JSONObject put(String key, Float value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }
    if (Float.isNaN(value) || Float.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonObject.put(key, new JSONNumber(value));
    return this;
  }

  public JSONObject put(String key, int value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    jsonObject.put(key, new JSONNumber(value));
    return this;
  }

  public JSONObject put(String key, Integer value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }
    jsonObject.put(key, new JSONNumber(value));
    return this;
  }

  public JSONObject put(String key, long value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    jsonObject.put(key, new JSONNumber(value));
    return this;
  }

  public JSONObject put(String key, Long value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }
    jsonObject.put(key, new JSONNumber(value));
    return this;
  }

  public JSONObject put(String key, String value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }
    jsonObject.put(key, new JSONString(value));
    return this;
  }

  public JSONObject put(String key, JSONArray value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }
    jsonObject.put(key, value.asJSONArray());
    return this;
  }

  public JSONObject put(String key, JSONObject value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }
    jsonObject.put(key, value.asJSONObject());
    return this;
  }

  public JSONObject put(String key, JSONNull value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    jsonObject.put(key, JSONNull.getInstance());
    return this;
  }

  public JSONObject put(String key, Object value) throws JSONException {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (jsonObject == null) {
      jsonObject = new com.google.gwt.json.client.JSONObject();
    }
    if (value == null) {
      jsonObject.put(key, JSONNull.getInstance());
      return this;
    }

    if (value instanceof Boolean || value.getClass().getName().equals(boolean.class.getName())) {
      jsonObject.put(key, JSONBoolean.getInstance((Boolean) value));
    } else if (value instanceof String) {
      jsonObject.put(key, new JSONString((String) value));
    } else if (value instanceof Double || value.getClass().getName().equals(double.class.getName())) {
      Double d = (Double) value;
      if (Double.isNaN(d) || Double.isInfinite(d)) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }
      jsonObject.put(key, new JSONNumber(d));
    } else if (value instanceof Float || value.getClass().getName().equals(float.class.getName())) {
      Float f = (Float) value;
      if (Float.isNaN(f) || Float.isInfinite(f)) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }
      jsonObject.put(key, new JSONNumber(f));
    } else if (value instanceof Long || value.getClass().getName().equals(long.class.getName())) {
      jsonObject.put(key, new JSONNumber((Long) value));
    } else if (value instanceof Integer || value.getClass().getName().equals(int.class.getName())) {
      jsonObject.put(key, new JSONNumber((Integer) value));
    } else if (value instanceof JSONArray) {
      JSONArray jsonArray = (JSONArray) value;
      jsonObject.put(key, (jsonArray.asJSONArray()));
    } else if (value instanceof JSONObject) {
      JSONObject jso = (JSONObject) value;
      jsonObject.put(key, jso.asJSONObject());
    } else if (value instanceof JSONNull) {
      jsonObject.put(key, (JSONNull) value);
    } else if (value instanceof com.google.gwt.json.client.JSONValue) {
      jsonObject.put(key, (com.google.gwt.json.client.JSONValue) value);
    } else if (value instanceof com.google.gwt.json.client.JSONObject) {
      jsonObject.put(key, (com.google.gwt.json.client.JSONObject) value);
    } else if (value instanceof com.google.gwt.json.client.JSONArray) {
      jsonObject.put(key, (com.google.gwt.json.client.JSONArray) value);
    } else {
      throw new JSONException("Object type " + value.getClass().getName() + " is not supported.");
    }
    return this;
  }

  public Set<String> keySet() {
    return jsonObject.keySet();
  }

  public com.google.gwt.json.client.JSONObject asJSONObject() {
    return this.jsonObject;
  }

  @Override
  public String toString() {
    if (this.jsonObject == null) {
      return "null";
    }
    return this.jsonObject.toString();
  }
}
