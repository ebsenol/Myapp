package com.example.senolb.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import retrofit2.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;

public class Page1 extends Activity {

    public String prevUrl="";
    public String url ="";
    final public int total = 10;                    //total num of gifs to be shown
    public String[] titles = new String[total];     //to hold movie titles
    public int count = 0;                           //index of current movie


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_page1);

        // call the movie api\
        int num = 1+(int)(Math.random() * 50);
        TextView text2 = (TextView) findViewById(R.id.first_text);

        String numm = num +"";
        text2.setText(numm);
        ApiInterfaceMovie service = ApiInterfaceMovie.retrofit2.create(ApiInterfaceMovie.class);
        float vote = (float) 6.9;
        Call<JsonResponse2> movieList = service.getMovie("052ab3ed3f1f39a747fc24b817ee31e7","en",numm,vote); // insert queries
        movieList.enqueue(new Callback<JsonResponse2>() {
            @Override
            public void onResponse(Call<JsonResponse2> call, Response<JsonResponse2> response) {
                if (response.isSuccessful()) { //got response
                    int i = 0;
                    for (Result result : response.body().getResults()) {
                        // remove the part after ":" and add to array
                        int ind =result.getOriginalTitle().indexOf(":");
                        if ( ind != -1) {
                            String str = result.getOriginalTitle().substring(0,ind);
                            titles[i] = str;
                        }
                        else {
                            titles[i] = result.getOriginalTitle();
                        }
                        i++;
                        if (i>total-1) break; // reached to number of total movies
                    }
                } else {
                    //unsuccessful response
                }
            }
            @Override
            public void onFailure(Call<JsonResponse2> call, Throwable t) {
                //display the error
                Log.d("Error", t.getMessage());
                TextView text2 = (TextView) findViewById(R.id.first_text);
                text2.setText(t.getMessage());
            }
        });

    }
    public void showName(View view){ //show name of the current gif
        TextView text2= (TextView)findViewById(R.id.first_text);
        if (count==0) // if there is no gif
            text2.setText("--no title--");
        else
            text2.setText(titles[count-1]);
    }
    public void request(View view) throws IOException {
        if (count == total){ // go to main page if total count is reached
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            //get the movie title from array
            TextView text2 = (TextView) findViewById(R.id.first_text);
            String keyword = titles[count];
            keyword = "movie " + keyword;
            text2.setText("");
            count++;

            //call gif api
            ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
            Call<JsonResponse> myDownsized = service.getGif("dc6zaTOxFJmzC", "json", keyword); // api key, format, tag

            myDownsized.enqueue(new Callback<JsonResponse>() {
                @Override
                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                    if (response.isSuccessful()) {
                        //get the data
                        Data data = response.body().getData();
                        prevUrl = url;
                        url = data.getImageOriginalUrl();

                        //display the gif
                        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
                        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                        Glide.with(getApplicationContext()).load(url).into(imageViewTarget);

                    } else { //unsuccessful response
                        TextView text2 = (TextView) findViewById(R.id.first_text);
                        text2.setText("sad");
                    }
                }

                @Override
                public void onFailure(Call<JsonResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    TextView text2 = (TextView) findViewById(R.id.first_text);
                    text2.setText(t.getMessage());
                }
            });
        }
    }

    public void prevGif(View view){
        TextView text2 = (TextView) findViewById(R.id.first_text);
        text2.setText("1");
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        text2.setText("2");
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        text2.setText("3");
        Glide.with(getApplicationContext()).load(prevUrl).into(imageViewTarget);
        text2.setText("4");
    }

    public void getMovie(View view){

        //  myGif.execute();
    }
 /*   public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        //String message = editText.getText().toString();
        startActivity(intent);
    }*/
}
