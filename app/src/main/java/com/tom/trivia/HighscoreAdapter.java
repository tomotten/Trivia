package com.tom.trivia;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoreAdapter extends ArrayAdapter<Highscore> {
    private ArrayList<Highscore> highscores;

    public HighscoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> highscores) {
        super(context, resource, highscores);
        this.highscores = highscores;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore_item, parent, false);
        }
        Highscore currentScore = highscores.get(position);

        TextView name = convertView.findViewById(R.id.nameView);
        TextView date = convertView.findViewById(R.id.dateView);
        TextView score = convertView.findViewById(R.id.scoreView);
        TextView rank = convertView.findViewById(R.id.rankingView);

        name.setText(currentScore.getPlayerName());
        date.setText(currentScore.getDate());
        score.setText(Integer.toString(currentScore.getScore()));
        rank.setText(Integer.toString(position+1));

        return convertView;
    }
}
