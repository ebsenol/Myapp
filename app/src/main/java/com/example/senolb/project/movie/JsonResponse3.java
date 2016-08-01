package com.example.senolb.project.movie;

import com.example.senolb.project.normalmodegif.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by senolb on 28/07/16.
 */

public class JsonResponse3 {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }
}