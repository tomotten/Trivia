package com.tom.trivia;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CategoryRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;
    private Callback activity;

    public interface Callback {
        void gotCategories(Map<String, Integer> categories);
        void gotCategoriesError(String message);
    }

    public CategoryRequest(Context context) {
        this.context = context;
    }

    public void getCategories(Callback activity) {
        this.activity = activity;

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://opentdb.com/api_category.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotCategoriesError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        Map<String, Integer> categories = new HashMap<String, Integer>();
        categories.put("Random", 0);
        try {
            JSONArray responseArray = response.getJSONArray("trivia_categories");

            // add all results to Hashmap
            for(int i=0; i < responseArray.length(); i++) {
                JSONObject trivia_category = responseArray.getJSONObject(i);
                String name = trivia_category.getString("name");
                int id = trivia_category.getInt("id");
                categories.put(name, id);
            }
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        activity.gotCategories(categories);
    }
}
