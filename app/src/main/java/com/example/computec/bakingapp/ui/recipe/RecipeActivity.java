package com.example.computec.bakingapp.ui.recipe;

import android.os.Bundle;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.ui.base.BaseActivity;

public class RecipeActivity extends BaseActivity implements RecipeFragment.IdelCallBack {

    private boolean syncFinished = false;

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

    public boolean isSyncFinished() {
        return syncFinished;
    }

    public void setSyncFinished(Boolean syncFinished) {
        this.syncFinished = syncFinished;
    }
}
