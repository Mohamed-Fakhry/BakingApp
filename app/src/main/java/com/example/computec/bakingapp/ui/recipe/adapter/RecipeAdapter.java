package com.example.computec.bakingapp.ui.recipe.adapter;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Recipe;
import com.example.computec.bakingapp.ui.base.BaseViewHolder;
import com.example.computec.bakingapp.ui.recipedetails.RecipeDetailsActivity;
import com.example.computec.bakingapp.ui.recipedetails.RecipeDetailsFragment;
import com.example.computec.bakingapp.utils.ImageUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class RecipeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private ArrayList<Recipe> recipes;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new RecipeVH(LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyRecipeVH(LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (recipes == null || recipes.size() == 0) ? VIEW_TYPE_EMPTY : VIEW_TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return (recipes == null || recipes.size() == 0) ? 1 : recipes.size();
    }

    public class RecipeVH extends BaseViewHolder {

        @BindView(R.id.recipeIV)
        ImageView recipeIV;
        @BindView(R.id.recipeTV)
        TextView recipeTV;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.servingTV)
        TextView servingTV;

        RecipeVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Recipe recipe = recipes.get(position);
            Log.d("test", recipe + "\n" + recipes.toString());
            recipeTV.setText(recipe.getName());

            itemView.setOnClickListener(view ->
                    itemView.getContext().startActivity(RecipeDetailsActivity
                            .startRecipeDetailsActivity(itemView.getContext(), recipe)));


            servingTV.setText(String.valueOf(recipe.getServings()));

            if (recipe.getImage() == null || recipe.getImage().isEmpty()) {
                String url = recipe.getSteps().get(recipe.getSteps().size() - 1).getVideoURL();
                try {
                    new Thread(() -> {
                        try {
                            Bitmap bitmap = ImageUtils.retriveVideoFrameFromVideo(url);

                            ((AppCompatActivity) itemView.getContext()).runOnUiThread(() -> {
                                recipeIV.setImageBitmap(bitmap);
                                progressBar.setVisibility(View.GONE);
                            });
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Glide.with(itemView.getContext())
                        .load(recipe.getImage())
                        .into(recipeIV);
            }
        }
    }

    class EmptyRecipeVH extends BaseViewHolder {
        EmptyRecipeVH(View itemView) {
            super(itemView);
        }
    }
}
