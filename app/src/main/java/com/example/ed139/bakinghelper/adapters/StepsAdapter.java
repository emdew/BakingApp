package com.example.ed139.bakinghelper.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ed139.bakinghelper.R;
import com.example.ed139.bakinghelper.VideoActivity;
import com.example.ed139.bakinghelper.fragments.StepFragment.OnClickCallback;
import com.example.ed139.bakinghelper.models.StepDeets;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> implements Serializable {

    private Context mContext;
    private List<StepDeets> mStepsList;
    private OnClickCallback mOnStepsListClick;
    private StepDeets currentStep;
    private boolean mTwoPane;

    // constructor
    public StepsAdapter(Context context, List<StepDeets> steps, OnClickCallback listener, boolean twoPane) {
        mContext = context;
        mStepsList = steps;
        mOnStepsListClick = listener;
        mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_steps, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mDescTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mDescTv = itemView.findViewById(R.id.desc_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            mOnStepsListClick.onClick(clickedPosition);
            currentStep = mStepsList.get(clickedPosition);

            Bundle bundle = new Bundle();
            bundle.putParcelable("current_steps", currentStep);

            if (!mTwoPane) {
                Gson gson = new Gson();

                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra("key", gson.toJson(currentStep));

                mContext.startActivity(intent);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, int position) {
        currentStep = mStepsList.get(position);
        holder.mDescTv.setText(currentStep.getDescription());
    }
}
