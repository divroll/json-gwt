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

public class JSONArrayComplexTypesTest extends GWTTestCase {

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
     * Tests that nested arrays and objects maintain their structure and data.
     *
     * @throws JSONException if there is an error in the JSON operations
     */
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
        assertEquals(1, outArr.getInt(0));

        assertNotNull(outObj);
        assertEquals("value", outObj.getString("key"));
    }
}
