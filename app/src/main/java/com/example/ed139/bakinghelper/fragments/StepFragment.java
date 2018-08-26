package com.example.ed139.bakinghelper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ed139.bakinghelper.R;
import com.example.ed139.bakinghelper.adapters.StepsAdapter;
import com.example.ed139.bakinghelper.models.StepDeets;

import java.util.ArrayList;
import java.util.List;

public class StepFragment extends Fragment {

    public static final String STEPS_ITEM_ID = "steps_id";

    private View rootView;
    private StepsAdapter mStepsAdapter;
    private RecyclerView mRecyclerViewSteps;
    private RecyclerView.LayoutManager mLayoutManagerSteps;
    private List<StepDeets> stepsList;
    private boolean mTwoPane;
    private OnClickCallback callback;

    // with help from https://stackoverflow.com/questions/46587222/passing-a-listener-to-a-custom-fragment-in-android
    public interface OnClickCallback {
        void onClick(int index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnClickCallback) context;
    }

    // Mandatory empty constructor
    public StepFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mTwoPane = getArguments().getBoolean("two_pane");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Load the saved state (the list) if there is one
        if(savedInstanceState != null) {
            stepsList = savedInstanceState.getParcelableArrayList("steps_list");
        }

        rootView = inflater.inflate(R.layout.rv_steps, container, false);

        mRecyclerViewSteps = rootView.findViewById(R.id.steps_rv);

        mRecyclerViewSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(getId());
            }
        });

        mLayoutManagerSteps = new LinearLayoutManager(getContext());

        mStepsAdapter = new StepsAdapter(getContext(), stepsList, callback, mTwoPane);

        mRecyclerViewSteps.setLayoutManager(mLayoutManagerSteps);

        mRecyclerViewSteps.setAdapter(mStepsAdapter);

        // Return the root view
        return rootView;
    }

    public void setStepsList(List<StepDeets> stepsList) {
        this.stepsList = stepsList;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("steps_list", (ArrayList<? extends Parcelable>) stepsList);
    }
}
