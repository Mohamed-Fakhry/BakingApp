package com.example.computec.bakingapp.ui.recipedetails;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Recipe;
import com.example.computec.bakingapp.ui.base.BaseFragment;
import com.example.computec.bakingapp.ui.widget.BakingWidgetProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends BaseFragment {

    @BindView(R.id.recipeDetailsRV)
    RecyclerView recipeDetailsRV;

    static final String RECIPE_KEY = "recipe";

    private Recipe recipe;
    RecipeDetailsAdapter recpieDetailsAdapter;

    public RecipeDetailsFragment() {}

    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        getActivity().setTitle(recipe.getName());
        return view;
    }

    @Override
    protected void setUp(View view) {
        recipeDetailsRV.setLayoutManager(new LinearLayoutManager(getBaseActivity(),
                LinearLayoutManager.VERTICAL, false));
        ArrayList recipeDetails = new ArrayList();
        recipeDetails.addAll(recipe.getIngredients());
        recipeDetails.addAll(recipe.getSteps());
        recpieDetailsAdapter = new RecipeDetailsAdapter(recipeDetails);
        recipeDetailsRV.setAdapter(recpieDetailsAdapter);

        //test
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getBaseActivity());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getBaseActivity(), BakingWidgetProvider.class));

        BakingWidgetProvider.updatePlantWidgets(getBaseActivity(), appWidgetManager,recipe, appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.bakingListViewWidget);
    }
}
