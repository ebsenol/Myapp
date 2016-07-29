package com.example.senolb.project.api_help;

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