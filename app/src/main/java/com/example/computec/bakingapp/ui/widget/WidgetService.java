package com.example.computec.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by Mohamed Fakhry on 17/11/2017.
 */

public class WidgetService extends RemoteViewsService {

    static Intent createIntent(Context context) {
        return new Intent(context, WidgetService.class);
    }

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new ListProvider(this.getApplicationContext(), intent));
    }
}

