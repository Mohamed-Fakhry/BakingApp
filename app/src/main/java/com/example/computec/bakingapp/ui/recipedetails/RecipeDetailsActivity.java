package com.example.computec.bakingapp.ui.recipedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Recipe;
import com.example.computec.bakingapp.ui.base.BaseActivity;

import static com.example.computec.bakingapp.ui.recipedetails.RecipeDetailsFragment.RECIPE_KEY;

public class RecipeDetailsActivity extends BaseActivity {

    public static Intent startRecipeDetailsActivity(Context context, Recipe recipe){
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        Log.d("test 1", recipe.toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_KEY, recipe);
        intent.putExtra(RECIPE_KEY, bundle);
        Log.d("test 2", recipe.toString());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getBundleExtra(RECIPE_KEY);
            Recipe recipe = bundle.getParcelable(RECIPE_KEY);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecipeDetailsFragment.newInstance(recipe))
                    .commit();
        }
    }

    @Override
    protected void setUp() {

    }
}
