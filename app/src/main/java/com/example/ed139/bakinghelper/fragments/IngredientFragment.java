package com.example.ed139.bakinghelper.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ed139.bakinghelper.R;
import com.example.ed139.bakinghelper.adapters.IngredientsAdapter;
import com.example.ed139.bakinghelper.models.IngredientDeets;

import java.util.ArrayList;
import java.util.List;

public class IngredientFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String INGREDIENTS_ITEM_ID = "ingred_id";

    private View rootView;
    private IngredientsAdapter mIngredientsAdapter;
    private RecyclerView mRecyclerViewIngredients;
    private RecyclerView.LayoutManager mLayoutManagerIngredients;
    private List<IngredientDeets> ingredientsList;

    // Mandatory empty constructor
    public IngredientFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Inflates the LinearLayout of all steps
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Load the saved state (the list of images and list index) if there is one
        if(savedInstanceState != null) {
            ingredientsList = savedInstanceState.getParcelableArrayList("ingredients_list");
        }

        rootView = inflater.inflate(R.layout.rv_ingredients, container, false);

        mRecyclerViewIngredients = rootView.findViewById(R.id.ingredients_rv);

        mLayoutManagerIngredients = new LinearLayoutManager(getContext());

        mIngredientsAdapter = new IngredientsAdapter(getContext(), ingredientsList);

        mRecyclerViewIngredients.setLayoutManager(mLayoutManagerIngredients);

        mRecyclerViewIngredients.setAdapter(mIngredientsAdapter);

        // Return the root view
        return rootView;
    }

    public void setIngredientsList(List<IngredientDeets> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ingredients_list", (ArrayList<? extends Parcelable>) ingredientsList);
    }
}
