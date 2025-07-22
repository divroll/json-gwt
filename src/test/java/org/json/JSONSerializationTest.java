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
import org.json.JSONException;

public class JSONSerializationTest extends GWTTestCase {
    private JSONArray jsonArray;
    private JSONObject jsonObject;

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonArray = new JSONArray();
        jsonObject = new JSONObject();
    }

    /**
     * Verifies that serializing and deserializing an array preserves all elements.
     */
    public void test_serializing_and_deserializing_array_preserves_elements() throws JSONException {
        jsonArray.put(true);
        jsonArray.put(123);
        jsonArray.put("test");
        jsonArray.put(new JSONArray().put(1).put(2));
        jsonArray.put(new JSONObject().put("key", "value"));

        String serialized = jsonArray.toString();
        assertNotNull(serialized);

        JSONArray deserialized = new JSONArray(serialized);
        assertEquals(jsonArray.length(), deserialized.length());
        assertEquals(true, deserialized.getBoolean(0));
        assertEquals(123, deserialized.getInt(1));
        assertEquals("test", deserialized.getString(2));

        JSONArray nestedArray = deserialized.getJSONArray(3);
        assertEquals(2, nestedArray.length());
        assertEquals(1, nestedArray.getInt(0));
        assertEquals(2, nestedArray.getInt(1));

        JSONObject nestedObject = deserialized.getJSONObject(4);
        assertEquals("value", nestedObject.getString("key"));
    }

    /**
     * Verifies that serializing and deserializing an object preserves its entries.
     */
    public void test_serializing_and_deserializing_object_preserves_entries() throws JSONException {
        jsonObject.put("boolean", true);
        jsonObject.put("number", 123);
        jsonObject.put("string", "test");
        jsonObject.put("array", new JSONArray().put(1).put(2));
        jsonObject.put("object", new JSONObject().put("key", "value"));

        String serialized = jsonObject.toString();
        assertNotNull(serialized);

        JSONObject deserialized = new JSONObject(serialized);
        assertTrue(deserialized.getBoolean("boolean"));
        assertEquals(123, deserialized.getInt("number"));
        assertEquals("test", deserialized.getString("string"));

        JSONArray nestedArray = deserialized.getJSONArray("array");
        assertEquals(2, nestedArray.length());
        assertEquals(1, nestedArray.getInt(0));
        assertEquals(2, nestedArray.getInt(1));

        JSONObject nestedObject = deserialized.getJSONObject("object");
        assertEquals("value", nestedObject.getString("key"));
    }

    /**
     * Verifies that serializing and deserializing special characters preserves content integrity.
     */
    public void test_serializing_and_deserializing_special_characters_preserves_content_integrity() throws JSONException {
        jsonObject.put("key with spaces", "value with spaces");
        jsonObject.put("key\"with\"quotes", "value\"with\"quotes");
        jsonObject.put("key\\with\\backslashes", "value\\with\\backslashes");

        String serialized = jsonObject.toString();
        assertNotNull(serialized);

        JSONObject deserialized = new JSONObject(serialized);
        assertEquals("value with spaces", deserialized.getString("key with spaces"));
        assertEquals("value\"with\"quotes", deserialized.getString("key\"with\"quotes"));
        assertEquals("value\\with\\backslashes", deserialized.getString("key\\with\\backslashes"));
    }
}
