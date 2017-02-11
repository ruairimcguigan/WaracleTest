package com.waracle.androidtest.model.parser;

import com.waracle.androidtest.model.Cake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruairi on 10/02/2017.
 */

public class JsonParser {

    public static List<Cake> parseCakeFeed(String url) {

        // Parse json to object
        // if parsing result to array

        // this is all that is needed to transform the json formatted content
        // to an untyped array of objects
        try {
            JSONArray jsonArray = new JSONArray(url);

            // We want a list of specifically typed objects
            List<Cake> cakeList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Cake cake = new Cake();
                cake.setTitle(jsonObject.getString("title"));
                cake.setDesc(jsonObject.getString("desc"));
                cake.setImage(jsonObject.getString("image"));

                cakeList.add(cake);
            }
            return cakeList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
