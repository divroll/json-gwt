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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Wrapper for GWT {@link com.google.gwt.json.client.JSONArray}, providing
 * convenient getter and setter methods for various data types.
 *
 * @author <a href="mailto:kerby@divroll.com">Kerby Martino</a>
 * @version 0.0.1
 * @since 0.0.1
 */
public class JSONArray implements Serializable {

  private static final long serialVersionUID = 1L;

  com.google.gwt.json.client.JSONArray jsonArray;

  /**
   * Constructs an empty JSONArray.
   */
  public JSONArray() {
    this.jsonArray = new com.google.gwt.json.client.JSONArray();
  }

  /**
   * Constructs a JSONArray from a JSON string.
   *
   * @param json the JSON string
   * @throws JSONException if the JSON string is invalid or does not represent an array
   */
  public JSONArray(String json) throws JSONException {
    try {
      this.jsonArray = JSONParser.parseStrict(json).isArray();
      if (this.jsonArray == null) {
        throw new JSONException("JSONArray text must begin with '['");
      }
    } catch (Exception e) {
      throw new JSONException("Invalid JSON string", e);
    }
  }

  /**
   * Constructs a JSONArray from a GWT JSONArray.
   *
   * @param jsonArray the GWT JSONArray
   */
  public JSONArray(com.google.gwt.json.client.JSONArray jsonArray) {
    this.jsonArray = jsonArray;
  }

