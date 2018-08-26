package com.example.ed139.bakinghelper.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.exoplayer2.C;
import com.google.gson.annotations.SerializedName;

// POJO for ingredients details
public class IngredientDeets implements Parcelable {

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    public IngredientDeets(String quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected IngredientDeets(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IngredientDeets> CREATOR = new Creator<IngredientDeets>() {
        @Override
        public IngredientDeets createFromParcel(Parcel in) {
            return new IngredientDeets(in);
        }

        @Override
        public IngredientDeets[] newArray(int size) {
            return new IngredientDeets[size];
        }
    };

    public String getQuantity(){
        return quantity;
    }

    public void setQuantity(String quantity){
        this.quantity = quantity;
    }

    public String getMeasure(){
        return measure;
    }

    public void setMeasure(String measure){
        this.measure = measure;
    }

    public String getIngredient(){
        return ingredient;
    }

    public void setIngredient(String ingredient){
        this.ingredient = ingredient;
    }
}