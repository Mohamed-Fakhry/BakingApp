package com.example.computec.bakingapp.ui.recipe;

import com.example.computec.bakingapp.model.Recipe;
import com.example.computec.bakingapp.ui.base.MvpPresenter;
import com.example.computec.bakingapp.ui.base.MvpView;

import java.util.ArrayList;

public class RecipeContract {

    interface View extends MvpView {
        void showRecipes(ArrayList<Recipe> recipes);
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {
        void getRecipe();
    }
}
