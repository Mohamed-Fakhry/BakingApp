package com.example.computec.bakingapp.ui.recipe;

import android.support.test.espresso.IdlingResource;

import com.example.computec.bakingapp.ui.recipe.RecipeActivity;

/**
 * Created by Mohamed Fakhry on 23/11/2017.
 */

public class RecipeIdlingResource implements IdlingResource {

    private RecipeActivity activity;
    private ResourceCallback callback;

    public RecipeIdlingResource(RecipeActivity activity) {
        this.activity = activity;
    }

    @Override
    public String getName() {
        return "MainActivityIdleName";
    }

    @Override
    public boolean isIdleNow() {
        Boolean idle = isIdle();
        if (idle) callback.onTransitionToIdle();
        return idle;
    }

    public boolean isIdle() {
        return activity != null && callback != null && activity.isSyncFinished();
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback resourceCallback) {
        this.callback = resourceCallback;
    }
}
