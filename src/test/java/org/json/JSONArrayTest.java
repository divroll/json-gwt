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
import com.google.gwt.json.client.JSONNull;
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

    public void test_new_empty_array_is_created_successfully() {
        JSONArray array = new JSONArray();
        assertNotNull(array);
        assertTrue(array.isEmpty());
        assertEquals(0, array.length());
    }

    public void test_array_can_be_initialized_from_gwt_json_array() {
        com.google.gwt.json.client.JSONArray gwtArray = new com.google.gwt.json.client.JSONArray();
        gwtArray.set(0, new com.google.gwt.json.client.JSONString("test"));

        JSONArray array = new JSONArray(gwtArray);
        assertNotNull(array);
        assertEquals(1, array.length());
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
        assertNull(jsonArray.get(2));
    }

    public void test_unsupported_objects_throw_illegal_argument_exception() throws JSONException {
        try {
            jsonArray.put((Object) new java.util.Date());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void test_enum_retrieval_throws_exception_for_string_values() throws JSONException {
        jsonArray.put("VALUE1");
        try {
            jsonArray.getEnum(TestEnum.class, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
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
        assertEquals(42, jsonArray.getInt(4).intValue());
        assertEquals(123456789L, jsonArray.getLong(5).longValue());
    }

    public void test_null_wrapper_objects_return_null_when_retrieved() throws JSONException {
        jsonArray.put((Boolean) null)
                .put((Double) null)
                .put((Float) null)
                .put((Integer) null)
                .put((Long) null)
                .put((JSONArray) null);

        assertEquals(6, jsonArray.length());
        assertNull(jsonArray.get(0));
        assertNull(jsonArray.getBigDecimal(1));
        assertNull(jsonArray.getFloat(2));
        assertNull(jsonArray.getInt(3));
        assertNull(jsonArray.getLong(4));
        assertNull(jsonArray.getJSONArray(5));
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
        assertNull(jsonArray.getString(2));
    }

    public void test_nested_arrays_and_objects_maintain_their_structure_and_data() throws JSONException {
        JSONArray innerArray = new JSONArray();
        innerArray.put(1).put(2);
        JSONObject innerObj = new JSONObject();
        innerObj.put("key", "value");

        jsonArray.put(innerArray).put(innerObj);

        JSONArray outArr = jsonArray.getJSONArray(0);
        JSONObject outObj = jsonArray.getJSONObject(1);

        assertNotNull(outArr);
        assertEquals(2, outArr.length());
        assertEquals(1, outArr.getInt(0).intValue());

        assertNotNull(outObj);
        assertEquals("value", outObj.getString("key"));
    }

    public void test_adding_unsupported_object_types_results_in_exception() throws JSONException {
        try {
            jsonArray.put((Object) new java.util.Date());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void test_empty_state_and_null_detection_work_correctly() throws JSONException {
        assertTrue(jsonArray.isEmpty());
        jsonArray.put(JSONNull.getInstance());
        assertFalse(jsonArray.isEmpty());
        assertTrue(jsonArray.isNull(0));
    }
}
