package com.example.ed139.bakinghelper.database;

import android.arch.persistence.room.TypeConverter;

import com.example.ed139.bakinghelper.models.IngredientDeets;
import com.example.ed139.bakinghelper.models.StepDeets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ConvertersSteps {

    // with help from https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9

    static Gson gson = new Gson();

    @TypeConverter
    public static List<StepDeets> stringToStepsList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type stepsList = new TypeToken<List<StepDeets>>() {}.getType();
        return gson.fromJson(data, stepsList);
    }

    @TypeConverter
    public static String stepsListToString(List<StepDeets> stepsList) {
        return gson.toJson(stepsList);
    }
}
