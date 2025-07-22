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

public class JSONArrayEdgeCasesTest extends GWTTestCase {
    private JSONArray jsonArray;

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonArray = new JSONArray();
    }

    public void testNegativeIndices() throws JSONException {
        try {
            jsonArray.get(-1);
            fail("Expected JSONException for negative index");
        } catch (JSONException e) {
            assertTrue(true); // Expected exception
        }

        try {
            jsonArray.put(-1, "test");
            fail("Expected JSONException for negative index");
        } catch (JSONException e) {
            assertTrue(true); // Expected exception
        }
    }

    public void testOutOfBoundIndices() throws JSONException {
        try {
            jsonArray.get(0); // Empty array
            fail("Expected JSONException for out-of-bound index");
        } catch (JSONException e) {
            assertTrue(true); // Expected exception
        }

        jsonArray.put("test");
        try {
            jsonArray.get(1); // Index out of bounds
            fail("Expected JSONException for out-of-bound index");
        } catch (JSONException e) {
            assertTrue(true); // Expected exception
        }
    }

    public void testLargeArrayHandling() throws JSONException {
        int largeSize = 10000;
        for (int i = 0; i < largeSize; i++) {
            jsonArray.put(i);
        }
        assertEquals(largeSize, jsonArray.length());
        assertEquals(0, jsonArray.getInt(0));
        assertEquals(largeSize - 1, jsonArray.getInt(largeSize - 1));

        // Test performance by accessing elements
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < largeSize; i++) {
            assertEquals(i, jsonArray.getInt(i));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Large array access time: " + (endTime - startTime) + " ms");
    }

    public void testSpecialCharactersInStrings() throws JSONException {
        jsonArray.put("special\\characters\"and\tnewlines\n");
        String retrieved = jsonArray.getString(0);
        assertEquals("special\\characters\"and\tnewlines\n", retrieved);
    }

    public void testVeryLargeNumbers() throws JSONException {
        double largeDouble = Double.MAX_VALUE;
        jsonArray.put(largeDouble);
        assertEquals(largeDouble, jsonArray.getDouble(0), 0.0);

        long largeLong = Long.MAX_VALUE;
        jsonArray.put(largeLong);
        assertEquals(largeLong, jsonArray.getLong(1));
    }
}
