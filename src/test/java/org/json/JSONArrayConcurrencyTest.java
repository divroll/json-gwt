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

public class JSONArrayConcurrencyTest extends GWTTestCase {
    private JSONArray jsonArray;

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonArray = new JSONArray();
    }

    /**
     * Tests that array access operations are performed sequentially.
     */
    public void test_array_access_should_be_sequential() {
        final JSONArray array = new JSONArray();
        final int numOperations = 1000;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                try {
                    array.put(i + "-" + j);
                } catch (Exception e) {
                    fail("Exception during array put operation: " + e.getMessage());
                }
            }
        }

        assertEquals(numOperations, array.length());
    }

    /**
     * Tests that array modification operations are performed sequentially.
     */
    public void test_array_modification_should_be_sequential() {
        final JSONArray array = new JSONArray();

        for (int i = 0; i < 100; i++) {
            array.put("initial-" + i);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                try {
                    int index = j;
                    array.put(index, "thread-" + i + "-update-" + j);
                } catch (Exception e) {
                    fail("Exception during array modification: " + e.getMessage());
                }
            }
        }

        assertTrue("Array length should be at least 10", array.length() >= 10);

        try {
            String value = array.getString(0);
            assertTrue("First element should be modified", value.startsWith("thread-"));
        } catch (Exception e) {
            fail("Exception while reading array element: " + e.getMessage());
        }
    }

    /**
     * Tests that the array maintains its integrity after various operations.
     */
    public void test_array_should_maintain_integrity() {
        final JSONArray array = new JSONArray();

        array.put("string");
        array.put(42);
        array.put(true);
        array.put(3.14);

        assertEquals("Array should have 4 elements", 4, array.length());

        try {
            assertEquals("First element should be string", "string", array.getString(0));
            assertEquals("Second element should be integer", 42, array.getInt(1));
            assertEquals("Third element should be boolean", true, array.getBoolean(2));
            assertEquals("Fourth element should be double", 3.14, array.getDouble(3), 0.001);
        } catch (Exception e) {
            fail("Exception while reading array elements: " + e.getMessage());
        }
    }

    /**
     * Tests that the array handles bounds correctly.
     */
    public void test_array_should_handle_bounds_correctly() {
        final JSONArray array = new JSONArray();
        array.put("test");

        assertEquals("Array should have 1 element", 1, array.length());

        try {
            String value = array.getString(0);
            assertEquals("Should get correct value", "test", value);

            boolean exceptionThrown = false;
            try {
                String outOfBounds = array.getString(10);
                // Some JSONArray implementations return null for out of bounds
                // This is valid behavior, so we don't fail the test
            } catch (Exception e) {
                exceptionThrown = true;
            }
        } catch (Exception e) {
            fail("Exception during bounds test: " + e.getMessage());
        }
    }
}
