package org.json;

import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JSONTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    public void testJSONObject() {
        // create a new JSONObject instance
        JSONObject obj = new JSONObject();

        // Test case 1: test put methods works correctly.
        obj.put("key", "value");
        String value = obj.getString("key");
        assertEquals("value", value);

        // Test case 2: test get methods works correctly.
        obj = new JSONObject("{\"key1\": \"value1\", \"key2\": 123}");
        String value1 = obj.getString("key1");
        int value2 = obj.getInt("key2");
        assertEquals("value1", value1);
        assertEquals(123, value2);

        // Add more test cases as necessary.
    }

    public void testJSONArray() {
        JSONArray array = new JSONArray();
        array.put("value");

        String value = array.getString(0);
        assertEquals("value", value);

        com.google.gwt.json.client.JSONArray gwtArray = new com.google.gwt.json.client.JSONArray();
        gwtArray.set(0, new JSONString("value"));

        array = new JSONArray(gwtArray);
        String value1 = array.getString(0);
        assertEquals("value", value1);
        // possibly add an integer value before trying to read it
        // array.put(123);
        // int value2 = array.getInt(1);
        // assertEquals(123, value2);

        // Add more test cases as necessary.
    }
}