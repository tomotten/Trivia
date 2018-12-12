package com.tom.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements CategoryRequest.Callback{
    private Map<String, Integer> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        CategoryRequest cr = new CategoryRequest(this);
        cr.getCategories(this);
        setTitle("Trivia : Menu");
    }

    @Override
    public void gotCategories(Map<String, Integer> categories) {
        ArrayList<String> category_list = new ArrayList<String>(categories.keySet());
        this.categories = categories;

        Spinner cat_spin = findViewById(R.id.categorySpinner);
        Spinner diff_spin = findViewById(R.id.difficultySpinner);
        Spinner type_spin = findViewById(R.id.typeSpinner);

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, category_list);
        cat_spin.setAdapter(cat_adapter);

        ArrayList<String> difficulties = new ArrayList<String>();
        difficulties.add("All");
        difficulties.add("easy");
        difficulties.add("medium");
        difficulties.add("hard");
        ArrayAdapter<String> diff_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, difficulties);
        diff_spin.setAdapter(diff_adapter);

        ArrayList<String> types = new ArrayList<String>();
        types.add("All");
        types.add("Multiple choise");
        types.add("True/False");

        ArrayAdapter<String> type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        type_spin.setAdapter(type_adapter);

        // set startvalues
        cat_spin.setSelection(2);
        diff_spin.setSelection(0);
        type_spin.setSelection(0);

    }

    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void onStartGameClick(View view) {
        Spinner cat_spin = findViewById(R.id.categorySpinner);
        int id = categories.get(cat_spin.getSelectedItem().toString());

        Spinner diff_spin = findViewById(R.id.difficultySpinner);
        String difficulty = diff_spin.getSelectedItem().toString();

        // get type and map to correct syntax
        Map<String, String> types_map = new HashMap<String, String>();
        types_map.put("Multiple choise", "multiple");
        types_map.put("True/False", "boolean");
        types_map.put("All", "All");
        Spinner type_spin = findViewById(R.id.typeSpinner);
        String type = types_map.get(type_spin.getSelectedItem().toString());


        Intent intent = new Intent(MenuActivity.this, GamePlayActivity.class);
        intent.putExtra("category_id", id);
        intent.putExtra("type", type);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    @Override
    // act as if homeButton is pressed
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onShowScoreClick(View view) {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }
}
