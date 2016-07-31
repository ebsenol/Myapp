package com.example.senolb.project.pages;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.senolb.project.R;
import com.example.senolb.project.api_help.ApiInterface;
import com.example.senolb.project.api_help.ApiInterfaceMovie;
import com.example.senolb.project.api_help.Data;
import com.example.senolb.project.api_help.JsonResponse;
import com.example.senolb.project.api_help.JsonResponse2;
import com.example.senolb.project.api_help.Result;
import com.example.senolb.project.easy_mode.ListInterface;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EasyMode extends Activity {
    public String prevUrl="";
    public String url ="";
    final public int total = 15;                    //total num of gifs to be shown
    public String[] titles = new String[total];     //to hold movie titles
    public int count = 0;                           //index of current movie
    public int answer = -1;
    private Handler mHandler = new Handler();
    public int trueCounter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_mode);
        // call the movie api\
        int num = 1+(int)(Math.random() * 100);
        String numm = num +"";

        ApiInterfaceMovie service = ApiInterfaceMovie.retrofit2.create(ApiInterfaceMovie.class);
        float vote = (float) 5.9;
        String genre = getIntent().getExtras().getString("genre");
        Call<JsonResponse2> movieList;
        if (genre.equals("3")){  // get drama
            movieList = service.getMovieWithGenre(18,"en","052ab3ed3f1f39a747fc24b817ee31e7",numm,vote);
        }
        else if (genre.equals("2")){ // get animation
            movieList = service.getMovieWithGenre(16,"en","052ab3ed3f1f39a747fc24b817ee31e7",numm,vote);
        }
        else if (genre.equals("1")){ // get action movies
            movieList = service.getMovieWithGenre(28,"en","052ab3ed3f1f39a747fc24b817ee31e7",numm,vote);
        }
        else //default case
            movieList = service.getMovie("en","052ab3ed3f1f39a747fc24b817ee31e7",numm,vote); // insert queries

        movieList.enqueue(new Callback<JsonResponse2>() {
            @Override
            public void onResponse(Call<JsonResponse2> call, Response<JsonResponse2> response) {
                if (response.isSuccessful()) { //got response
                    int i = 0;
                    for (Result result : response.body().getResults()) {
                        // remove the part after ":" and add to array
                        if(result.getOriginalLanguage().equals("en")){
                            int ind = result.getOriginalTitle().indexOf(":");
                            if (ind != -1) {
                                String str = result.getOriginalTitle().substring(0, ind);
                                titles[i] = str;
                            } else {
                                titles[i] = result.getOriginalTitle();
                            }
                            i++;
                        }
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
            }
        });
    }

    public void request(View view) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress2);
        //   View v = findViewById(R.id.loading_spinner);
        //  v.setVisibility(View.VISIBLE);
        Button A = (Button) findViewById(R.id.ezanswer_1);
        Button B = (Button) findViewById(R.id.ezanswer_2);
        Button C = (Button) findViewById(R.id.ezanswer_3);
        A.setBackgroundResource(R.drawable.buttonshape);
        B.setBackgroundResource(R.drawable.buttonshape);
        C.setBackgroundResource(R.drawable.buttonshape);

        if (count == total) { // go to main page if total count is reached
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            //get the movie title from array
            String keyword = titles[count];
            answer = 1 + (int) (Math.random() * 3);
            ;
            switch (answer) {
                case 1:
                    fillContent(B, C, A, keyword);
                    //A.setText(keyword);
                    break;
                case 2:
                    fillContent(A, C, B, keyword);
                    //  B.setText(keyword);
                    break;
                case 3:
                    fillContent(A, B, C, keyword);
                    //C.setText(keyword);
                default:
                    break;
            }

            keyword = keyword + " movie";
            count++;

            //call gif api
            ListInterface service = ListInterface.retrofit.create(ListInterface.class);
            Call<com.example.senolb.project.easy_mode.JsonResponse> myDownsized = service.getDownsized("dc6zaTOxFJmzC", "json", keyword,"3"); // api key, format, tag

            myDownsized.enqueue(new Callback<com.example.senolb.project.easy_mode.JsonResponse>() {
                @Override
                public void onResponse(Call<com.example.senolb.project.easy_mode.JsonResponse> call,
                                       Response<com.example.senolb.project.easy_mode.JsonResponse> response) {
                    if (response.isSuccessful()) {
                        //get the data
                        int n = (int)(Math.random() * 2);
                        com.example.senolb.project.easy_mode.Data data = response.body().getDataList().get(n);

                        url = data.getImages().getDownsized().getUrl();
                        progressBar.setVisibility(View.VISIBLE);
                        //display the gif
                        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                        Glide
                                .with(getApplicationContext())
                                .load(url)
                                .error(R.drawable.bg)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        return false;
                                    }
                                })
                                .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(),10,10))
                                .into(imageViewTarget);

                    } else { //unsuccessful response

                    }
                }

                @Override
                public void onFailure(Call<com.example.senolb.project.easy_mode.JsonResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        }
    }

    public void check(View view) { // checks if the answer is true or false
        if ( answer == 1 && R.id.ezanswer_1 == view.getId()){
            trueAnswer(R.id.ezanswer_1,view);
        }
        else if ( answer == 2 && R.id.ezanswer_2 == view.getId()){
            trueAnswer(R.id.ezanswer_2,view);
        }
        else if ( answer == 3 && R.id.ezanswer_3 == view.getId()){
            trueAnswer(R.id.ezanswer_3,view);
        }
        else if ( answer == -1 ){ //first click
            request(view);
        }
        else falseAnswer( view.getId(),view);


    }
    public Button getAnswer(){
        if (answer == 1)
            return (Button) findViewById(R.id.ezanswer_1);
        else if (answer == 2)
            return (Button) findViewById(R.id.ezanswer_2);
        else if (answer == 3)
            return (Button) findViewById(R.id.ezanswer_3);
        else return null;

    }

    public void trueAnswer(int id, final View view) {
        trueCounter++;
        Button trueButton = (Button) findViewById(id);
        trueButton.setBackgroundResource(R.drawable.truebuttonshape);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                request(view);
            }
        }, 1500);
    }

    public void falseAnswer(int id, final View view){
        Button falseButton = (Button) findViewById(id);
        falseButton.setBackgroundResource(R.drawable.falsebuttonshape);
        getAnswer().setBackgroundResource(R.drawable.truebuttonshape);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                request(view);
            }
        }, 1500);
    }

    public void fillContent(final Button b1, final Button b2, final Button trueButton, final String keyword){

        int num = 1+(int)(Math.random() * 100);
        String n = num +"";

        ApiInterfaceMovie s = ApiInterfaceMovie.retrofit2.create(ApiInterfaceMovie.class);
        Call<JsonResponse2> movieList = s.getMovie("en","052ab3ed3f1f39a747fc24b817ee31e7",n,(float)4.5); // insert queries
        movieList.enqueue(new Callback<JsonResponse2>() {
            @Override
            public void onResponse(Call<JsonResponse2> call, Response<JsonResponse2> response) {
                if (response.isSuccessful()) { //got response
                    int i = 0;
                    int num = 1+(int)(Math.random() * 15);
                    if(!response.body().getResults().get(num).getOriginalLanguage().equals("en"))
                        num = 1+(int)(Math.random() * 15);
                    int num2 = 1+(int)(Math.random() * 15);
                    if (num==num2 || !response.body().getResults().get(num2).getOriginalLanguage().equals("en"))
                        num2 = 1+(int)(Math.random() * 15);
                    b1.setText(response.body().getResults().get(num).getOriginalTitle());
                    b2.setText(response.body().getResults().get(num2).getOriginalTitle());
                    trueButton.setText(keyword);
                }
                else {
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

}
