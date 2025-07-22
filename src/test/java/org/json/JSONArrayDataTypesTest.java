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

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.junit.client.GWTTestCase;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JSONArrayDataTypesTest extends GWTTestCase {

    private JSONArray jsonArray;

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonArray = new JSONArray();
    }

    public void test_boolean_values_are_stored_and_retrieved_correctly() throws JSONException {
        jsonArray.put(true);
        jsonArray.put(false);

        assertEquals(2, jsonArray.length());
        assertTrue(((Boolean) jsonArray.get(0)).booleanValue());
        assertFalse(((Boolean) jsonArray.get(1)).booleanValue());
    }

    public void test_boolean_wrapper_objects_including_null_are_handled_properly() throws JSONException {
        jsonArray.put(Boolean.TRUE);
        jsonArray.put(Boolean.FALSE);
        jsonArray.put((Boolean) null);

        assertEquals(3, jsonArray.length());
        assertTrue(((Boolean) jsonArray.get(0)).booleanValue());
        assertFalse(((Boolean) jsonArray.get(1)).booleanValue());
        try {
            assertNull(jsonArray.get(2));
        } catch (JSONException e) {
            assertEquals("JSONArray[2] is not a Boolean.", e.getMessage());
        }
    }

    public void test_float_values_maintain_precision_when_retrieved() throws JSONException {
        jsonArray.put(2.5f);
        assertEquals(2.5f, jsonArray.getFloat(0), 0.001f);
    }

    public void test_primitive_values_are_stored_and_retrieved_with_correct_types() throws JSONException {
        jsonArray.put(true).put(false);
        jsonArray.put(1.234d).put(2.5f);
        jsonArray.put(42).put(123456789L);

        assertEquals(6, jsonArray.length());
        assertTrue(jsonArray.getBoolean(0));
        assertFalse(jsonArray.getBoolean(1));
        assertEquals(1.234d, jsonArray.getDouble(2));
        assertEquals(2.5f, jsonArray.getFloat(3), 0.0001f);
        assertEquals(42, jsonArray.getInt(4));
        assertEquals(123456789L, jsonArray.getLong(5));
    }

    public void test_null_wrapper_objects_return_null_when_retrieved() throws JSONException {
        jsonArray.put((Boolean) null)
                .put((Double) null)
                .put((Float) null)
                .put((Integer) null)
                .put((Long) null)
                .put((JSONArray) null);

        assertEquals(6, jsonArray.length());

        try {
            jsonArray.getBoolean(0);
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONArray[0] is not a Boolean.", e.getMessage());
        }

        try {
            jsonArray.getBigDecimal(1);
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONArray[1] is not a number.", e.getMessage());
        }

        try {
            jsonArray.getFloat(2);
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONArray[2] is not a number.", e.getMessage());
        }

        try {
            jsonArray.getInt(3);
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONArray[3] is not a number.", e.getMessage());
        }

        try {
            jsonArray.getLong(4);
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONArray[4] is not a number.", e.getMessage());
        }

        try {
            jsonArray.getJSONArray(5);
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONArray[5] is not a JSONArray.", e.getMessage());
        }
    }

    public void test_numeric_values_can_be_retrieved_as_big_decimal_and_big_integer() throws JSONException {
        jsonArray.put(10.75).put(123456789);
        BigDecimal bd = jsonArray.getBigDecimal(0);
        BigInteger bi = jsonArray.getBigInteger(1);

        assertEquals(new BigDecimal("10.75"), bd);
        assertEquals(BigInteger.valueOf(123456789L), bi);
    }

    public void test_string_number_and_null_values_are_retrieved_with_appropriate_types() throws JSONException {
        jsonArray.put("hello");
        jsonArray.put(3.14);
        jsonArray.put((String) null);

        assertEquals("hello", jsonArray.getString(0));
        assertEquals(Double.valueOf(3.14), jsonArray.getNumber(1));
        try {
            String jsonString = jsonArray.getString(2);
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONArray[2] is not a string.", e.getMessage());
        }
    }
}
