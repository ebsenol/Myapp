package com.example.senolb.project.activities;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.senolb.project.GlobalData;
import com.example.senolb.project.R;
import com.example.senolb.project.easymodegif.ListInterface;
import com.example.senolb.project.movie.ApiInterfaceMovie;
import com.example.senolb.project.movie.JsonResponse2;
import com.example.senolb.project.movie.Result;
import com.example.senolb.project.normalmodegif.ApiInterface;
import com.example.senolb.project.normalmodegif.Data;
import com.example.senolb.project.normalmodegif.JsonResponse;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends Activity {
    @BindView(R.id.imageViewGif) ImageView gifView;
    @BindView(R.id.answer_1) Button btnA;
    @BindView(R.id.answer_2) Button btnB;
    @BindView(R.id.answer_3) Button btnC;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.first_text) TextView mainText;
    @BindView(R.id.counterButton) Button btnCount;
    @BindView(R.id.heart_button) LikeButton heartButton;
    @BindView(R.id.resultText) TextView resultText;
    @BindView(R.id.progressBar) ProgressBar timeBar;

    private String url ="";
    final private int total = 10;                    //total num of gifs to be shown
    private String[] titles = new String[total];     //to hold movie titles
    private String[] urls = new String[total];
    private int count = 0;                           //index of current movie
    private int inCache=0;
    private int answer = -1;
    private Handler mHandler = new Handler();
    private int trueCounter=0;
    private CountDownTimer waitTimer;
    private int totalPoints = 0;
    private ObjectAnimator animation;
    private double leftTime =0 ;
    public Bundle bundleExplore = new Bundle();
    public ArrayList<String> pass = new ArrayList<>();
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setupWindowAnimations();

        ButterKnife.bind(this);
        final boolean easyMode= getIntent().getExtras().getBoolean("easyMode");

        timeBar.setVisibility(View.VISIBLE);
        timeBar.setMax(100000);


        heartButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(count<total)
                   GlobalData.addToPassingList(urls[count]+"]"+titles[count]);
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                GlobalData.removeFromPassingList(urls[count]);

            }
        });


        progressBar.setVisibility(View.VISIBLE);
        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        resultText.setVisibility(View.INVISIBLE);
        btnCount.setText("0/0");

        // call the movie api
        int num = 1+(int)(Math.random() * 5); // get the page number for api
        String page = num +"";
        ApiInterfaceMovie service = ApiInterfaceMovie.retrofit2.create(ApiInterfaceMovie.class);
        float vote = (float) 7;


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

    public void request(final View view) {
        progressBar.setVisibility(View.VISIBLE);
        if (getAnswer()!=null)  makeButtonInvisible(getAnswer());

        heartButton.setLiked(false);

        if (count == total) { // go to main page if total count is reached
            showResult(getCurrentFocus());
        } else {
            //get the movie title from array
            final String keyword = titles[count];
            answer = 1 + (int) (Math.random() * 3); //set the answer
            switch (answer) {
                case 1:
                    mHandler.postDelayed(new Runnable() {
                    public void run() {
                        fillContent(btnB, btnC, btnA, keyword);
                    }
                }, 200);

                    break;
                case 2:
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            fillContent(btnA, btnC, btnB, keyword);
                        }
                    }, 200);

                    break;
                case 3:
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            fillContent(btnA, btnB, btnC, keyword);
                        }
                    }, 200);

                default:
                    break;
            }
            mainText.setText("");
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifView);

            if( !getIntent().getExtras().getBoolean("easyMode")) { //normal mode

                Glide   .with(getApplicationContext())
                        .load(urls[count])
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

                                mHandler.postDelayed(new Runnable() {
                                    public void run() {makeButtonVisible(btnA);
                                    }
                                }, 1300);
                                mHandler.postDelayed(new Runnable() {
                                    public void run() {makeButtonVisible(btnB);
                                    }
                                }, 1400);
                                mHandler.postDelayed(new Runnable() {
                                    public void run() {makeButtonVisible(btnC);
                                    }
                                }, 1500);

                                animation = ObjectAnimator.ofInt (timeBar, "progress", 100000, 0);
                                animation.setDuration (11000); //in milliseconds
                                animation.start();
                                waitTimer = new CountDownTimer(11000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                        btnCount.setText(millisUntilFinished / 1000+"");
                                        leftTime=  millisUntilFinished;
                                    }

                                    public void onFinish() {
                                        timeOut();
                                    }
                                }.start();
                                return false;
                            }
                        })
                        .into(imageViewTarget);

                if (inCache<total) new LoadNormalGifs(1).execute(); // load one gif from future :P
            }
            else { //easy mode
                Glide   .with(getApplicationContext())
                        .load(urls[count])
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

                                mHandler.postDelayed(new Runnable() {
                                    public void run() {makeButtonVisible(btnA);
                                    }
                                }, 1300);
                                mHandler.postDelayed(new Runnable() {
                                    public void run() {makeButtonVisible(btnB);
                                    }
                                }, 1400);
                                mHandler.postDelayed(new Runnable() {
                                    public void run() {makeButtonVisible(btnC);
                                    }
                                }, 1500);

                                animation = ObjectAnimator.ofInt (timeBar, "progress", 100000, 0);
                                animation.setDuration (11000); //in milliseconds
                                animation.start();

                                waitTimer = new CountDownTimer(11000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                        btnCount.setText(millisUntilFinished / 1000+"");
                                        leftTime=  millisUntilFinished;
                                    }

                                    public void onFinish() {
                                        timeOut();
                                    }
                                }.start();
                                return false;
                            }
                        })
                        .into(imageViewTarget);
                if (inCache<total) new LoadEasyGifs(1).execute();
            }

            count++;

            //start the timer

        }
    }

    //animations
    public void makeButtonVisible(final Button btn){
   /*     int cx = btnA.getWidth() / 2;
        int cy = btnA.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);

        final Animator anim =
                ViewAnimationUtils.createCircularReveal(btnA, cx, cy, 0, finalRadius);

        final Animator anim2 =
                ViewAnimationUtils.createCircularReveal(btnB, cx, cy, 0, finalRadius);

        final Animator anim3 =
                ViewAnimationUtils.createCircularReveal(btnC, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        btnA.setVisibility(View.VISIBLE);
        anim.start();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                btnB.setVisibility(View.VISIBLE);
                anim2.start();
            }
        },50);

        mHandler.postDelayed(new Runnable() {
            public void run() {
                btnC.setVisibility(View.VISIBLE);
                anim3.start();
            }
        },100);*/

        final Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeIn.setAnimationListener((new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
               btn.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        }));
        btn.startAnimation(animFadeIn);
    }
    public void makeButtonInvisible(final Button btn){

    /*    // get the center for the clipping circle
        int cx = btnA.getWidth() / 2;
        int cy = btnA.getHeight() / 2;
        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);
        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(btnA, cx, cy, initialRadius, 0);
        final Animator anim2 =
                ViewAnimationUtils.createCircularReveal(btnB, cx, cy, initialRadius, 0);
        final Animator anim3 =
                ViewAnimationUtils.createCircularReveal(btnC, cx, cy, initialRadius, 0);
        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btnA.setVisibility(View.INVISIBLE);
            }
        });

        anim2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btnB.setVisibility(View.INVISIBLE);
            }
        });

        anim3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btnC.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                btnC.setVisibility(View.VISIBLE);
                anim2.start();
            }
        },50);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                btnC.setVisibility(View.VISIBLE);
                anim3.start();
            }
        },100);*/

        final Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);

        animFadeOut.setAnimationListener((new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                btn.setVisibility(View.INVISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btn.setBackgroundColor(getColor(R.color.colorPrimary));
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        }));
        btn.startAnimation(animFadeOut);
    }
    public void setTextWithAnimation(View view, String text){
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);
        view.clearAnimation();
        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        view.setVisibility(View.VISIBLE);
        anim.start();
    }
    public void colorChangeAnimation(final View view, int color1, int color2, int time){
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), color1, color2);
        colorAnimation.setDuration(time); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    //answer related
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
        totalPoints+=leftTime;
        if(waitTimer != null) {
            waitTimer.cancel();
            animation.cancel();
            waitTimer = null;
        }
        trueCounter++;
        colorChangeAnimation(view,0xFF673AB7, 0XFF4CAF50,300);  //true
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if ( view == btnA) {
                    makeButtonInvisible(btnB);
                    makeButtonInvisible(btnC);
                }
                else if (view == btnB){
                    makeButtonInvisible(btnA);
                    makeButtonInvisible(btnC);
                }
                else if (view == btnC){
                    makeButtonInvisible(btnA);
                    makeButtonInvisible(btnB);
                }
            }
        }, 1000);


       // btnCount.setText(trueCounter + "/" + count);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                request(view);
            }
        }, 2000);
    }
    public void falseAnswer(final View view){
        if(waitTimer != null) {
            waitTimer.cancel();
            animation.cancel();
            waitTimer = null;
        }
        final Button ans = getAnswer();
        Button temp = null;
        colorChangeAnimation(view,0xFF673AB7, 0xFFF44336,300); //false
        if (btnA!=ans && btnA!=view)    temp=btnA;
        else if (btnB!=ans && btnB!=view)    temp=btnB;
        else if (btnC!=ans && btnC!=view)    temp=btnC;

        final Button third=temp;

        mHandler.postDelayed(new Runnable() {
            public void run() {
                makeButtonInvisible((Button) view);
                makeButtonInvisible(third);
                colorChangeAnimation(ans,0xFF673AB7, 0XFF4CAF50,300);  //true
            }
        }, 1200);


        mHandler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                request(view);
            }
        }, 3000);
    }
    public void timeOut(){
        final Button ans = getAnswer();
        if(waitTimer != null) {
            waitTimer.cancel();
            animation.cancel();
            waitTimer = null;
        }
        btnCount.setText("0");
        colorChangeAnimation(ans,0xFF673AB7, 0XFF4CAF50,300);  //true
        if (ans == btnA){
            makeButtonInvisible(btnB);
            makeButtonInvisible(btnC);
        }
        else if(ans==btnB){
            makeButtonInvisible(btnA);
            makeButtonInvisible(btnC);

        }
        else if(ans==btnC){
            makeButtonInvisible(btnA);
            makeButtonInvisible(btnB);

        }
        mHandler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                request(getCurrentFocus());
            }
        }, 2000);

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


    public void goHome(View view){
        getWindow().setExitTransition(new Explode());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent,
                ActivityOptions
                        .makeSceneTransitionAnimation(this).toBundle());

    }
    public void showResult(View view){

        Glide.clear(gifView);
        resultText.clearComposingText();
        progressBar.setVisibility(View.VISIBLE);

        makeButtonInvisible(getAnswer());
        int point = totalPoints/1000;
        btnCount.setText(point+"");
        if(waitTimer != null) {
            waitTimer.cancel();
            animation.cancel();
            waitTimer = null;
        }
        final String keyword;
        ApiInterface service = ApiInterface.retrofit.create(ApiInterface.class);
        Call<JsonResponse> myDownsized;

        if (point< 15){
            keyword = "disappointed";
            resultText.setText("You Suck");
        }
        else if ( point <30 )
        {
            keyword = "not bad";
            resultText.setText("Not Bad");

        }
        else if ( point<50 )
        {
            keyword = "thumbs up";
            resultText.setText("Good Job");
        }
        else { //guud
            keyword = "cheers";
            resultText.setText("Well Done");
        }

        myDownsized =  service.getGif("dc6zaTOxFJmzC", "json", keyword);
        final GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifView);
        myDownsized.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call,Response<JsonResponse> response){
                if (response.isSuccessful()) {
                    //get the data
                    Data data = response.body().getData();
                    url = data.getImageOriginalUrl();
                    Glide   .with(getApplicationContext())
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                                    if (keyword.equals("disappointed"))
                                        setTextWithAnimation(resultText,"You Suck");
                                    else if(keyword.equals("not bad"))
                                        setTextWithAnimation(resultText,"Not Bad");
                                    else if ( keyword.equals("thumbs up"))
                                        setTextWithAnimation(resultText,"Good Job");
                                    else if (keyword.equals("cheers"))
                                        setTextWithAnimation(resultText,"Well Done");
                                    return false;
                                }
                            })
                            .into(imageViewTarget)
                    ;

                } else { //unsuccessful response

                }
            }
            @Override
            public void onFailure(Call<JsonResponse> call,
                                  Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    //work in background
    public void incrementInCache(){
        inCache++;
    }
    public void callEasy(int n){
        new LoadEasyGifs(n).execute();
    }
    public void callNormal(int n){
        new LoadNormalGifs(n).execute();
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
}



