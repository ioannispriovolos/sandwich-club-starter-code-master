package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) { // Original code modified from https://stackoverflow.com/questions/28736419/parsing-json-array-and-object-in-android

        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject sandwichObject = sandwichJson.getJSONObject("name");
            String mainName = sandwichObject.getString("mainName");
            JSONArray alsoKnownAsArray = sandwichObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            String description = sandwichJson.getString("description");
            String image = sandwichJson.getString("image");
            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
