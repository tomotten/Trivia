package com.tom.trivia;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class QuestionsRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;
    private Callback activity;
    private int amount;

    public interface Callback {
        void gotQuestions(ArrayList<Question> questions);
        void gotQuestionError(String message);
    }

    public QuestionsRequest(Context context) {
        this.context = context;
    }

    public void getQuestions(Callback activity, int amount, int category_id, String difficulty, String type){
        this.activity = activity;
        this.amount = amount;
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://opentdb.com/api.php?amount="+amount;
        if (category_id != 0) {
            url += "&category="+Integer.toString(category_id);
        }
        if (difficulty.length() > 3) {
            url += "&difficulty="+difficulty;
        }
        if (type.length() > 4) {
            url+= "&type="+type;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<Question> question_list = new ArrayList<Question>();

        try {
            JSONArray responseArray = response.getJSONArray("results");

            // add all results to Hashmap
            for(int i=0; i < responseArray.length(); i++) {
                Question q = new Question();
                JSONObject trivia_question = responseArray.getJSONObject(i);
                q.setCategory(trivia_question.getString("category"));
                q.setType(trivia_question.getString("type"));
                q.setDifficulty(trivia_question.getString("difficulty"));

                // fill in all quotes and clean question from other symbols
                String question_string = trivia_question.getString("question").replaceAll("&quot;", "'");
                q.setQuestion(question_string.replaceAll("&#[0-9]+;", ""));

                q.setCorrectAnswer(trivia_question.getString("correct_answer"));
                JSONArray incorrect_answers = trivia_question.getJSONArray("incorrect_answers");
                ArrayList<String> answers = new ArrayList<String>();
                answers.add(trivia_question.getString("correct_answer"));
                for(int j=0; j < incorrect_answers.length(); j++) {
                    answers.add(incorrect_answers.optString(j));
                }
                q.setAnswers(answers);
                question_list.add(q);
            }
        }

        catch (Exception e) {
            activity.gotQuestionError(e.getMessage());
        }
        // check if we got enough questions from response, if not throw error message
        if (question_list.size() != amount) {
            activity.gotQuestionError("Not enough questions found with these restrictions!");
        }
        else {
            activity.gotQuestions(question_list);
        }
    }
}
