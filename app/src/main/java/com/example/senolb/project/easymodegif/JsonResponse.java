package com.example.senolb.project.easymodegif;

/**
 * Created by senolb on 29/07/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JsonResponse {

    @SerializedName("data")
    @Expose
    private List<Data> dataList = new ArrayList<>();

    public List<Data> getDataList() {
        return dataList;
    }
}