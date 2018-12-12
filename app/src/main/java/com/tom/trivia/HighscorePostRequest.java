package com.tom.trivia;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HighscorePostRequest implements Response.Listener<String>, Response.ErrorListener {
    private Context context;
    private Callback activity;

    public interface Callback {
        void gotPostError(String message);
        void gotPostConfirmation();
    }

    public HighscorePostRequest(Context context) {
        this.context = context;
    }

    public void PostHighscore(Callback activity, final Highscore highscore) {
        this.activity = activity;
        String url = "https://ide50-netto156.cs50.io:8080/list";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                this ,this) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", highscore.getPlayerName());
                params.put("score", Integer.toString(highscore.getScore()));
                params.put("date", highscore.getDate());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onResponse(String response) {
        activity.gotPostConfirmation();
    }
}
