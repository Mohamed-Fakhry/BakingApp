package com.example.computec.bakingapp.ui.recipe;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Recipe;
import com.example.computec.bakingapp.ui.base.BaseFragment;
import com.example.computec.bakingapp.ui.recipe.adapter.RecipeAdapter;
import com.example.computec.bakingapp.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends BaseFragment implements RecipeContract.View {

    @BindView(R.id.recipeRV)
    RecyclerView recipeRV;

    RecipeContract.Presenter<RecipeFragment> presenter;
    RecipeAdapter recipeAdapter;

    IdelCallBack callBack;

    public RecipeFragment() {}

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        super.setUnBinder(ButterKnife.bind(this, view));
        setRetainInstance(true);
        return view;
    }

    @Override
    protected void setUp(View view) {
        recipeRV.setLayoutManager(new GridLayoutManager(getBaseActivity(), numberOfColumns()));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter = new RecipePresenter<>();
        presenter.onAttach(this);
        presenter.getRecipe();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (IdelCallBack) context;
    }

    @Override
    public void showRecipes(ArrayList<Recipe> recipes) {
        recipeAdapter = new RecipeAdapter(recipes);
        recipeRV.setAdapter(recipeAdapter);
        callBack.setSyncFinished(true);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = ViewUtils.dpToPx(250);
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 1) return 1;
        return nColumns;
    }

    interface IdelCallBack {

        void setSyncFinished(Boolean syncFinished);
    }
}
