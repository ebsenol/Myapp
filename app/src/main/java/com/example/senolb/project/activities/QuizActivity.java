package com.example.senolb.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import com.example.senolb.project.easymodegif.ListInterface;
import com.example.senolb.project.movie.ApiInterfaceMovie;
import com.example.senolb.project.movie.JsonResponse2;
import com.example.senolb.project.movie.Result;
import com.example.senolb.project.normalmodegif.ApiInterface;
import com.example.senolb.project.normalmodegif.Data;
import com.example.senolb.project.normalmodegif.JsonResponse;
import com.like.LikeButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    @BindView(R.id.imageViewGif) ImageView gifView;
    @BindView(R.id.answer_1) Button btnA;
    @BindView(R.id.answer_2) Button btnB;
    @BindView(R.id.answer_3) Button btnC;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.first_text) TextView mainText;
    @BindView(R.id.counterButton) Button btnCount;
    @BindView(R.id.heart_button) LikeButton heartButton;
    @BindView(R.id.toolbar) Toolbar toolbar;
    public String url ="";
    final public int total = 15;                    //total num of gifs to be shown
    public String[] titles = new String[total];     //to hold movie titles
    public String[] urls = new String[total];
    private int count = 0;                           //index of current movie
    public int inCache=0;
    public int answer = -1;
    private Handler mHandler = new Handler();
    public int trueCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        final boolean easyMode= getIntent().getExtras().getBoolean("easyMode");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        btnCount.setText("0/0");

        // call the movie api
        int num = 1+(int)(Math.random() * 100); // get the page number for api
        String page = num +"";
        ApiInterfaceMovie service = ApiInterfaceMovie.retrofit2.create(ApiInterfaceMovie.class);
        float vote = (float) 5.9;


        String genre = getIntent().getExtras().getString("genre");
        Call<JsonResponse2> movieList;
        if (genre.equals("3")){  // get drama
            movieList = service.getMovieWithGenre(18,"en","052ab3ed3f1f39a747fc24b817ee31e7",page,vote);
        }
        else if (genre.equals("2")){ // get animation
            movieList = service.getMovieWithGenre(16,"en","052ab3ed3f1f39a747fc24b817ee31e7",page,vote);
        }
        else if (genre.equals("1")){ // get action movies
            movieList = service.getMovieWithGenre(28,"en","052ab3ed3f1f39a747fc24b817ee31e7",page,vote);
        }
        else //default case
           movieList = service.getMovie("en","052ab3ed3f1f39a747fc24b817ee31e7",page,vote); // insert queries

        movieList.enqueue(new Callback<JsonResponse2>() {
            @Override
            public void onResponse(Call<JsonResponse2> call, Response<JsonResponse2> response) {
                if (response.isSuccessful()) { //got response
                    int i = 0;

                    for (Result result : response.body().getResults()) {
                        // remove the part after ":" and add to the array
                        if(result.getOriginalLanguage().equals("en")){
                           // int ind = result.getOriginalTitle().indexOf(":");
                            //if (ind != -1) {
                             //   String str = result.getOriginalTitle().substring(0, ind);
                              //  titles[i] = str;
                            //} else {
                                titles[i] = result.getOriginalTitle();
                            //}
                            System.out.println(titles[i]);
                            i++;
                        }

                        if (i>total-1) break; // reached to number of total movies
                    }

                    // load 3 gifs
                    if(easyMode) new LoadEasyGifs(3).execute();
                    else         new LoadNormalGifs(3).execute();

                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                            request(getCurrentFocus());
                        }
                    },2000);


                } else { //unsuccessful response

                }
            }
            @Override
            public void onFailure(Call<JsonResponse2> call, Throwable t) {
                Log.d("Error", t.getMessage());
                System.out.println("Asdasdas");
            }
        });

    }
    public void showName(View view){ //show name of the current gif
        if (count==0) // if there is no gif
            mainText.setText("--no title--");
        else
            mainText.setText(titles[count-1]);
    }

    public void request(View view) {
        progressBar.setVisibility(View.VISIBLE);
        btnA.setBackgroundResource(R.drawable.btn_normal); // make the buttons default color again
        btnB.setBackgroundResource(R.drawable.btn_normal);
        btnC.setBackgroundResource(R.drawable.btn_normal);
        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        heartButton.setLiked(false);


        if (count == total) { // go to main page if total count is reached
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            //get the movie title from array
            String keyword = titles[count];
            answer = 1 + (int) (Math.random() * 3); //set the answer
            switch (answer) {
                case 1:
                    fillContent(btnB, btnC, btnA, keyword);
                    break;
                case 2:
                    fillContent(btnA, btnC, btnB, keyword);
                    break;
                case 3:
                    fillContent(btnA, btnB, btnC, keyword);
                default:
                    break;
            }

            mainText.setText("");
            count++;
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifView);

            if( !getIntent().getExtras().getBoolean("easyMode")) { //normal mode

                Glide   .with(getApplicationContext())
                        .load(urls[count-1])
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e,
                                                       String model,
                                                       Target<GlideDrawable> target,
                                                       boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource,
                                                           String model,
                                                           Target<GlideDrawable> target,
                                                           boolean isFromMemoryCache,
                                                           boolean isFirstResource) {
                                progressBar.setVisibility(View.INVISIBLE); //gif is ready
                                return false;
                            }
                        })
                        .into(imageViewTarget);

                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        btnA.setVisibility(View.VISIBLE);
                        btnB.setVisibility(View.VISIBLE);
                        btnC.setVisibility(View.VISIBLE);
                    }
                }, 1500);

                if (inCache<total) new LoadNormalGifs(1).execute(); // load one gif from future :P
            }
            else { //easy mode
                Glide   .with(getApplicationContext())
                        .load(urls[count-1])
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade(400)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e,
                                                       String model,
                                                       Target<GlideDrawable> target,
                                                       boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource,
                                                           String model,
                                                           Target<GlideDrawable> target,
                                                           boolean isFromMemoryCache,
                                                           boolean isFirstResource) {
                                progressBar.setVisibility(View.INVISIBLE); //gif is ready
                                return false;
                            }
                        })
                        .into(imageViewTarget);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        btnA.setVisibility(View.VISIBLE);
                        btnB.setVisibility(View.VISIBLE);
                        btnC.setVisibility(View.VISIBLE);
                    }
                }, 1500);

                if (inCache<total) new LoadEasyGifs(1).execute();

            }
        }
    }

    public void check(View view) { // checks if the answer is true or false
        if ( answer == 1 && btnA == view){
            trueAnswer(btnA);
        }
        else if ( answer == 2 && btnB == view){
            trueAnswer(btnB);
        }
        else if ( answer == 3 && btnC == view){
            trueAnswer(btnC);
        }
        else if ( answer == -1 ){ //first click
            request(view);
        }
        else falseAnswer(view);


    }

    public Button getAnswer(){
        if (answer == 1)
            return btnA;
        else if (answer == 2)
            return btnB;
        else if (answer == 3)
            return btnC;
        else return null;

    }

    public void trueAnswer(final View view) {
        trueCounter++;
        view.setBackgroundResource(R.drawable.btn_true);
     //   YoYo    .with(Techniques.Flash)
     //           .duration(1000)
     //           .playOn(gifView);

        btnCount.setText(trueCounter + "/" + count);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                request(view);
            }
        }, 1000);
    }

    public void falseAnswer(final View view){
        view.setBackgroundResource(R.drawable.btn_false);
        getAnswer().setBackgroundResource(R.drawable.btn_true);

    //    YoYo.with(Techniques.Tada)
     //           .duration(700)
     //           .playOn(gifView);

        btnCount.setText(trueCounter + "/" + count);


        mHandler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                request(view);
            }
        }, 1500);
    }

    public void fillContent(final Button b1, final Button b2, final Button trueButton, final String keyword){

        int num = 1+(int)(Math.random() * 100);
        String n = num +"";

        ApiInterfaceMovie s = ApiInterfaceMovie.retrofit2.create(ApiInterfaceMovie.class);
        Call<JsonResponse2> movieList = s.getMovie("en","052ab3ed3f1f39a747fc24b817ee31e7",n,(float)4.5);
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
                   // b1.setVisibility(View.INVISIBLE);
                  //  b2.setVisibility(View.INVISIBLE);
                    //trueButton.setVisibility(View.INVISIBLE);
                    b1.setText(response.body().getResults().get(num).getOriginalTitle());
                    b2.setText(response.body().getResults().get(num2).getOriginalTitle());
                    trueButton.setText(keyword);

                }
             else {
                    System.out.println("ASDASD5");   //unsuccessful response
                }
            }
            @Override
            public void onFailure(Call<JsonResponse2> call, Throwable t) {
                //display the error
                Log.d("Error", t.getMessage());
                System.out.println("ASDASD4");
            }
        });
    }

    class LoadNormalGifs extends AsyncTask <Void, Void, String>{
        private int count;

        public LoadNormalGifs(int count){
            this.count=count;
        }

        @Override
        protected String doInBackground(Void... voids) {
                ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
                Call<JsonResponse> myDownsized = service.getGif("dc6zaTOxFJmzC", "json", titles[inCache]+" movie");
                myDownsized.enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call,Response<JsonResponse> response){
                        if (response.isSuccessful()) {
                            //get the data
                            Data data = response.body().getData();
                            url = data.getImageOriginalUrl();
                            String height = data.getImageHeight();
                            String width = data.getImageWidth();
                            urls[inCache] = url;
                            System.out.println(titles[inCache]);
                            Glide   .with(getApplicationContext())
                                    .load(url)
                                    .downloadOnly(Integer.parseInt(height),Integer.parseInt(width))
                                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            ;
                            incrementInCache();

                            if (count > 1){
                                count--;
                                callNormal(count);
                            }

                        } else { //unsuccessful response

                        }
                    }
                    @Override
                    public void onFailure(Call<JsonResponse> call,
                                          Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            return null;
        }
    }
    public void incrementInCache(){
        inCache++;
    }
    public void callEasy(int n){
        new LoadEasyGifs(n).execute();
    }
    public void callNormal(int n){
        new LoadNormalGifs(n).execute();
    }

    class LoadEasyGifs extends AsyncTask<Void,Void,String>{

        private int count;

        public LoadEasyGifs(int count){
            this.count=count;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Do some background work

            ListInterface service = ListInterface.retrofit.create(ListInterface.class);
            Call<com.example.senolb.project.easymodegif.JsonResponse> myDownsized =
            service.getDownsized("dc6zaTOxFJmzC", "json", titles[inCache], "3"); // api key, format, tag
            System.out.println(titles[inCache]+"---------");
            myDownsized.enqueue(new Callback<com.example.senolb.project.easymodegif.JsonResponse>() {
                @Override
                public void onResponse(Call<com.example.senolb.project.easymodegif.JsonResponse> call,
                                       Response<com.example.senolb.project.easymodegif.JsonResponse> response) {
                    if (response.isSuccessful()) {
                    //get the data
                        int n = (int) (Math.random() * 2);
                        com.example.senolb.project.easymodegif.Data data =
                                response.body().getDataList().get(n);

                        url = data.getImages().getDownsized().getUrl();
                        urls[inCache] = url;
                        System.out.println(inCache);
                          //  System.out.println(titles[inCache]+"---------");
                        String height = data.getImages().getDownsized().getHeight();
                        String width = data.getImages().getDownsized().getWidth();
                        Glide   .with(getApplicationContext())
                                .load(urls[inCache])
                                .downloadOnly(Integer.parseInt(height), Integer.parseInt(width))
                            // .diskCacheStrategy(DiskCacheStrategy.NONE)
                        ;

                        incrementInCache();

                        if (count > 1){
                            count--;
                            //  new LoadEasyGifs(count).execute();
                            callEasy(count);
                        }
                    } else { //unsuccessful response

                    }
                }

                @Override
                public void onFailure(Call<com.example.senolb.project.easymodegif.JsonResponse> call,
                                          Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
            return null;
        }
    }

    public void goHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}



