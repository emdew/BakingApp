package com.example.ed139.bakinghelper.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.ed139.bakinghelper.R;
import com.example.ed139.bakinghelper.database.AppDatabase;
import com.example.ed139.bakinghelper.models.IngredientDeets;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    public static final String LOG_TAG = "UpdateWidget";
    private static String mName;
    public static List<IngredientDeets> mIngredientsList;
    public static AppDatabase mDb;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String name, List<IngredientDeets> ingredientsList) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        // how to get the last recipe saved?
//        mDb = AppDatabase.getInstance(context);
//        mDb.recipesDao().loadRecipes();

        // loading arraylists and passing them without database with help from @Fer

        mName = name;
        mIngredientsList = ingredientsList;

        if (name == null) {
            // set null views
        } else {

            // ingredients head is the name of the current recipe
            views.setTextViewText(R.id.ingredients_header, name);

            // info I want to pass to the RemoteViewsFactory
            Intent intentRVService = new Intent(context, WidgetService.class);
            intentRVService.putExtra("ingredients", ingredientsList.size());

            // create array lists for the ingredient details
            ArrayList<String> quantity = new ArrayList<>();
            ArrayList<String> measure = new ArrayList<>();
            ArrayList<String> ingredient = new ArrayList<>();

            // loop through the quantity, measure, and ingredient lists
            for (int i = 0; i < ingredientsList.size(); i++) {
                quantity.add(String.valueOf(ingredientsList.get(i).getQuantity()));
                measure.add(ingredientsList.get(i).getMeasure());
                ingredient.add(ingredientsList.get(i).getIngredient());
            }

            // put the string lists on the service intent
            intentRVService.putStringArrayListExtra("quantity", quantity);
            intentRVService.putStringArrayListExtra("measure", measure);
            intentRVService.putStringArrayListExtra("ingredient", ingredient);

            // does it matter that I used widget_list here instead of the simple_list?
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
            views.setRemoteAdapter(R.id.widget_list, intentRVService);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // created a new update method with help from @Fer in order to pass in the name and list
    public static void updateAllIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                                  int[] appWidgetIds, String name, List<IngredientDeets> ingredientsList){
        for (int appWidgetId: appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId, name, ingredientsList);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        if (mIngredientsList != null) {
            Log.d(LOG_TAG, "Should update with " + mIngredientsList.size());
        }
        UpdateWidget.startActionUpdateIngredients(context, mName, mIngredientsList);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

