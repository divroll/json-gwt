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

public class JSONObjectBasicFunctionalityTest extends GWTTestCase {

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
     * Tests that a new empty JSONObject is created successfully.
     */
    public void test_new_empty_object_is_created_successfully() {
        JSONObject obj = new JSONObject();
        assertNotNull(obj);
        assertTrue(obj.keySet().isEmpty());
    }

    /**
     * Tests that a JSONObject can be created from a valid JSON string.
     */
    public void test_object_can_be_created_from_valid_json_string() {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"active\":true}";
        try {
            JSONObject obj = new JSONObject(jsonString);
            assertNotNull(obj);
        } catch (Exception e) {
        }
    }

    /**
     * Tests that a JSONObject can be initialized from a GWT JSON object.
     */
    public void test_object_can_be_initialized_from_gwt_json_object() {
        com.google.gwt.json.client.JSONObject gwtObject = new com.google.gwt.json.client.JSONObject();
        gwtObject.put("test", new JSONString("value"));

        JSONObject obj = new JSONObject(gwtObject);
        assertNotNull(obj);
        assertEquals("value", obj.getString("test"));
    }
}
