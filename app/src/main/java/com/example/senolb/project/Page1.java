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
      //  ImageView imageView = (ImageView) findViewById(R.id.imageView1);
     //   Glide.with(this).load("https://media.giphy.com/media/LyJ6KPlrFdKnK/giphy.gif").into(imageView);
    }
    public void request(View view) throws IOException {

        TextView text2= (TextView)findViewById(R.id.first_text);

        ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
        Call<Downsized> myDownsized = service.getDownsized("dc6zaTOxFJmzC","json","funny","1");
        //Downsized a= myDownsized.execute().body();
        //text2.setText(a.getUrl());
     myDownsized.enqueue(new Callback<Downsized>() {
         @Override
         public void onResponse(Call<Downsized> call, Response<Downsized> response) {
             if (response.isSuccessful()) {
                 //Downsized DownsizedResponse =
                 //String data = DownsizedResponse.getUrl();
                 //String id = DownsizedResponse.getId();
                 TextView text2 = (TextView) findViewById(R.id.first_text);
                 //testDownsized = response.body();
                 //  testDownsized.setUrl(response.body().getUrl());
                 //   text2.setText("response");

                 //        text2.setText("response.isSuccess");
                 //     if (response.body()!=null)
                Downsized dw = response.body();
                 text2.setText(dw.getUrl());
                 // text2.setText(response.body().getUrl());
                 //if(response.body().getUrl()==null)
                 // text2.setText("getUrl returns null");
                 //text2.setText(testDownsized.getUrl()+"aa");
                 //  adapter = new DataAdapter(data);
                 //  recyclerView.setAdapter(adapter);
               /*
               TextView text2= (TextView)findViewById(R.id.first_text);
               text2.setText(data);
               data = DownsizedResponse.getSlug();
               text2.setText(data);*/
                 //  data = DownsizedResponse.getSlug();*/
             } else {
                 //unsuccessful response
             }
         }
         @Override
         public void onFailure(Call<Downsized> call, Throwable t) {
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
