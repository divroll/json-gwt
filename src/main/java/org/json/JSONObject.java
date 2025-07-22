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

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import java.io.Serializable;
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
public class JSONObject implements Serializable {

  private static final long serialVersionUID = 1L;

  public static JSONNull NULL = JSONNull.getInstance();

  private com.google.gwt.json.client.JSONObject jsonObject;

  /**
   * Constructs an empty JSONObject.
   */
  public JSONObject() {
    this.jsonObject = new com.google.gwt.json.client.JSONObject();
  }

  /**
   * Constructs a JSONObject from a JSON string.
   *
   * @param json the JSON string
   * @throws JSONException if the JSON string is invalid or does not represent an object
   */
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

  /**
   * Constructs a JSONObject from a GWT JSONObject.
   *
   * @param jsonObject the GWT JSONObject
   */
  public JSONObject(com.google.gwt.json.client.JSONObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  /**
   * Returns the type of the JSON value.
   *
   * @param jsonValue the JSON value
   * @return the type of the JSON value
   */
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

  /**
   * Retrieves the value associated with the specified key.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key
   * @throws JSONException if the key is null or the value is not found
   */
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

  /**
   * Retrieves the value associated with the specified key as a JSONObject.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a JSONObject
   * @throws JSONException if the key is null, the value is not found, or the value is not a JSONObject
   */
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

  /**
   * Retrieves the value associated with the specified key as a BigDecimal.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a BigDecimal
   * @throws JSONException if the key is null, the value is not found, or the value is not a number
   */
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

  /**
   * Retrieves the value associated with the specified key as a BigInteger.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a BigInteger
   * @throws JSONException if the key is null, the value is not found, or the value is not a number
   */
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

  /**
   * Retrieves the value associated with the specified key as a boolean.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a boolean
   * @throws JSONException if the key is null, the value is not found, or the value is not a boolean
   */
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

  /**
   * Retrieves the value associated with the specified key as a double.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a double
   * @throws JSONException if the key is null, the value is not found, or the value is not a number
   */
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

  /**
   * Retrieves an enum constant by its name associated with the specified key.
   *
   * @param clazz the enum class
   * @param key the key of the value to retrieve
   * @return the enum constant, or null if the entry is missing or not a string
   * @throws JSONException if the name does not match the enum
   */
  public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) throws JSONException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  /**
   * Retrieves the value associated with the specified key as a float.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a float
   * @throws JSONException if the key is null, the value is not found, or the value is not a number
   */
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

  /**
   * Retrieves the value associated with the specified key as an int.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as an int
   * @throws JSONException if the key is null, the value is not found, or the value is not a number
   */
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

  /**
   * Retrieves the value associated with the specified key as a JSONArray.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a JSONArray
   * @throws JSONException if the key is null, the value is not found, or the value is not a JSONArray
   */
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

  /**
   * Retrieves the value associated with the specified key as a long.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a long
   * @throws JSONException if the key is null, the value is not found, or the value is not a number
   */
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

  /**
   * Retrieves the value associated with the specified key as a Number.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a Number
   * @throws JSONException if the key is null, the value is not found, or the value is not a number
   */
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

  /**
   * Retrieves the value associated with the specified key as a String.
   *
   * @param key the key of the value to retrieve
   * @return the value associated with the specified key as a String
   * @throws JSONException if the key is null, the value is not found, or the value is not a string
   */
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

  /**
   * Checks if the JSONObject contains the specified key.
   *
   * @param key the key to check
   * @return true if the JSONObject contains the specified key, false otherwise
   */
  public boolean has(String key) {
    if (key == null) {
      return false;
    }
    return jsonObject != null && jsonObject.get(key) != null;
  }

  /**
   * Checks if the value associated with the specified key is null.
   *
   * @param key the key of the value to check
   * @return true if the value is null, false otherwise
   */
  public boolean isNull(String key) {
    if (key == null) {
      return false;
    }
    if (jsonObject == null || jsonObject.get(key) == null) {
      return false;
    }
    return jsonObject.get(key).isNull() != null;
  }

  /**
   * Associates a boolean value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the boolean value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates a Boolean value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the Boolean value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates a double value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the double value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null or the value is not finite
   */
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

  /**
   * Associates a Double value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the Double value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null or the value is not finite
   */
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

  /**
   * Associates a float value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the float value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null or the value is not finite
   */
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

  /**
   * Associates a Float value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the Float value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null or the value is not finite
   */
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

  /**
   * Associates an int value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the int value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates an Integer value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the Integer value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates a long value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the long value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates a Long value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the Long value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates a String value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the String value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates a JSONArray value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the JSONArray value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates a JSONObject value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the JSONObject value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates a JSONNull value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the JSONNull value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null
   */
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

  /**
   * Associates an Object value with the specified key.
   *
   * @param key the key of the value to associate
   * @param value the Object value to associate
   * @return this JSONObject
   * @throws JSONException if the key is null or the value is not supported
   */
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

  /**
   * Returns a set of keys in the JSONObject.
   *
   * @return a set of keys in the JSONObject
   */
  public Set<String> keySet() {
    return jsonObject.keySet();
  }

  /**
   * Returns the underlying GWT JSONObject.
   *
   * @return the underlying GWT JSONObject
   */
  public com.google.gwt.json.client.JSONObject asJSONObject() {
    return this.jsonObject;
  }

  /**
   * Returns a string representation of the JSONObject.
   *
   * @return a string representation of the JSONObject
   */
  @Override
  public String toString() {
    if (this.jsonObject == null) {
      return "null";
    }
    return this.jsonObject.toString();
  }

  /**
   * Returns an array of the keys in the JSONObject.
   *
   * @return an array of the keys in the JSONObject
   */
  public static String[] getNames(JSONObject jsonObject) {
    if (jsonObject == null) {
      return null;
    }
    Set<String> keys = jsonObject.keySet();
    return keys.toArray(new String[0]);
  }
}
