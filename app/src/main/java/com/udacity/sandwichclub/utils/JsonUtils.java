package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    // the items are already in json!
    //  parse the json into Java
//    then use the parsed json java in populateUI in DetailActivity.java


        public static Sandwich parseSandwichJson(String json) {


            try {

                // root json object: instantiate a parser that helps to get the values inside JSON object
                JSONObject sandwichDetailRootObj = new JSONObject(json);


//                get name object from root object as name is an object inside the main json object
               JSONObject name =  sandwichDetailRootObj.getJSONObject("name");

               // extract name
               String mainName = name.optString("mainName");

               // extract place of origin
               String placeOfOrigin =  sandwichDetailRootObj.getString("placeOfOrigin");

               // extract description
               String description =  sandwichDetailRootObj.getString("description");

               // extract image
               String image =  sandwichDetailRootObj.getString("image");


                // Extract aka details into array from the name object which is inside the main object
                JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
                ArrayList<String> alsoKnownAsList = new ArrayList<>();

                if(alsoKnownAs.length() == 0) {

                    alsoKnownAs.put("N/A");

                    }
                for (int i = 0; i < alsoKnownAs.length(); i++) {


                    alsoKnownAsList.add(alsoKnownAs.optString(i));
                }


                // extract ingredients
                JSONArray ingredients = sandwichDetailRootObj.getJSONArray("ingredients");
                ArrayList<String> ingredientsList = new ArrayList<>();

                for(int i = 0;i < ingredients.length(); i++) {

                    ingredientsList.add(ingredients.optString(i));


                }


                return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description,image,ingredientsList);
            }
            catch(JSONException e) {
                e.printStackTrace();
                Log.e("JsonUtils", "The parsing didn't work");
            }



        return null;
    }
}
