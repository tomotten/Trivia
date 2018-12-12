package com.tom.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {
    private Context context;
    private Callback activity;

    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoreError(String message);
    }

    public HighscoreRequest(Context context){
        this.context = context;
    }

    public void getHighscores(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://ide50-netto156.cs50.io:8080/list";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,this, this);
        queue.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighscoreError(error.getMessage());
    }


    @Override
    // response for GET
    public void onResponse(JSONArray response) {
        ArrayList<Highscore> highscores = new ArrayList<Highscore>();
        try {
            for (int i =0; i < response.length(); i++) {
                JSONObject highscore = response.getJSONObject(i);
                String name = highscore.getString("name");
                String date = highscore.getString("date");
                int score = Integer.valueOf(highscore.getString("score"));
                Highscore hs = new Highscore(name, score, date);
                highscores.add(hs);
            }
        }
        catch (Exception e){
            activity.gotHighscoreError(e.getMessage());
        }
        Collections.sort(highscores);
        activity.gotHighscores(highscores);
    }
}
