// -------------------------------------------------------------------------------------------
// Copyright (C) 2016 Sony Interactive Entertainment Inc.
// Licensed under the MIT License. See the LICENSE file in the project root for license information.
// --------------------------------------------------------------------------------------------
package com.tkporter.fabrictwitterkit;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class FabricTwitterKitUtils {
    public static WritableMap jsonToWritableMap(String json) throws JSONException {
        return jsonToWritableMap(new JSONObject(json));
    }

    private static WritableMap jsonToWritableMap(final JSONObject jsonObject) throws JSONException {
        final WritableMap writableMap = Arguments.createMap();
        final Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            final String key = (String) iterator.next();
            final Object value = jsonObject.get(key);
            if (value instanceof Float || value instanceof Double) {
                writableMap.putDouble(key, jsonObject.getDouble(key));
            } else if (value instanceof Number) {
                writableMap.putInt(key, jsonObject.getInt(key));
            } else if (value instanceof String) {
                writableMap.putString(key, jsonObject.getString(key));
            } else if (value instanceof JSONObject) {
                writableMap.putMap(key, jsonToWritableMap(jsonObject.getJSONObject(key)));
            } else if (value instanceof JSONArray) {
                writableMap.putArray(key, jsonToWritableArray(jsonObject.getJSONArray(key)));
            } else if (value instanceof Boolean) {
                writableMap.putBoolean(key, jsonObject.getBoolean(key));
            } else if (value == JSONObject.NULL) {
                writableMap.putNull(key);
            }
        }
        return writableMap;
    }

    private static WritableArray jsonToWritableArray(final JSONArray jsonArray) throws JSONException {
        final WritableArray writableArray = Arguments.createArray();
        for (int i = 0; i < jsonArray.length(); i++) {

            final Object value = jsonArray.get(i);
            if (value instanceof Float || value instanceof Double) {
                writableArray.pushDouble(jsonArray.getDouble(i));
            } else if (value instanceof Number) {
                writableArray.pushInt(jsonArray.getInt(i));
            } else if (value instanceof String) {
                writableArray.pushString(jsonArray.getString(i));
            } else if (value instanceof Boolean) {
                writableArray.pushBoolean(jsonArray.getBoolean(i));
            } else if (value instanceof JSONObject) {
                writableArray.pushMap(jsonToWritableMap(jsonArray.getJSONObject(i)));
            } else if (value instanceof JSONArray) {
                writableArray.pushArray(jsonToWritableArray(jsonArray.getJSONArray(i)));
            } else if (value == JSONObject.NULL) {
                writableArray.pushNull();
            }
        }
        return writableArray;
    }
}
