package com.example.ed139.bakinghelper.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ed139.bakinghelper.MainActivity;
import com.example.ed139.bakinghelper.R;
import com.example.ed139.bakinghelper.RecipeDetailsActivity;
import com.example.ed139.bakinghelper.models.Recipes;
import com.example.ed139.bakinghelper.widget.UpdateWidget;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ViewHolder> implements Serializable {

    private final MainActivity mParentActivity;
    private Context mContext;
    private List<Recipes> mRecipeList;
    private ListItemClickListener mOnClickListener;
    private Recipes recipe;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MasterListAdapter(Context context, List<Recipes> recipesList, ListItemClickListener listener) {
        mContext = context;
        mRecipeList = recipesList;
        mOnClickListener = listener;
        mParentActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public MasterListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_master_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;
        TextView mName;
        TextView mServings;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.food_image);
            mName = itemView.findViewById(R.id.name_tv);
            mServings = itemView.findViewById(R.id.servings_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
            Recipes currentRecipe = mRecipeList.get(clickedPosition);

            // update in the widget... code with help from @Fer on slack
            UpdateWidget.startActionUpdateIngredients(mContext, currentRecipe.getName(), currentRecipe.getIngredients());

            Gson gson = new Gson();
            Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
            intent.putExtra("key", gson.toJson(currentRecipe));

            mContext.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MasterListAdapter.ViewHolder holder, int position) {

        recipe = mRecipeList.get(position);

        // set image
        String imagePath = recipe.getImage();
        if (!(imagePath.isEmpty())) {
            Picasso.get().load(imagePath).into(holder.mImageView);
        } else {
            // need a better placeholder...
            Picasso.get().load(R.drawable.doriangray).into(holder.mImageView);
        }

        // set Text on the TextViews
        holder.mName.setText(recipe.getName());
        holder.mServings.setText(recipe.getServings());
    }
}
