package com.example.ed139.bakinghelper.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StepDeets implements Parcelable {

    @SerializedName("description")
    private String description;

    @SerializedName("videoURL")
    private String url;

    @SerializedName("thumbnailURL")
    private String thumbnailUrl;

    public StepDeets(String description){
        this.description = description;
    }

    protected StepDeets(Parcel in) {
        description = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StepDeets> CREATOR = new Creator<StepDeets>() {
        @Override
        public StepDeets createFromParcel(Parcel in) {
            return new StepDeets(in);
        }

        @Override
        public StepDeets[] newArray(int size) {
            return new StepDeets[size];
        }
    };

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl){
        this.thumbnailUrl = thumbnailUrl;
    }

}

