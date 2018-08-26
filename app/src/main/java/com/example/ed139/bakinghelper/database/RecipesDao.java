package com.example.ed139.bakinghelper.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ed139.bakinghelper.models.IngredientDeets;
import com.example.ed139.bakinghelper.models.Recipes;

import java.util.List;

@Dao
public interface RecipesDao {

    @Query("SELECT * FROM recipes ORDER BY id")
    List<Recipes> loadRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipes recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipes recipe);

    @Delete
    void deleteRecipe(Recipes recipe);

    @Query("SELECT * FROM recipes WHERE id = :id")
    Recipes loadRecipeById(int id);
}
