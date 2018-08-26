package com.example.ed139.bakinghelper.network;

import com.example.ed139.bakinghelper.models.Recipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetData {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipes>> getAllRecipes();

}
