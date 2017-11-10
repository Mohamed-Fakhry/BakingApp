package com.example.computec.bakingapp.ui.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.computec.bakingapp.R;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, RecipeFragment.newInstance())
                .commit();
    }
}
