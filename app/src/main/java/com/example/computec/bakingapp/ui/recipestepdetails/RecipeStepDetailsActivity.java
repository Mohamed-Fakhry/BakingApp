package com.example.computec.bakingapp.ui.recipestepdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Step;
import com.example.computec.bakingapp.ui.base.BaseActivity;

import static com.example.computec.bakingapp.ui.recipestepdetails.RecipeStepDetailsFragment.STEP_KEY;

public class RecipeStepDetailsActivity extends BaseActivity {

    public static Intent startRecipeStepDetailsActivity(Context context,Step step) {
        Intent intent = new Intent(context, RecipeStepDetailsActivity.class);
        intent.putExtra(STEP_KEY, step);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);
        Step step;
        if (savedInstanceState == null) {
            step = getIntent().getParcelableExtra(STEP_KEY);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecipeStepDetailsFragment.newInstance(step))
                    .commit();
        }

    }

    @Override
    protected void setUp() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
