package com.example.senolb.project.easymodegif;

/**
 * Created by senolb on 29/07/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Downsized {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("size")
    @Expose
    private String size;
// getter and setter methods below

    public String getUrl(){
        return url;
    }
    public String getWidth(){
        return width;
    }
    public String getHeight(){
        return height;
    }
}