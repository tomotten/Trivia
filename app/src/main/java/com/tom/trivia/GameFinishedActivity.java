package com.tom.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameFinishedActivity extends AppCompatActivity implements HighscorePostRequest.Callback{
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);
        Intent intent = getIntent();
        this.score = intent.getIntExtra("score", -1);

        TextView wp = findViewById(R.id.wellPlayedView);
        if (score < 5) {
            wp.setText("Too bad, try again!");
        }
        else {
            wp.setText("Well played!");
        }
        TextView scoreView = findViewById(R.id.displayScoreView);
        scoreView.setText("Your score was: "+Integer.toString(score));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void onPlayAgainClick(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void onShowHighscoreClick(View view) {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }

    public void onSubmitClick(View view) {
        EditText nameView = findViewById(R.id.nameEditText);
        String name = nameView.getText().toString();
        if (name.length() < 2) {
            Toast.makeText(this,"Please enter your name!",Toast.LENGTH_LONG).show();
            return;
        }
        if (name.length() > 22) {
            Toast.makeText(this,"Entered name is too long!",Toast.LENGTH_LONG).show();
            return;
        }

            Highscore newScore = new Highscore(name, score);

        HighscorePostRequest hr = new HighscorePostRequest(this);
        hr.PostHighscore(this, newScore);
        Toast.makeText(this, "Uploading your score..", Toast.LENGTH_LONG).show();

    }

    @Override
    public void gotPostError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    // wait for post to succeed before going to scoreboard, so new input is shown
    public void gotPostConfirmation() {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }
}
