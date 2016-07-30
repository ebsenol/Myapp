package com.example.senolb.project.easy_mode;

/**
 * Created by senolb on 29/07/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("downsized")
    @Expose
    private Downsized downsized;

    public Downsized getDownsized() {
        return downsized;
    }
}