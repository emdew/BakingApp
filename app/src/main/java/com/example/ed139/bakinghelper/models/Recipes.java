package com.example.ed139.bakinghelper.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "recipes")
public class Recipes implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("servings")
    private String servings;

    @SerializedName("ingredients")
    private List<IngredientDeets> ingredients;

    @SerializedName("steps")
    private List<StepDeets> steps;

    public Recipes(int id, String name, String image, String servings, List<IngredientDeets> ingredients, List<StepDeets> steps){
        this.id = id;
        this.name = name;
        this.image = image;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @Ignore
    public Recipes(String name, String image, String servings, List<IngredientDeets> ingredients, List<StepDeets> steps){
        this.name = name;
        this.image = image;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    protected Recipes(Parcel in) {
        name = in.readString();
        image = in.readString();
        servings = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(servings);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getServings(){
        return servings;
    }

    public void setServings(String servings){
        this.servings = servings;
    }

    public List<IngredientDeets> getIngredients(){
        return ingredients;
    }

    public void setIngredients(List<IngredientDeets> ingredients){
        this.ingredients = ingredients;
    }

    public List<StepDeets> getSteps(){
        return steps;
    }

    public void setSteps(List<StepDeets> steps){
        this.steps = steps;
    }
}
