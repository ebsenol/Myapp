package com.example.senolb.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import retrofit2.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;

public class Page1 extends AppCompatActivity {

    public String url="a";
    public String url2="b";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
       //Glide.with(getApplicationContext()).load("https://media.giphy.com/media/LyJ6KPlrFdKnK/giphy.gif").into(imageView);
        //Glide.with(this).load("https://media4.giphy.com/media/Y5GVgQZCluUWQ/giphy.gif").into(imageView);
        TextView text2 = (TextView) findViewById(R.id.first_text);
        text2.setText(url);
    }

    public void request(View view) throws IOException {

        TextView text2= (TextView)findViewById(R.id.first_text);

        // ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
        //  = service.getDownsized("dc6zaTOxFJmzC","json","funny","1");
        //s a= myDownsized.execute().body();
        //text2.setText(a.getUrl());
        EditText name = (EditText) findViewById(R.id.first_number);
        String keyword = name.getText().toString();
        ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
        Call<JsonResponse> myDownsized = service.getGif("dc6zaTOxFJmzC", "json", keyword);

        myDownsized.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.isSuccessful()) {
                    //for (Data data : response.body().getData()) {
                        //System.out.println(data.getImages().getDownsized().getUrl());
                        TextView text2 = (TextView) findViewById(R.id.first_text);
                        // dw = response.body();
                       // text2.setText(data.getImages().getDownsized().getUrl());
                        text2.setText("");
                        Data data = response.body().getData();
                        url = data.getImageOriginalUrl();
                        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
                        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                        Glide.with(getApplicationContext()).load(url).into(imageViewTarget);
                        // Glide.load(data.getImages().getDownsized().getUrl()).into(imageView);
                        // Glide.with(getApplicationContext()).load(data.getImages().getDownsized().getUrl()).into(imageView);

                  //  }

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
