package com.example.ed139.bakinghelper.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ed139.bakinghelper.R;
import com.example.ed139.bakinghelper.models.IngredientDeets;
import com.example.ed139.bakinghelper.models.Recipes;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private Context mContext;
    private List<IngredientDeets> mIngredientsList;
    private IngredientDeets currentIngredient;

    public IngredientsAdapter(Context context, List<IngredientDeets> ingredients) {
        mContext = context;
        mIngredientsList = ingredients;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_ingredients, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mQuantity;
        TextView mMeasure;
        TextView mIngredient;

        public ViewHolder(View itemView) {
            super(itemView);
            mQuantity = itemView.findViewById(R.id.quantity_tv);
            mMeasure = itemView.findViewById(R.id.measure_tv);
            mIngredient = itemView.findViewById(R.id.ingredient_tv);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        currentIngredient = mIngredientsList.get(position);
        holder.mQuantity.setText(currentIngredient.getQuantity());
        holder.mMeasure.setText(currentIngredient.getMeasure());
        holder.mIngredient.setText(currentIngredient.getIngredient());
    }
}