  /**
   * Retrieves the value at the specified index.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index
   * @throws JSONException if the index is out of bounds or the value is not found
   */
  public Object get(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue jsonValue = jsonArray.get(index);
    if (jsonValue == null) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
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
   * Retrieves the value at the specified index as a BigDecimal.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a BigDecimal
   * @throws JSONException if the index is out of bounds or the value is not a number
   */
  public BigDecimal getBigDecimal(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
    return BigDecimal.valueOf(num.doubleValue());
  }

  /**
   * Retrieves the value at the specified index as a BigInteger.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a BigInteger
   * @throws JSONException if the index is out of bounds or the value is not a number
   */
  public BigInteger getBigInteger(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
    return BigInteger.valueOf(Double.valueOf(num.doubleValue()).longValue());
  }

  /**
   * Retrieves the value at the specified index as a boolean.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a boolean
   * @throws JSONException if the index is out of bounds or the value is not a boolean
   */
  public boolean getBoolean(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONBoolean bool = val.isBoolean();
    if (bool == null) {
      throw new JSONException("JSONArray[" + index + "] is not a Boolean.");
    }
    return bool.booleanValue();
  }

  /**
   * Retrieves the value at the specified index as a double.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a double
   * @throws JSONException if the index is out of bounds or the value is not a number
   */
  public double getDouble(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
    return num.doubleValue();
  }

  /**
   * Retrieves an enum constant by its name at the specified index.
   *
   * @param clazz the enum class
   * @param index the index of the value to retrieve
   * @return the enum constant, or null if the entry is missing or not a string
   * @throws JSONException if the name does not match the enum
   */
  public <E extends Enum<E>> E getEnum(Class<E> clazz, int index) throws JSONException {
    if (jsonArray == null || jsonArray.get(index) == null) {
      return null;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONString js = val.isString();
    if (js == null) {
      return null;
    }
    String name = js.stringValue();
    try {
      return Enum.valueOf(clazz, name);
    } catch (IllegalArgumentException e) {
      throw new JSONException(
              "Value '" + name + "' is not a valid enum constant for " + clazz.getName(), e);
    }
  }

  /**
   * Retrieves the value at the specified index as a float.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a float
   * @throws JSONException if the index is out of bounds or the value is not a number
   */
  public float getFloat(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
    return (float) num.doubleValue();
  }

  /**
   * Retrieves the value at the specified index as an int.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as an int
   * @throws JSONException if the index is out of bounds or the value is not a number
   */
  public int getInt(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
    return (int) num.doubleValue();
  }

  /**
   * Retrieves the value at the specified index as a JSONArray.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a JSONArray
   * @throws JSONException if the index is out of bounds or the value is not a JSONArray
   */
  public JSONArray getJSONArray(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONArray array = val.isArray();
    if (array == null) {
      throw new JSONException("JSONArray[" + index + "] is not a JSONArray.");
    }
    return new JSONArray(array);
  }

  /**
   * Retrieves the value at the specified index as a JSONObject.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a JSONObject
   * @throws JSONException if the index is out of bounds or the value is not a JSONObject
   */
  public JSONObject getJSONObject(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONObject jsonObject = val.isObject();
    if (jsonObject == null) {
      throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
    }
    return new JSONObject(jsonObject);
  }

  /**
   * Retrieves the value at the specified index as a long.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a long
   * @throws JSONException if the index is out of bounds or the value is not a number
   */
  public long getLong(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
    return (long) num.doubleValue();
  }

  /**
   * Retrieves the value at the specified index as a Number.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a Number
   * @throws JSONException if the index is out of bounds or the value is not a number
   */
  public Number getNumber(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
    return num.doubleValue();
  }

  /**
   * Retrieves the value at the specified index as a String.
   *
   * @param index the index of the value to retrieve
   * @return the value at the specified index as a String
   * @throws JSONException if the index is out of bounds or the value is not a string
   */
  public String getString(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONString str = val.isString();
    if (str == null) {
      throw new JSONException("JSONArray[" + index + "] is not a string.");
    }
    return str.stringValue();
  }

  /**
   * Checks if the value at the specified index is null.
   *
   * @param index the index of the value to check
   * @return true if the value is null, false otherwise
   */
  public boolean isNull(int index) {
    if (index < 0 || index >= jsonArray.size()) {
      return false;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    if (val == null) {
      return false;
    }
    return val.isNull() != null;
  }

  /**
   * Returns the number of elements in the JSONArray.
   *
   * @return the number of elements in the JSONArray
   */
  public int length() {
    return jsonArray.size();
  }

  /**
   * Appends a boolean value to the JSONArray.
   *
   * @param value the boolean value to append
   * @return this JSONArray
   */
  public JSONArray put(boolean value) {
    jsonArray.set(jsonArray.size(), JSONBoolean.getInstance(value));
    return this;
  }

  /**
   * Appends a Boolean value to the JSONArray.
   *
   * @param value the Boolean value to append
   * @return this JSONArray
   */
  public JSONArray put(Boolean value) {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
    } else {
      jsonArray.set(jsonArray.size(), JSONBoolean.getInstance(value));
    }
    return this;
  }

  /**
   * Appends a double value to the JSONArray.
   *
   * @param value the double value to append
   * @return this JSONArray
   * @throws JSONException if the value is not finite
   */
  public JSONArray put(double value) throws JSONException {
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  /**
   * Appends a Double value to the JSONArray.
   *
   * @param value the Double value to append
   * @return this JSONArray
   * @throws JSONException if the value is not finite
   */
  public JSONArray put(Double value) throws JSONException {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  /**
   * Appends a float value to the JSONArray.
   *
   * @param value the float value to append
   * @return this JSONArray
   * @throws JSONException if the value is not finite
   */
  public JSONArray put(float value) throws JSONException {
    if (Float.isNaN(value) || Float.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  /**
   * Appends a Float value to the JSONArray.
   *
   * @param value the Float value to append
   * @return this JSONArray
   * @throws JSONException if the value is not finite
   */
  public JSONArray put(Float value) throws JSONException {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    if (Float.isNaN(value) || Float.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  /**
   * Appends an int value to the JSONArray.
   *
   * @param value the int value to append
   * @return this JSONArray
   */
  public JSONArray put(int value) {
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  /**
   * Appends an Integer value to the JSONArray.
   *
   * @param value the Integer value to append
   * @return this JSONArray
   */
  public JSONArray put(Integer value) {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
    } else {
      jsonArray.set(jsonArray.size(), new JSONNumber(value));
    }
    return this;
  }

  /**
   * Appends a long value to the JSONArray.
   *
   * @param value the long value to append
   * @return this JSONArray
   */
  public JSONArray put(long value) {
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  /**
   * Appends a Long value to the JSONArray.
   *
   * @param value the Long value to append
   * @return this JSONArray
   */
  public JSONArray put(Long value) {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
    } else {
      jsonArray.set(jsonArray.size(), new JSONNumber(value));
    }
    return this;
  }

  /**
   * Appends a String value to the JSONArray.
   *
   * @param value the String value to append
   * @return this JSONArray
   */
  public JSONArray put(String value) {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
    } else {
      jsonArray.set(jsonArray.size(), new JSONString(value));
    }
    return this;
  }

  /**
   * Appends a JSONArray value to the JSONArray.
   *
   * @param value the JSONArray value to append
   * @return this JSONArray
   */
  public JSONArray put(JSONArray value) {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
    } else {
      jsonArray.set(jsonArray.size(), value.asJSONArray());
    }
    return this;
  }

  /**
   * Appends a JSONObject value to the JSONArray.
   *
   * @param value the JSONObject value to append
   * @return this JSONArray
   */
  public JSONArray put(JSONObject value) {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
    } else {
      jsonArray.set(jsonArray.size(), value.asJSONObject());
    }
    return this;
  }

  /**
   * Appends a JSONNull value to the JSONArray.
   *
   * @param value the JSONNull value to append
   * @return this JSONArray
   */
  public JSONArray put(JSONNull value) {
    jsonArray.set(jsonArray.size(), JSONNull.getInstance());
    return this;
  }

  /**
   * Appends an Object value to the JSONArray.
   *
   * @param value the Object value to append
   * @return this JSONArray
   * @throws JSONException if the value is not supported
   */
  public JSONArray put(Object value) throws JSONException {
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }

    if (value instanceof Boolean || value.getClass().getName().equals(boolean.class.getName())) {
      jsonArray.set(jsonArray.size(), JSONBoolean.getInstance((Boolean) value));
    } else if (value instanceof String) {
      jsonArray.set(jsonArray.size(), new JSONString((String) value));
    } else if (value instanceof Double || value.getClass().getName().equals(double.class.getName())) {
      Double d = (Double) value;
      if (Double.isNaN(d) || Double.isInfinite(d)) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }
      jsonArray.set(jsonArray.size(), new JSONNumber(d));
    } else if (value instanceof Float || value.getClass().getName().equals(float.class.getName())) {
      Float f = (Float) value;
      if (Float.isNaN(f) || Float.isInfinite(f)) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }
      jsonArray.set(jsonArray.size(), new JSONNumber(f));
    } else if (value instanceof Long || value.getClass().getName().equals(long.class.getName())) {
      jsonArray.set(jsonArray.size(), new JSONNumber((Long) value));
    } else if (value instanceof Integer || value.getClass().getName().equals(int.class.getName())) {
      jsonArray.set(jsonArray.size(), new JSONNumber((Integer) value));
    } else if (value instanceof JSONArray) {
      JSONArray jsonArr = (JSONArray) value;
      jsonArray.set(jsonArray.size(), jsonArr.asJSONArray());
    } else if (value instanceof JSONObject) {
      JSONObject jso = (JSONObject) value;
      jsonArray.set(jsonArray.size(), jso.asJSONObject());
    } else if (value instanceof JSONNull) {
      jsonArray.set(jsonArray.size(), (JSONNull) value);
    } else if (value instanceof com.google.gwt.json.client.JSONValue) {
      jsonArray.set(jsonArray.size(), (com.google.gwt.json.client.JSONValue) value);
    } else if (value instanceof com.google.gwt.json.client.JSONObject) {
      jsonArray.set(jsonArray.size(), (com.google.gwt.json.client.JSONObject) value);
    } else if (value instanceof com.google.gwt.json.client.JSONArray) {
      jsonArray.set(jsonArray.size(), (com.google.gwt.json.client.JSONArray) value);
    } else {
      throw new JSONException("Object type " + value.getClass().getName() + " is not supported.");
    }
    return this;
  }

  /**
   * Sets the value at the specified index to a boolean.
   *
   * @param index the index of the value to set
   * @param value the boolean value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, boolean value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    jsonArray.set(index, JSONBoolean.getInstance(value));
    return this;
  }

  /**
   * Sets the value at the specified index to a Boolean.
   *
   * @param index the index of the value to set
   * @param value the Boolean value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, Boolean value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
    } else {
      jsonArray.set(index, JSONBoolean.getInstance(value));
    }
    return this;
  }

  /**
   * Sets the value at the specified index to a double.
   *
   * @param index the index of the value to set
   * @param value the double value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds or the value is not finite
   */
  public JSONArray put(int index, double value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonArray.set(index, new JSONNumber(value));
    return this;
  }

  /**
   * Sets the value at the specified index to a Double.
   *
   * @param index the index of the value to set
   * @param value the Double value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds or the value is not finite
   */
  public JSONArray put(int index, Double value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
      return this;
    }
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonArray.set(index, new JSONNumber(value));
    return this;
  }

  /**
   * Sets the value at the specified index to a float.
   *
   * @param index the index of the value to set
   * @param value the float value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds or the value is not finite
   */
  public JSONArray put(int index, float value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (Float.isNaN(value) || Float.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonArray.set(index, new JSONNumber(value));
    return this;
  }

  /**
   * Sets the value at the specified index to a Float.
   *
   * @param index the index of the value to set
   * @param value the Float value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds or the value is not finite
   */
  public JSONArray put(int index, Float value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
      return this;
    }
    if (Float.isNaN(value) || Float.isInfinite(value)) {
      throw new JSONException("JSON does not allow non-finite numbers.");
    }
    jsonArray.set(index, new JSONNumber(value));
    return this;
  }

  /**
   * Sets the value at the specified index to an int.
   *
   * @param index the index of the value to set
   * @param value the int value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, int value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    jsonArray.set(index, new JSONNumber(value));
    return this;
  }

  /**
   * Sets the value at the specified index to an Integer.
   *
   * @param index the index of the value to set
   * @param value the Integer value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, Integer value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
    } else {
      jsonArray.set(index, new JSONNumber(value));
    }
    return this;
  }

  /**
   * Sets the value at the specified index to a long.
   *
   * @param index the index of the value to set
   * @param value the long value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, long value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    jsonArray.set(index, new JSONNumber(value));
    return this;
  }

  /**
   * Sets the value at the specified index to a Long.
   *
   * @param index the index of the value to set
   * @param value the Long value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, Long value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
    } else {
      jsonArray.set(index, new JSONNumber(value));
    }
    return this;
  }

  /**
   * Sets the value at the specified index to a String.
   *
   * @param index the index of the value to set
   * @param value the String value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, String value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
    } else {
      jsonArray.set(index, new JSONString(value));
    }
    return this;
  }

  /**
   * Sets the value at the specified index to a JSONArray.
   *
   * @param index the index of the value to set
   * @param value the JSONArray value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, JSONArray value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
    } else {
      jsonArray.set(index, value.asJSONArray());
    }
    return this;
  }

  /**
   * Sets the value at the specified index to a JSONObject.
   *
   * @param index the index of the value to set
   * @param value the JSONObject value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, JSONObject value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
    } else {
      jsonArray.set(index, value.asJSONObject());
    }
    return this;
  }

  /**
   * Sets the value at the specified index to a JSONNull.
   *
   * @param index the index of the value to set
   * @param value the JSONNull value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds
   */
  public JSONArray put(int index, JSONNull value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    jsonArray.set(index, JSONNull.getInstance());
    return this;
  }

  /**
   * Sets the value at the specified index to an Object.
   *
   * @param index the index of the value to set
   * @param value the Object value to set
   * @return this JSONArray
   * @throws JSONException if the index is out of bounds or the value is not supported
   */
  public JSONArray put(int index, Object value) throws JSONException {
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }

    if (value == null) {
      jsonArray.set(index, JSONNull.getInstance());
      return this;
    }

    if (value instanceof Boolean || value.getClass().getName().equals(boolean.class.getName())) {
      jsonArray.set(index, JSONBoolean.getInstance((Boolean) value));
    } else if (value instanceof String) {
      jsonArray.set(index, new JSONString((String) value));
    } else if (value instanceof Double || value.getClass().getName().equals(double.class.getName())) {
      Double d = (Double) value;
      if (Double.isNaN(d) || Double.isInfinite(d)) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }
      jsonArray.set(index, new JSONNumber(d));
    } else if (value instanceof Float || value.getClass().getName().equals(float.class.getName())) {
      Float f = (Float) value;
      if (Float.isNaN(f) || Float.isInfinite(f)) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }
      jsonArray.set(index, new JSONNumber(f));
    } else if (value instanceof Long || value.getClass().getName().equals(long.class.getName())) {
      jsonArray.set(index, new JSONNumber((Long) value));
    } else if (value instanceof Integer || value.getClass().getName().equals(int.class.getName())) {
      jsonArray.set(index, new JSONNumber((Integer) value));
    } else if (value instanceof JSONArray) {
      JSONArray jsonArr = (JSONArray) value;
      jsonArray.set(index, jsonArr.asJSONArray());
    } else if (value instanceof JSONObject) {
      JSONObject jso = (JSONObject) value;
      jsonArray.set(index, jso.asJSONObject());
    } else if (value instanceof JSONNull) {
      jsonArray.set(index, (JSONNull) value);
    } else if (value instanceof com.google.gwt.json.client.JSONValue) {
      jsonArray.set(index, (com.google.gwt.json.client.JSONValue) value);
    } else if (value instanceof com.google.gwt.json.client.JSONObject) {
      jsonArray.set(index, (com.google.gwt.json.client.JSONObject) value);
    } else if (value instanceof com.google.gwt.json.client.JSONArray) {
      jsonArray.set(index, (com.google.gwt.json.client.JSONArray) value);
    } else {
      throw new JSONException("Object type " + value.getClass().getName() + " is not supported.");
    }
    return this;
  }

  /**
   * Removes the value at the specified index.
   *
   * @param index the index of the value to remove
   * @return the removed value
   * @throws JSONException if the index is out of bounds
   */
  public Object remove(int index) throws JSONException {
    if (index < 0 || index >= jsonArray.size()) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    Object value = get(index);

    // Shift elements left to fill the gap
    for (int i = index; i < jsonArray.size() - 1; i++) {
      jsonArray.set(i, jsonArray.get(i + 1));
    }

    // Create a new array with one less element
    com.google.gwt.json.client.JSONArray newArray = new com.google.gwt.json.client.JSONArray();
    for (int i = 0; i < jsonArray.size() - 1; i++) {
      newArray.set(i, jsonArray.get(i));
    }
    this.jsonArray = newArray;

    return value;
  }

  /**
   * Returns the underlying GWT JSONArray.
   *
   * @return the underlying GWT JSONArray
   */
  public com.google.gwt.json.client.JSONArray asJSONArray() {
    return this.jsonArray;
  }

  /**
   * Returns a string representation of the JSONArray.
   *
   * @return a string representation of the JSONArray
   */
  @Override
  public String toString() {
    if (this.jsonArray == null) {
      return "null";
    }
    return this.jsonArray.toString();
  }
}
