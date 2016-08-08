package com.example.senolb.project;

import java.util.ArrayList;

/**
 * Created by senolb on 08/08/16.
 */
public class GlobalData {
    public static ArrayList<String> passingUrls = new ArrayList<>();

    public static void addToPassingList(String url) {
        passingUrls.add(url);
    }

    public static void removeFromPassingList (String url) {
        passingUrls.remove(url);
    }

    public static void removeFromPassingList (int index){
        passingUrls.remove(index);
    }

    public static ArrayList<String> getList(){
        return passingUrls;
    }
}