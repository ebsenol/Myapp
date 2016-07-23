package com.example.senolb.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Response;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Page1 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
       //Glide.with(getApplicationContext()).load("https://media.giphy.com/media/LyJ6KPlrFdKnK/giphy.gif").into(imageView);
        //Glide.with(this).load("https://media4.giphy.com/media/Y5GVgQZCluUWQ/giphy.gif").into(imageView);
       // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        //Glide.with(this).load("https://media4.giphy.com/media/Y5GVgQZCluUWQ/giphy.gif").into(imageViewTarget);
    }
    public void request(View view) throws IOException {

        TextView text2= (TextView)findViewById(R.id.first_text);

       // ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
      //  Call<Downsized> myDownsized = service.getDownsized("dc6zaTOxFJmzC","json","funny","1");
        //Downsized a= myDownsized.execute().body();
        //text2.setText(a.getUrl());
        EditText name = (EditText) findViewById(R.id.first_number);
        String keyword = name.getText().toString();
        ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
        Call<JsonResponse> myDownsized = service.getDownsized("dc6zaTOxFJmzC", "json", keyword, "1");

        myDownsized.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.isSuccessful()) {
                    for (Data data : response.body().getDataList()) {
                        //System.out.println(data.getImages().getDownsized().getUrl());
                        TextView text2 = (TextView) findViewById(R.id.first_text);
                        //Downsized dw = response.body();
                        text2.setText(data.getImages().getDownsized().getUrl());
                        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
                        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                        Glide.with(getApplicationContext()).load(data.getImages().getDownsized().getUrl()).into(imageViewTarget);
                       // Glide.load(data.getImages().getDownsized().getUrl()).into(imageView);
                       // Glide.with(getApplicationContext()).load(data.getImages().getDownsized().getUrl()).into(imageView);

                    }

                } else {
                    //unsuccessful response
                }
            }
         @Override
         public void onFailure(Call<JsonResponse> call, Throwable t) {
             Log.d("Error", t.getMessage());
             TextView text2 = (TextView) findViewById(R.id.first_text);
             text2.setText(t.getMessage());
         }
     });
     //  myGif.execute();
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        //String message = editText.getText().toString();
        startActivity(intent);
    }
}
