package com.example.ed139.bakinghelper.database;

import android.arch.persistence.room.TypeConverter;

import com.example.ed139.bakinghelper.models.IngredientDeets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {

    // with help from https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9

    static Gson gson = new Gson();

    @TypeConverter
    public static List<IngredientDeets> stringToIngredientsList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type ingredientsList = new TypeToken<List<IngredientDeets>>() {}.getType();
        return gson.fromJson(data, ingredientsList);
    }

    @TypeConverter
    public static String ingredientsListToString(List<IngredientDeets> ingredientsList) {
        return gson.toJson(ingredientsList);
    }
}
