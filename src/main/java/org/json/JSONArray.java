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
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Wrapper for GWT {@link com.google.gwt.json.client.JSONArray}, providing
 * convenient getter and setter methods for various data types.
 *
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
    com.google.gwt.json.client.JSONValue jsonValue = jsonArray.get(index);
    if (jsonValue == null) {
      return null;
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

  public BigDecimal getBigDecimal(int index) throws JSONException {
    if (jsonArray == null && jsonArray.get(index) != null) {
      return null;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      return null;
    }
    return BigDecimal.valueOf(num.doubleValue());
  }

  public BigInteger getBigInteger(int index) throws JSONException {
    if (jsonArray == null && jsonArray.get(index) != null) {
      return null;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      return null;
    }
    return BigInteger.valueOf(Double.valueOf(num.doubleValue()).longValue());
  }

  public Boolean getBoolean(int index) throws JSONException {
    if (jsonArray == null || jsonArray.get(index) == null) {
      return false;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONBoolean bool = val.isBoolean();
    if (bool == null) {
      return null;
    }
    return bool.booleanValue();
  }

  public Double getDouble(int index) throws JSONException {
    if (jsonArray == null || jsonArray.get(index) == null) {
      return null;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      return null;
    }
    return num.doubleValue();
  }

  public <E extends Enum<E>> E getEnum(Class<E> clazz, int index) throws JSONException {
    throw new IllegalArgumentException("Not yet implemented");
  }

  public Float getFloat(int index) throws JSONException {
    if (jsonArray == null || jsonArray.get(index) == null) {
      return null;
    }
    com.google.gwt.json.client.JSONNumber num = jsonArray.get(index).isNumber();
    if (num == null) {
      return null;
    }
    return (float) num.doubleValue();
  }

  public Integer getInt(int index) throws JSONException {
    if (jsonArray == null || jsonArray.get(index) == null) {
      return null;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      return null;
    }
    return Integer.valueOf(Double.valueOf(num.doubleValue()).intValue());
  }

  public JSONArray getJSONArray(int index) throws JSONException {
    if (jsonArray == null
            || jsonArray.get(index) == null
            || jsonArray.get(index).isArray() == null) {
      return null;
    }
    com.google.gwt.json.client.JSONArray array = jsonArray.get(index).isArray();
    return new JSONArray(array);
  }

  public JSONObject getJSONObject(int index) throws JSONException {
    if (jsonArray == null
            || jsonArray.get(index) == null
            || jsonArray.get(index).isObject() == null) {
      return null;
    }
    com.google.gwt.json.client.JSONObject jsonObject = jsonArray.get(index).isObject();
    return new JSONObject(jsonObject);
  }

  public Long getLong(int index) throws JSONException {
    if (jsonArray == null || jsonArray.get(index) == null) {
      return null;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      return null;
    }
    return Long.valueOf(Double.valueOf(num.doubleValue()).longValue());
  }

  public Number getNumber(int index) throws JSONException {
    if (jsonArray == null && jsonArray.get(index) != null) {
      return null;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONNumber num = val.isNumber();
    if (num == null) {
      return null;
    }
    return Double.valueOf(num.doubleValue());
  }

  public String getString(int index) throws JSONException {
    if (jsonArray == null && jsonArray.get(index) != null) {
      return null;
    }
    com.google.gwt.json.client.JSONValue val = jsonArray.get(index);
    com.google.gwt.json.client.JSONString str = val.isString();
    if (str == null) {
      return null;
    }
    return str.stringValue();
  }

  public boolean isEmpty() {
    return jsonArray.size() == 0;
  }

  public boolean isNull(int index) {
    return jsonArray.get(index).isNull() != null;
  }

  public JSONArray put(boolean value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    jsonArray.set(jsonArray.size(), JSONBoolean.getInstance(value));
    return this;
  }

  public JSONArray put(Boolean value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    jsonArray.set(jsonArray.size(), JSONBoolean.getInstance(value));
    return this;
  }

  public JSONArray put(double value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  public JSONArray put(Double value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  public JSONArray put(float value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  public JSONArray put(Float value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  public JSONArray put(int value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  public JSONArray put(Integer value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  public JSONArray put(long value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  public JSONArray put(Long value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    jsonArray.set(jsonArray.size(), new JSONNumber(value));
    return this;
  }

  public JSONArray put(JSONArray value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    jsonArray.set(jsonArray.size(), value.asJSONArray());
    return this;
  }

  public JSONArray put(JSONObject value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    jsonArray.set(jsonArray.size(), value.asJSONObject());
    return this;
  }

  public JSONArray put(JSONNull value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    jsonArray.set(jsonArray.size(), value);
    return this;
  }

  public JSONArray put(Object value) throws JSONException {
    if (jsonArray == null) {
      jsonArray = new com.google.gwt.json.client.JSONArray();
    }
    if (value == null) {
      jsonArray.set(jsonArray.size(), JSONNull.getInstance());
      return this;
    }
    if (value != null) {
      if (value instanceof Boolean || value.getClass().getName().equals(boolean.class.getName())) {
        jsonArray.set(jsonArray.size(), JSONBoolean.getInstance((Boolean) value));
      } else if (value instanceof String) {
        jsonArray.set(jsonArray.size(), new JSONString((String) value));
      } else if (value instanceof Double || value.getClass()
          .getName()
          .equals(double.class.getName())) {
        jsonArray.set(jsonArray.size(), new JSONNumber((Double) value));
      } else if (value instanceof Float || value.getClass()
          .getName()
          .equals(float.class.getName())) {
        jsonArray.set(jsonArray.size(), new JSONNumber((Float) value));
      } else if (value instanceof Long || value.getClass().getName().equals(long.class.getName())) {
        jsonArray.set(jsonArray.size(), new JSONNumber((Long) value));
      } else if (value instanceof Integer || value.getClass()
          .getName()
          .equals(double.class.getName())) {
        jsonArray.set(jsonArray.size(), new JSONNumber((Integer) value));
      } else if (value instanceof JSONArray) {
        JSONArray jsonArray = (JSONArray) value;
        this.jsonArray.set(this.jsonArray.size(), (jsonArray.asJSONArray()));
      } else if (value instanceof JSONObject) {
        JSONObject jsonObject = (JSONObject) value;
        jsonArray.set(jsonArray.size(), jsonObject.asJSONObject());
      } else if (value instanceof JSONNull) {
        jsonArray.set(jsonArray.size(), (JSONNull) value);
      } else {
        throw new IllegalArgumentException(
            "Object type " + value.getClass().getName() + " is not supported.");
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
    if (jsonArray == null) {
      return null;
    } else if (jsonArray.toString() == "null") {
      return null;
    }
    return this.jsonArray.toString();
  }
}
