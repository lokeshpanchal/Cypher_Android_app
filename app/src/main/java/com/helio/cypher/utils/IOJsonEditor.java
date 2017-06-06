package com.helio.cypher.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class IOJsonEditor {


    public static String jsonArrayToString(JSONArray array) {
        return array.toString();
    }

    public static JSONArray stringToJSONArray(String array) {

        JSONArray temp = null;

        try {
            temp = new JSONArray(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public static List<String> makeArray(JSONArray jsonArray) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                temp.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }
}
