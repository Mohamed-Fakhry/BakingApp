package com.example.computec.bakingapp.ui.recipe;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Recipe;
import com.example.computec.bakingapp.ui.base.BaseFragment;
import com.example.computec.bakingapp.ui.recipe.adapter.RecipeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends BaseFragment implements RecipeContract.View {

    @BindView(R.id.recipeRV)
    RecyclerView recipeRV;

    RecipeContract.Presenter<RecipeFragment> presenter;
    RecipeAdapter recipeAdapter;

    public RecipeFragment() {}

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        super.setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    protected void setUp(View view) {
        presenter = new RecipePresenter<>();
        presenter.onAttach(this);
        presenter.getRecipe();
        recipeRV.setLayoutManager(new LinearLayoutManager(getBaseActivity(),
                LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showRecipes(ArrayList<Recipe> recipes) {
        recipeAdapter = new RecipeAdapter(recipes);
        recipeRV.setAdapter(recipeAdapter);
    }
}
