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

public class JSONArrayErrorHandlingTest extends GWTTestCase {

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

    public void test_unsupported_objects_throw_illegal_argument_exception() throws JSONException {
        try {
            jsonArray.put((Object) new java.util.Date());
            fail("Expected IllegalArgumentException");
        } catch (JSONException e) {
            // expected
        }
    }

    public void test_enum_retrieval_throws_exception_for_string_values() throws JSONException {
        jsonArray.put("VALUE1");
        TestEnum valueEnum = jsonArray.getEnum(TestEnum.class, 0);
        assertNotNull(valueEnum);
        assertEquals("VALUE1", valueEnum.toString());
    }

    public void test_adding_unsupported_object_types_results_in_exception() throws JSONException {
        try {
            jsonArray.put((Object) new java.util.Date());
            fail("Expected IllegalArgumentException");
        } catch (org.json.JSONException e) {
            // expected
            assertEquals("Object type java.util.Date is not supported.", e.getMessage());
        }
    }

    public void test_empty_state_and_null_detection_work_correctly() throws JSONException {
        assertTrue(jsonArray.length() == 0);
        jsonArray.put(JSONNull.getInstance());
        assertFalse(jsonArray.length() == 0);
        assertTrue(jsonArray.isNull(0));
    }
}
