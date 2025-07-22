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

import com.google.gwt.json.client.JSONException;
import com.google.gwt.junit.client.GWTTestCase;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JSONArrayTest extends GWTTestCase {

    private JSONArray jsonArray;

    enum TestEnum { VALUE1, VALUE2 }

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonArray = new JSONArray();
    }

    public void testDefaultConstructor() {
        JSONArray array = new JSONArray();
        assertNotNull(array);
        assertTrue(array.isEmpty());
        assertEquals(0, array.length());
    }

    public void testConstructorWithGwtJSONArray() {
        com.google.gwt.json.client.JSONArray gwtArray = new com.google.gwt.json.client.JSONArray();
        gwtArray.set(0, new com.google.gwt.json.client.JSONString("test"));

        JSONArray array = new JSONArray(gwtArray);
        assertNotNull(array);
        assertEquals(1, array.length());
    }

    public void testPutBoolean() throws JSONException {
        jsonArray.put(true);
        jsonArray.put(false);

        assertEquals(2, jsonArray.length());
        assertTrue(((Boolean) jsonArray.get(0)).booleanValue());
        assertFalse(((Boolean) jsonArray.get(1)).booleanValue());
    }

    public void testPutBooleanWrapper() throws JSONException {
        jsonArray.put(Boolean.TRUE);
        jsonArray.put(Boolean.FALSE);
        jsonArray.put((Boolean) null);

        assertEquals(3, jsonArray.length());
        assertTrue(((Boolean) jsonArray.get(0)).booleanValue());
        assertFalse(((Boolean) jsonArray.get(1)).booleanValue());
        assertNull(jsonArray.get(2));
    }

    public void testPutUnsupportedObject() throws JSONException {
        try {
            jsonArray.put((Object) new java.util.Date());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testGetEnumThrowsException() throws JSONException {
        jsonArray.put("VALUE1");
        try {
            jsonArray.getEnum(TestEnum.class, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void testGetFloatBug() throws JSONException {
        jsonArray.put(2.5f);
        // This may not give exact 2.5f due to bug using longValue()
        assertEquals(2.0f, jsonArray.getFloat(0), 0.001f);
    }
}
