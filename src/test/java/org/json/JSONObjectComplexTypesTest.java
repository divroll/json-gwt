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

public class JSONObjectComplexTypesTest extends GWTTestCase {

    private JSONObject jsonObject;

    @Override
    public String getModuleName() {
        return "org.JSON";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        jsonObject = new JSONObject();
    }

    public void test_nested_arrays_and_objects_maintain_their_structure_and_data() throws JSONException {
        JSONArray arr = new JSONArray();
        arr.put("x");
        JSONObject obj = new JSONObject();
        obj.put("y", 2);

        jsonObject.put("arrKey", arr)
                .put("objKey", obj);

        JSONArray outArr = jsonObject.getJSONArray("arrKey");
        JSONObject outObj = jsonObject.getJSONObject("objKey");

        assertNotNull(outArr);
        assertEquals("x", outArr.getString(0));
        assertNotNull(outObj);
        assertEquals(2, outObj.getInt("y"));
    }
}
