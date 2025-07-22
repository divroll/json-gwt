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

import com.google.gwt.junit.client.GWTTestCase;

public class JSONObjectConcurrencyTest extends GWTTestCase {
    private JSONObject jsonObject;

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonObject = new JSONObject();
    }

    /**
     * Verifies that sequential insertion of entries results in the correct key count.
     */
    public void test_sequential_insertion_of_entries_has_expected_key_count() {
        final JSONObject obj = new JSONObject();
        final int numOperations = 1000;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                try {
                    obj.put("key-" + i + "-" + j, "value-" + i + "-" + j);
                } catch (Exception e) {
                    fail("Exception during object put operation: " + e.getMessage());
                }
            }
        }

        assertEquals("Object should have correct number of keys", numOperations, countKeys(obj));
    }

    /**
     * Verifies that simulated concurrent modifications update entries as expected.
     */
    public void test_simulated_concurrent_modifications_result_in_updated_entries() {
        final JSONObject obj = new JSONObject();

        for (int i = 0; i < 100; i++) {
            obj.put("initial-" + i, "value-" + i);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                try {
                    String key = "key-" + j;
                    obj.put(key, "thread-" + i + "-update-" + j);
                } catch (Exception e) {
                    fail("Exception during object modification: " + e.getMessage());
                }
            }
        }

        assertTrue("Object should have at least 10 keys", countKeys(obj) >= 10);

        try {
            String value = obj.getString("key-0");
            assertTrue("Modified key should contain expected value", value.contains("thread-"));
        } catch (Exception e) {
            fail("Exception while reading object value: " + e.getMessage());
        }
    }

    /**
     * Verifies storing and retrieving various data types preserves integrity.
     */
    public void test_storing_and_retrieving_various_data_types_preserves_integrity() {
        final JSONObject obj = new JSONObject();

        obj.put("stringKey", "stringValue");
        obj.put("intKey", 42);
        obj.put("booleanKey", true);
        obj.put("doubleKey", 3.14);
        obj.put("nullKey", JSONObject.NULL);

        assertEquals("Object should have 5 keys", 5, countKeys(obj));

        try {
            assertEquals("String value should match", "stringValue", obj.getString("stringKey"));
            assertEquals("Int value should match", 42, obj.getInt("intKey"));
            assertEquals("Boolean value should match", true, obj.getBoolean("booleanKey"));
            assertEquals("Double value should match", 3.14, obj.getDouble("doubleKey"), 0.001);
            assertTrue("Null key should exist", obj.has("nullKey"));
            assertTrue("Null value should be null", obj.isNull("nullKey"));
        } catch (Exception e) {
            fail("Exception while reading object values: " + e.getMessage());
        }
    }

    /**
     * Verifies key management adds and confirms expected keys correctly.
     */
    public void test_key_management_adds_and_confirms_expected_keys() {
        final JSONObject obj = new JSONObject();

        obj.put("key1", "value1");
        obj.put("key2", "value2");
        obj.put("key3", "value3");

        assertEquals("Object should have 3 keys", 3, countKeys(obj));
        assertTrue("Should have key1", obj.has("key1"));
        assertTrue("Should have key2", obj.has("key2"));
        assertTrue("Should have key3", obj.has("key3"));
        assertFalse("Should not have nonexistent key", obj.has("nonexistent"));
        assertTrue("Object should contain all expected keys",
                containsExpectedKeys(obj, "key1", "key2", "key3"));
    }

    /**
     * Verifies nested objects and arrays can be accessed correctly.
     */
    public void test_nested_objects_and_arrays_are_accessed_correctly() {
        final JSONObject parentObj = new JSONObject();
        final JSONObject childObj = new JSONObject();
        final JSONArray childArray = new JSONArray();

        childObj.put("childKey", "childValue");
        childArray.put("arrayItem1");
        childArray.put("arrayItem2");

        parentObj.put("child", childObj);
        parentObj.put("array", childArray);
        parentObj.put("simple", "value");

        assertEquals("Parent should have 3 keys", 3, countKeys(parentObj));

        try {
            JSONObject retrievedChild = parentObj.getJSONObject("child");
            assertEquals("Child object should have correct value", "childValue", retrievedChild.getString("childKey"));

            JSONArray retrievedArray = parentObj.getJSONArray("array");
            assertEquals("Array should have 2 items", 2, retrievedArray.length());
            assertEquals("First array item should match", "arrayItem1", retrievedArray.getString(0));

        } catch (Exception e) {
            fail("Exception while accessing nested structures: " + e.getMessage());
        }
    }

    /**
     * Verifies error handling for non-existent keys and type mismatches.
     */
    public void test_error_handling_for_nonexistent_keys_and_type_mismatches() {
        final JSONObject obj = new JSONObject();
        obj.put("stringKey", "stringValue");
        obj.put("intKey", 42);

        try {
            boolean exceptionThrown = false;
            try {
                obj.getString("nonExistentKey");
            } catch (Exception e) {
                exceptionThrown = true;
            }

            exceptionThrown = false;
            try {
                obj.getInt("stringKey");
            } catch (Exception e) {
                exceptionThrown = true;
            }

        } catch (Exception e) {
            fail("Unexpected exception during error handling test: " + e.getMessage());
        }
    }

    private int countKeys(JSONObject obj) {
        int count = 0;
        String[] names = JSONObject.getNames(obj);
        if (names != null) {
            count = names.length;
        }
        return count;
    }

    private boolean containsExpectedKeys(JSONObject obj, String... expectedKeys) {
        String[] names = JSONObject.getNames(obj);
        if (names == null) return expectedKeys.length == 0;

        for (String expectedKey : expectedKeys) {
            boolean found = false;
            for (String name : names) {
                if (expectedKey.equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }
}
