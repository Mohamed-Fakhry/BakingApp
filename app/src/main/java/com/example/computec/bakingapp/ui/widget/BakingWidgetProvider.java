package com.example.computec.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.model.Recipe;
import com.example.computec.bakingapp.model.Step;
import com.example.computec.bakingapp.ui.recipe.RecipeActivity;

import java.util.ArrayList;

public class BakingWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager
            appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; ++i) {
            RemoteViews remoteViews = getRecipeStepsListRemoteViewS(context,
                    appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i],
                    remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private static RemoteViews getRecipeStepsListRemoteView(Context context,
                                                            int appWidgetId, Recipe recipe) {

        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.widget_layout);

        Intent serviceIntent = WidgetService.createIntent(context);

        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        serviceIntent.putExtra("recipeBundle", bundle);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(R.id.bakingListViewWidget,
                serviceIntent);

        Intent appIntent = new Intent(context, RecipeActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.empty_view, appPendingIntent);

        remoteViews.setEmptyView(R.id.bakingListViewWidget, R.id.empty_view);

        return remoteViews;
    }

    private RemoteViews getRecipeStepsListRemoteViewS(Context context,
                                                            int appWidgetId) {

        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.widget_layout);

        Intent appIntent = new Intent(context, RecipeActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.empty_view, appPendingIntent);

        remoteViews.setEmptyView(R.id.bakingListViewWidget, R.id.empty_view);

        return remoteViews;
    }

    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,
                                          Recipe recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, null);
            RemoteViews rv = getRecipeStepsListRemoteView(context, appWidgetId, recipe);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }
}
