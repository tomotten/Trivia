package com.tom.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements CategoryRequest.Callback{
    private Map<String, Integer> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        CategoryRequest cr = new CategoryRequest(this);
        cr.getCategories(this);
    }

    @Override
    public void gotCategories(Map<String, Integer> categories) {
        ArrayList<String> category_list = new ArrayList<String>(categories.keySet());
        this.categories = categories;
        Spinner cat_spin = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, category_list);
        cat_spin.setAdapter(adapter);
    }

    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void onStartGameClick(View view) {
        Spinner cat_spin = findViewById(R.id.categorySpinner);
        int id = categories.get(cat_spin.getSelectedItem().toString());
//        Intent intent = new Intent(this, );
//        intent.putExtra("category_id", id);
//        startActivity(intent);
    }
}
