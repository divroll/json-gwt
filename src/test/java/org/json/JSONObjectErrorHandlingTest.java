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

public class JSONObjectErrorHandlingTest extends GWTTestCase {

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
     * Tests that enum retrieval works correctly for string values.
     *
     * @throws JSONException if there is an error in the JSON operations
     */
    public void test_enum_retrieval_works_correctly_for_string_values() throws JSONException {
        jsonObject.put("e", "VALUE1");
        JSONObjectErrorHandlingTest.TestEnum valueEnum = jsonObject.getEnum(JSONObjectErrorHandlingTest.TestEnum.class, "e");
        assertNotNull(valueEnum);
        assertEquals("VALUE1", valueEnum.toString());
    }

    /**
     * Tests that JSON null values are detected correctly.
     *
     * @throws JSONException if there is an error in the JSON operations
     */
    public void test_json_null_values_are_detected_correctly() throws JSONException {
        jsonObject.put("n", JSONNull.getInstance());
        assertTrue(jsonObject.get("n") == null);
    }

    enum TestEnum {
        VALUE1, VALUE2
    }
}
