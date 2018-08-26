package com.example.ed139.bakinghelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.ed139.bakinghelper.adapters.MasterListAdapter;
import com.example.ed139.bakinghelper.database.AppDatabase;
import com.example.ed139.bakinghelper.models.Recipes;
import com.example.ed139.bakinghelper.network.GetData;
import com.example.ed139.bakinghelper.network.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MasterListAdapter.ListItemClickListener {

    private RecyclerView mMainRecyclerView;
    private MasterListAdapter mMainAdapter;
    private RecyclerView.LayoutManager mMainLayoutManager;
    private List<Recipes> recipesList;
    public AppDatabase mDb;
    boolean mTwoPane;

    // The Idling Resource which will be null in production.
    @Nullable
    private MyIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link MyIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public MyIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new MyIdlingResource() {};
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());

        GetData data = Retrofit.getRetrofitInstance().create(GetData.class);
        Call<List<Recipes>> call = data.getAllRecipes();
        call.enqueue(new Callback<List<Recipes>>() {

            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {

                mMainRecyclerView = findViewById(R.id.recycler_view);

                mMainLayoutManager = new GridLayoutManager(MainActivity.this, 3);

                mMainAdapter = new MasterListAdapter(MainActivity.this, response.body(), MainActivity.this);

                mMainRecyclerView.setLayoutManager(mMainLayoutManager);

                mMainRecyclerView.setAdapter(mMainAdapter);
            }
            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        // Get the com.example.ed139.bakinghelper.MyIdlingResource instance
        getIdlingResource();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        // handled in adapter
    }
}
