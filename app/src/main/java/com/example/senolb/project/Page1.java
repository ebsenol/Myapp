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
    final public int total = 10;
    public String[] titles = new String[total];
    public int count = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_page1);

        TextView text2= (TextView)findViewById(R.id.first_text);
        ApiInterfaceMovie service = ApiInterfaceMovie.retrofit2.create(ApiInterfaceMovie.class);
        Call<JsonResponse2> movieList = service.getMovie("052ab3ed3f1f39a747fc24b817ee31e7","popularity.desc","en");
        movieList.enqueue(new Callback<JsonResponse2>() {
            @Override
            public void onResponse(Call<JsonResponse2> call, Response<JsonResponse2> response) {
                if (response.isSuccessful()) {
                    int i = 0;
                    for (Result result : response.body().getResults()) {
                        int ind =result.getOriginalTitle().indexOf(":");
                        if ( ind != -1) {
                            String str = result.getOriginalTitle().substring(0,ind);
                            titles[i] = str;
                        }
                        else {
                            titles[i] = result.getOriginalTitle();
                        }
                        i++;
                        if (i>total-1) break;
                    }
                } else {
                    //unsuccessful response
                }
            }
            @Override
            public void onFailure(Call<JsonResponse2> call, Throwable t) {
                Log.d("Error", t.getMessage());
                TextView text2 = (TextView) findViewById(R.id.first_text);
                text2.setText(t.getMessage());
            }
        });

    }
    public void showName(View view){
        TextView text2= (TextView)findViewById(R.id.first_text);
        if (count==0)
            text2.setText("--no title--");
        else
            text2.setText(titles[count-1]);
    }
    public void request(View view) throws IOException {
        if (count == total){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            TextView text2 = (TextView) findViewById(R.id.first_text);
            String keyword = titles[count];
            keyword = "movie " + keyword;
            text2.setText("");
            count++;
            ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
            Call<JsonResponse> myDownsized = service.getGif("dc6zaTOxFJmzC", "json", keyword);

            myDownsized.enqueue(new Callback<JsonResponse>() {
                @Override
                public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                    if (response.isSuccessful()) {
                        Data data = response.body().getData();
                        prevUrl = url;
                        url = data.getImageOriginalUrl();

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
