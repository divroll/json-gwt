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

public class JSONObjectEdgeCasesTest extends GWTTestCase {
    private JSONObject jsonObject;

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonObject = new JSONObject();
    }

    public void testEmptyKeys() throws JSONException {
        try {
            jsonObject.put("", "value");
            assertTrue(jsonObject.has(""));
            assertEquals("value", jsonObject.getString(""));
        } catch (Exception e) {
            fail("Empty keys should be allowed");
        }
    }

    public void testDuplicateKeys() throws JSONException {
        jsonObject.put("key", "value1");
        jsonObject.put("key", "value2");
        assertEquals("value2", jsonObject.getString("key")); // Last value should be retained
    }

    public void testSpecialCharactersInKeys() throws JSONException {
        jsonObject.put("special\\key\"with\tnewlines\n", "value");
        assertEquals("value", jsonObject.getString("special\\key\"with\tnewlines\n"));
    }

    public void testSpecialCharactersInValues() throws JSONException {
        jsonObject.put("key", "special\\characters\"and\tnewlines\n");
        String retrieved = jsonObject.getString("key");
        assertEquals("special\\characters\"and\tnewlines\n", retrieved);
    }

    public void testVeryLargeNumbers() throws JSONException {
        double largeDouble = Double.MAX_VALUE;
        jsonObject.put("largeDouble", largeDouble);
        assertEquals(largeDouble, jsonObject.getDouble("largeDouble"), 0.0);

        long largeLong = Long.MAX_VALUE;
        jsonObject.put("largeLong", largeLong);
        assertEquals(largeLong, jsonObject.getLong("largeLong"));
    }

    public void testLargeObjectHandling() throws JSONException {
        int largeSize = 10000;
        for (int i = 0; i < largeSize; i++) {
            jsonObject.put("key-" + i, "value-" + i);
        }
        assertEquals(largeSize, jsonObject.keySet().size());

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < largeSize; i++) {
            assertEquals("value-" + i, jsonObject.getString("key-" + i));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Large object access time: " + (endTime - startTime) + " ms");
    }
}
