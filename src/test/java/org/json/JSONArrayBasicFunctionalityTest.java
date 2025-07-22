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

public class JSONArrayBasicFunctionalityTest extends GWTTestCase {

    private JSONArray jsonArray;

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
        assertTrue(array.length() == 0);
        assertEquals(0, array.length());
    }

    public void test_array_can_be_initialized_from_gwt_json_array() {
        com.google.gwt.json.client.JSONArray gwtArray = new com.google.gwt.json.client.JSONArray();
        gwtArray.set(0, new com.google.gwt.json.client.JSONString("test"));

        JSONArray array = new JSONArray(gwtArray);
        assertNotNull(array);
        assertEquals(1, array.length());
    }
}
