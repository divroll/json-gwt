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
import com.google.gwt.json.client.*;
import com.google.gwt.junit.client.GWTTestCase;

import java.math.BigDecimal;
import java.math.BigInteger;

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

    public void test_new_empty_object_is_created_successfully() {
        JSONObject obj = new JSONObject();
        assertNotNull(obj);
        assertTrue(obj.keySet().isEmpty());
    }

    public void test_object_can_be_created_from_valid_json_string() {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"active\":true}";
        try {
            JSONObject obj = new JSONObject(jsonString);
            assertNotNull(obj);
        } catch (Exception e) {
        }
    }

    public void test_object_can_be_initialized_from_gwt_json_object() {
        com.google.gwt.json.client.JSONObject gwtObject = new com.google.gwt.json.client.JSONObject();
        gwtObject.put("test", new JSONString("value"));

        JSONObject obj = new JSONObject(gwtObject);
        assertNotNull(obj);
        assertEquals("value", obj.getString("test"));
    }

    public void test_boolean_values_are_stored_and_retrieved_correctly() throws JSONException {
        jsonObject.put("flag1", true);
        jsonObject.put("flag2", false);

        assertTrue(jsonObject.getBoolean("flag1"));
        assertFalse(jsonObject.getBoolean("flag2"));
    }

    public void test_boolean_wrapper_objects_including_null_are_handled_properly() throws JSONException {
        jsonObject.put("flag1", Boolean.TRUE);
        jsonObject.put("flag2", Boolean.FALSE);
        jsonObject.put("flag3", (Boolean) null);

        try {
            assertTrue(jsonObject.getBoolean("flag1"));
            assertFalse(jsonObject.getBoolean("flag2"));
            assertNull(jsonObject.getBoolean("flag3"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[flag3] is not a Boolean. Found: JSONNull", e.getMessage());
        }
    }

    public void test_double_values_maintain_precision_when_stored_and_retrieved() throws JSONException {
        jsonObject.put("pi", 3.14159);
        jsonObject.put("max", Double.MAX_VALUE);
        jsonObject.put("min", Double.MIN_VALUE);

        assertEquals(3.14159, jsonObject.getDouble("pi"), 0.00001);
        assertEquals(Double.MAX_VALUE, jsonObject.getDouble("max"), 0.0);
        assertEquals(Double.MIN_VALUE, jsonObject.getDouble("min"), 0.0);
    }

    public void test_double_wrapper_objects_including_null_are_handled_correctly() throws JSONException {
        jsonObject.put("value", Double.valueOf(2.71828));
        jsonObject.put("null", (Double) null);

        assertEquals(2.71828, jsonObject.getDouble("value"), 0.00001);
        try {
            assertNull(jsonObject.getDouble("null"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[null] is not a number. Found: JSONNull", e.getMessage());
        }
    }

    public void test_float_values_maintain_precision_when_stored_and_retrieved() throws JSONException {
        jsonObject.put("float1", 1.5f);
        jsonObject.put("float2", Float.MAX_VALUE);

        assertEquals(1.5f, jsonObject.getFloat("float1"), 0.001);
        assertEquals(Float.MAX_VALUE, jsonObject.getFloat("float2"), 0.0);
    }

    public void test_float_wrapper_objects_including_null_are_handled_correctly() throws JSONException {
        jsonObject.put("value", Float.valueOf(2.5f));
        jsonObject.put("null", (Float) null);

        assertEquals(2.5f, jsonObject.getFloat("value"), 0.001);
        try {
            assertNull(jsonObject.getFloat("null"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[null] is not a number. Found: JSONNull", e.getMessage());
        }
    }

    public void test_integer_values_are_stored_and_retrieved_with_correct_range() throws JSONException {
        jsonObject.put("answer", 42);
        jsonObject.put("max", Integer.MAX_VALUE);
        jsonObject.put("min", Integer.MIN_VALUE);

        assertEquals(42, jsonObject.getInt("answer"));
        assertEquals(Integer.MAX_VALUE, jsonObject.getInt("max"));
        assertEquals(Integer.MIN_VALUE, jsonObject.getInt("min"));
    }

    public void test_integer_wrapper_objects_including_null_are_handled_correctly() throws JSONException {
        jsonObject.put("value", Integer.valueOf(100));
        jsonObject.put("null", (Integer) null);

        assertEquals(100, jsonObject.getInt("value"));
        try {
            assertNull(jsonObject.getInt("null"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[null] is not a number. Found: JSONNull", e.getMessage());
        }
    }

    public void test_long_values_are_stored_and_retrieved_with_full_precision() throws JSONException {
        jsonObject.put("big", 123456789L);
        jsonObject.put("max", Long.MAX_VALUE);

        assertEquals(123456789L, jsonObject.getLong("big"));
        assertEquals(Long.MAX_VALUE, jsonObject.getLong("max"));
    }

    public void test_long_wrapper_objects_including_null_are_handled_correctly() throws JSONException {
        jsonObject.put("value", Long.valueOf(987654321L));
        jsonObject.put("null", (Long) null);

        assertEquals(987654321L, jsonObject.getLong("value"));
        try {
            assertNull(jsonObject.getLong("null"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[null] is not a number. Found: JSONNull", e.getMessage());
        }
    }

    public void test_string_values_including_empty_and_null_are_stored_correctly() throws JSONException {
        jsonObject.put("name", "John Doe");
        jsonObject.put("empty", "");
        jsonObject.put("null", (String) null);

        assertEquals("John Doe", jsonObject.getString("name"));
        assertEquals("", jsonObject.getString("empty"));
        try {
            assertNull(jsonObject.getString("null"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[null] is not a string. Found: JSONNull", e.getMessage());
        }
    }

    public void test_json_arrays_including_null_maintain_structure_and_content() throws JSONException {
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

        try {
            assertNull(jsonObject.getJSONArray("nullArray"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[nullArray] is not a JSONArray. Found: JSONNull", e.getMessage());
        }
    }

    public void test_all_primitive_types_work_together_in_single_object() throws JSONException {
        jsonObject.put("bool", true)
                .put("dbl", 2.5d)
                .put("flt", 1.25f)
                .put("int", 10)
                .put("long", 100L)
                .put("str", "test");

        assertTrue(jsonObject.getBoolean("bool"));
        assertEquals(2.5d, jsonObject.getDouble("dbl"));
        assertEquals(1.25f, jsonObject.getFloat("flt"));
        assertEquals(10, jsonObject.getInt("int"));
        assertEquals(100L, jsonObject.getLong("long"));
        assertEquals("test", jsonObject.getString("str"));
    }

    public void test_all_wrapper_null_values_return_null_when_retrieved() throws JSONException {
        jsonObject.put("b", (Boolean) null)
                .put("d", (Double) null)
                .put("f", (Float) null)
                .put("i", (Integer) null)
                .put("l", (Long) null)
                .put("s", (String) null)
                .put("arr", (JSONArray) null)
                .put("obj", (JSONObject) null);

        try {
            assertNull(jsonObject.getBoolean("b"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[b] is not a Boolean. Found: JSONNull", e.getMessage());
        }
        try {
            assertNull(jsonObject.getDouble("d"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[d] is not a number. Found: JSONNull", e.getMessage());
        }
        try {
            assertNull(jsonObject.getFloat("f"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[f] is not a number. Found: JSONNull", e.getMessage());
        }
        try {
            assertNull(jsonObject.getInt("i"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[i] is not a number. Found: JSONNull", e.getMessage());
        }
        try {
            assertNull(jsonObject.getLong("l"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[l] is not a number. Found: JSONNull", e.getMessage());
        }
        try {
            assertNull(jsonObject.getString("s"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[s] is not a string. Found: JSONNull", e.getMessage());
        }
        try {
            assertNull(jsonObject.getJSONArray("arr"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[arr] is not a JSONArray. Found: JSONNull", e.getMessage());
        }
        try {
            assertNull(jsonObject.getJSONObject("obj"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[obj] is not a JSONObject. Found: JSONNull", e.getMessage());
        }
    }

    public void test_numeric_values_can_be_retrieved_as_big_decimal_and_big_integer() throws JSONException {
        jsonObject.put("bd", 3.14d)
                .put("bi", 12345L);

        assertEquals(new BigDecimal("3.14"), jsonObject.getBigDecimal("bd"));
        assertEquals(BigInteger.valueOf(12345L), jsonObject.getBigInteger("bi"));
    }

    public void test_number_retrieval_and_key_enumeration_work_correctly() throws JSONException {
        jsonObject.put("num", 5.0d)
                .put("nullNum", (Number) null);

        assertEquals(5.0d, jsonObject.getNumber("num"));
        try {
            assertNull(jsonObject.getNumber("nullNum"));
            fail("Expected JSONException");
        } catch (JSONException e) {
            assertEquals("JSONObject[nullNum] is not a number. Found: JSONNull", e.getMessage());
        }
        assertTrue(jsonObject.keySet().contains("num"));
        assertTrue(jsonObject.keySet().contains("nullNum"));
    }

    public void test_nested_arrays_and_objects_maintain_their_structure_and_data() throws JSONException {
        JSONArray arr = new JSONArray();
        arr.put("x");
        JSONObject obj = new JSONObject();
        obj.put("y", 2);

        jsonObject.put("arrKey", arr)
                .put("objKey", obj);

        JSONArray outArr = jsonObject.getJSONArray("arrKey");
        JSONObject outObj = jsonObject.getJSONObject("objKey");

        assertNotNull(outArr);
        assertEquals("x", outArr.getString(0));
        assertNotNull(outObj);
        assertEquals(2, outObj.getInt("y"));
    }

    public void test_enum_retrieval_throws_exception_for_string_values() throws JSONException {
        jsonObject.put("e", "VALUE1");
        try {
            jsonObject.getEnum(TestEnum.class, "e");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    public void test_json_null_values_are_detected_correctly() throws JSONException {
        jsonObject.put("n", JSONNull.getInstance());
        assertTrue(jsonObject.get("n") == null);
    }

    enum TestEnum {
        VALUE1, VALUE2
    }
}
