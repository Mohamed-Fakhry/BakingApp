package com.example.computec.bakingapp.ui.recipedetails;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Ingredient;
import com.example.computec.bakingapp.model.Step;
import com.example.computec.bakingapp.ui.base.BaseViewHolder;
import com.example.computec.bakingapp.ui.recipestepdetails.RecipeStepDetailsFragment;
import com.example.computec.bakingapp.utils.ImageUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_INGREDIENTS = 0;
    private static final int VIEW_TYPE_STEPS = 1;

    private ArrayList recipeDetails;

    RecipeDetailsAdapter(ArrayList recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_STEPS:
                return new RecipeDetailVH(LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.item_recipe_detail_step, parent, false));
            case VIEW_TYPE_INGREDIENTS:
            default:
                return new RecipeDetailVH(LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.item_detail_intgredient, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        return (recipeDetails.get(position) instanceof Step) ? VIEW_TYPE_STEPS : VIEW_TYPE_INGREDIENTS;
    }

    @Override
    public int getItemCount() {
        return recipeDetails.size();
    }

    class RecipeDetailVH extends BaseViewHolder {

        @Nullable
        @BindView(R.id.ingredientTV)
        TextView ingredientTV;
        @Nullable
        @BindView(R.id.quantityTV)
        TextView quantityTV;

        @Nullable
        @BindView(R.id.videoIV)
        ImageView videoIV;
        @Nullable
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @Nullable
        @BindView(R.id.videoTV)
        TextView videoTV;


        RecipeDetailVH(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Object recipeDetail = recipeDetails.get(position);
            if (recipeDetail instanceof Step)
                bindStepDetail((Step) recipeDetail);
            else
                bindIngredientDetail((Ingredient) recipeDetail);
        }

        private void bindIngredientDetail(Ingredient ingredient) {
            assert ingredientTV != null;
            ingredientTV.setText(ingredient.getIngredient());
            assert quantityTV != null;
            quantityTV.setText(ingredient.getQuantity() + " " + ingredient.getMeasure());
        }

        private void bindStepDetail(Step step) {
            assert videoTV != null;
            videoTV.setText(step.getShortDescription());
            String url = step.getVideoURL();
            itemView.setOnClickListener(view -> {
                    if(((AppCompatActivity)itemView.getContext()).findViewById(R.id.videoContainer) != null) {
                        ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.videoContainer, RecipeStepDetailsFragment.newInstance(step))
                                .commit();

                        ((TextView)((AppCompatActivity)itemView.getContext())
                                .findViewById(R.id.stepTV)).setText(step.getDescription());
                    } else {
                        ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, RecipeStepDetailsFragment.newInstance(step))
                                .addToBackStack(null)
                                .commit();
                    }});
            try {
                new Thread(() -> {
                    try {
                        Bitmap bitmap = ImageUtils.retriveVideoFrameFromVideo(url);

                        ((AppCompatActivity) itemView.getContext()).runOnUiThread(() -> {
                            assert videoIV != null;
                            videoIV.setImageBitmap(bitmap);
                            assert progressBar != null;
                            progressBar.setVisibility(View.GONE);
                        });
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
