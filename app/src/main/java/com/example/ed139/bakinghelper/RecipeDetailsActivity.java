package com.example.ed139.bakinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ed139.bakinghelper.fragments.IngredientFragment;
import com.example.ed139.bakinghelper.fragments.StepFragment;
import com.example.ed139.bakinghelper.fragments.VideoFragment;
import com.example.ed139.bakinghelper.models.IngredientDeets;
import com.example.ed139.bakinghelper.models.Recipes;
import com.example.ed139.bakinghelper.models.StepDeets;
import com.google.gson.Gson;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements StepFragment.OnClickCallback {

    private List<IngredientDeets> ingredientsList;
    private List<StepDeets> stepsList;
    StepDeets steps;
    Intent mIntent;
    private boolean mTwoPane;
    String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.video_container) != null) {
            mTwoPane = true;
        }

        Gson gson = new Gson();
        mIntent = getIntent();
        String stringCurrentRecipe = mIntent.getStringExtra("key");
        Recipes currentRecipe = gson.fromJson(stringCurrentRecipe, Recipes.class);

        ingredientsList = currentRecipe.getIngredients();
        stepsList = currentRecipe.getSteps();

        Bundle bundle = new Bundle();
        bundle.putBoolean("two_pane", mTwoPane);

        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setIngredientsList(ingredientsList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.ingredient_container, ingredientFragment)
                .commit();

        StepFragment stepFragment = new StepFragment();
        stepFragment.setStepsList(stepsList);
        stepFragment.setArguments(bundle);
        stepFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.step_container, stepFragment)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(int index) {
        if (mTwoPane) {

            steps = stepsList.get(index);
            videoPath = steps.getUrl();

            Bundle bundle = new Bundle();
            bundle.putString("video_path", videoPath);

            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_container, videoFragment)
                    .commit();
        }
    }
}
