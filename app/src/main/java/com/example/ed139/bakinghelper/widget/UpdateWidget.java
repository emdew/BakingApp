package com.example.ed139.bakinghelper.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.ed139.bakinghelper.models.IngredientDeets;

import java.util.List;

public class UpdateWidget extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "com.example.ed139.bakingapp.widget.action.update_widget";
    private static String sName;
    private static List<IngredientDeets> sIngredientsList;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * Used to name the worker thread, important only for debugging.
     * threw an illegal exception when I kept the auto-generated constructor
     */
    public UpdateWidget() {
        super("UpdateWidget");
    }

    public static void startActionUpdateIngredients(Context context, String name, List<IngredientDeets> ingredientsList){

        // update if there's new info
        if (name != null) {
            sName = name;
            sIngredientsList = ingredientsList;
        }

        // start the intent
        // was stopping here before I added the extra service to the manifest
        Intent intent = new Intent(context, UpdateWidget.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)){
                updateIngredientsWidget();
            }
        }
    }

    // method that updates the ingredients @Fer
    private void updateIngredientsWidget(){
        // get the widget manager and update with info from intent
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidget.class));
        IngredientsWidget.updateAllIngredientWidgets(this, appWidgetManager, appWidgetsIds, sName, sIngredientsList);
    }
}
