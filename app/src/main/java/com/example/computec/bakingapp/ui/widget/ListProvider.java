package com.example.computec.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Recipe;
import com.example.computec.bakingapp.model.Step;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;
    private Recipe recipe;
    private Intent intent;

    ListProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        Bundle bundle = intent.getBundleExtra("recipeBundle");
        recipe = bundle.getParcelable("recipe");
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_row);

        remoteView.setTextViewText(R.id.content, recipe.getIngredients().get(position).getIngredient());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
