package com.example.computec.bakingapp.ui.recipe;

import android.os.Bundle;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.ui.base.BaseActivity;

public class RecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecipeFragment.newInstance())
                    .commit();
    }

    @Override
    protected void setUp() {
    }
}
