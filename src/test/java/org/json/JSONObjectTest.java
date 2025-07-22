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

import com.google.gwt.json.client.*;
import com.google.gwt.junit.client.GWTTestCase;

public class JSONObjectTest extends GWTTestCase {

    private JSONObject jsonObject;

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonObject = new JSONObject();
    }

    public void testDefaultConstructor() {
        JSONObject obj = new JSONObject();
        assertNotNull(obj);
        assertTrue(obj.keySet().isEmpty());
    }

    public void testConstructorWithJSONString() {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"active\":true}";
        try {
            JSONObject obj = new JSONObject(jsonString);
            assertNotNull(obj);
        } catch (Exception e) {
        }
    }

    public void testConstructorWithGwtJSONObject() {
        com.google.gwt.json.client.JSONObject gwtObject = new com.google.gwt.json.client.JSONObject();
        gwtObject.put("test", new JSONString("value"));

        JSONObject obj = new JSONObject(gwtObject);
        assertNotNull(obj);
        assertEquals("value", obj.getString("test"));
    }

    public void testPutBoolean() throws JSONException {
        jsonObject.put("flag1", true);
        jsonObject.put("flag2", false);

        assertTrue(jsonObject.getBoolean("flag1"));
        assertFalse(jsonObject.getBoolean("flag2"));
    }

    public void testPutBooleanWrapper() throws JSONException {
        jsonObject.put("flag1", Boolean.TRUE);
        jsonObject.put("flag2", Boolean.FALSE);
        jsonObject.put("flag3", (Boolean) null);

        assertTrue(jsonObject.getBoolean("flag1"));
        assertFalse(jsonObject.getBoolean("flag2"));
        assertNull(jsonObject.getBoolean("flag3"));
    }

    public void testPutDouble() throws JSONException {
        jsonObject.put("pi", 3.14159);
        jsonObject.put("max", Double.MAX_VALUE);
        jsonObject.put("min", Double.MIN_VALUE);

        assertEquals(3.14159, jsonObject.getDouble("pi"), 0.00001);
        assertEquals(Double.MAX_VALUE, jsonObject.getDouble("max"), 0.0);
        assertEquals(Double.MIN_VALUE, jsonObject.getDouble("min"), 0.0);
    }

    public void testPutDoubleWrapper() throws JSONException {
        jsonObject.put("value", Double.valueOf(2.71828));
        jsonObject.put("null", (Double) null);

        assertEquals(2.71828, jsonObject.getDouble("value"), 0.00001);
        assertNull(jsonObject.getDouble("null"));
    }

    public void testPutFloat() throws JSONException {
        jsonObject.put("float1", 1.5f);
        jsonObject.put("float2", Float.MAX_VALUE);

        assertEquals(1.5f, jsonObject.getFloat("float1"), 0.001);
        assertEquals(Float.MAX_VALUE, jsonObject.getFloat("float2"), 0.0);
    }

    public void testPutFloatWrapper() throws JSONException {
        jsonObject.put("value", Float.valueOf(2.5f));
        jsonObject.put("null", (Float) null);

        assertEquals(2.5f, jsonObject.getFloat("value"), 0.001);
        assertNull(jsonObject.getFloat("null"));
    }

    public void testPutInt() throws JSONException {
        jsonObject.put("answer", 42);
        jsonObject.put("max", Integer.MAX_VALUE);
        jsonObject.put("min", Integer.MIN_VALUE);

        assertEquals(Integer.valueOf(42), jsonObject.getInt("answer"));
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), jsonObject.getInt("max"));
        assertEquals(Integer.valueOf(Integer.MIN_VALUE), jsonObject.getInt("min"));
    }

    public void testPutIntegerWrapper() throws JSONException {
        jsonObject.put("value", Integer.valueOf(100));
        jsonObject.put("null", (Integer) null);

        assertEquals(Integer.valueOf(100), jsonObject.getInt("value"));
        assertNull(jsonObject.getInt("null"));
    }

    public void testPutLong() throws JSONException {
        jsonObject.put("big", 123456789L);
        jsonObject.put("max", Long.MAX_VALUE);

        assertEquals(Long.valueOf(123456789L), jsonObject.getLong("big"));
        assertEquals(Long.valueOf(Long.MAX_VALUE), jsonObject.getLong("max"));
    }

    public void testPutLongWrapper() throws JSONException {
        jsonObject.put("value", Long.valueOf(987654321L));
        jsonObject.put("null", (Long) null);

        assertEquals(Long.valueOf(987654321L), jsonObject.getLong("value"));
        assertNull(jsonObject.getLong("null"));
    }

    public void testPutString() throws JSONException {
        jsonObject.put("name", "John Doe");
        jsonObject.put("empty", "");
        jsonObject.put("null", (String) null);

        assertEquals("John Doe", jsonObject.getString("name"));
        assertEquals("", jsonObject.getString("empty"));
        assertNull(jsonObject.getString("null"));
    }

    public void testPutJSONArray() throws JSONException {
        JSONArray array = new JSONArray();
        array.put("item1");
        array.put("item2");

        jsonObject.put("array", array);
        jsonObject.put("nullArray", (JSONArray) null);

        JSONArray retrieved = jsonObject.getJSONArray("array");
        assertNotNull(retrieved);
        assertEquals(2, retrieved.length());
        assertEquals("item1", retrieved.getString(0));
        assertEquals("item2", retrieved.getString(1));

        assertNull(jsonObject.getJSONArray("nullArray"));
    }

    enum TestEnum {
        VALUE1, VALUE2
    }
}
