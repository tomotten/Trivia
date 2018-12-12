package com.tom.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements HighscoreRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        HighscoreRequest hr = new HighscoreRequest(this);
        hr.getHighscores(this);
        setTitle("Trivia : Highscores");
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {
        HighscoreAdapter adapter = new HighscoreAdapter(getApplicationContext(), R.layout.highscore_item, highscores);
        ListView list = findViewById(R.id.highscoreListView);
        list.setAdapter(adapter);
    }

    @Override
    public void gotHighscoreError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
