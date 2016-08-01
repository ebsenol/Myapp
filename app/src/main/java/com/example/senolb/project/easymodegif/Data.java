package com.example.senolb.project.easymodegif;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("images")
    @Expose
    private Image images;

    public Image getImages() {
        return images;
    }
}