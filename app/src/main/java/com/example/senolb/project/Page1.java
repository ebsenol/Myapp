package com.example.senolb.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import retrofit2.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import retrofit2.Call;
import retrofit2.Callback;

public class Page1 extends Activity {

    public String prevUrl="";
    public String url ="";
    final public int total = 10;                    //total num of gifs to be shown
    public String[] titles = new String[total];     //to hold movie titles
    public int count = 0;                           //index of current movie
    public int answer = -1;
    private Handler mHandler = new Handler();
    public int trueCounter=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        // call the movie api\
        int num = 1+(int)(Math.random() * 100);
        TextView text2 = (TextView) findViewById(R.id.first_text);

        String numm = num +"";

        ApiInterfaceMovie service = ApiInterfaceMovie.retrofit2.create(ApiInterfaceMovie.class);
        float vote = (float) 5.9;
        Call<JsonResponse2> movieList = service.getMovie("en","052ab3ed3f1f39a747fc24b817ee31e7","1",vote); // insert queries
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

    public void request(View view) {
        //   View v = findViewById(R.id.loading_spinner);
        //  v.setVisibility(View.VISIBLE);
        Button A = (Button) findViewById(R.id.answer_1);
        Button B = (Button) findViewById(R.id.answer_2);
        Button C = (Button) findViewById(R.id.answer_3);
        A.setBackgroundResource(R.drawable.buttonshape);
        B.setBackgroundResource(R.drawable.buttonshape);
        C.setBackgroundResource(R.drawable.buttonshape);

        if (count == total){ // go to main page if total count is reached
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            //get the movie title from array
            TextView text2 = (TextView) findViewById(R.id.first_text);
            String keyword = titles[count];
            answer = 1 + (int) (Math.random() * 3);
            ;
            switch (answer) {
                case 1:
                    fillContent(B,C);
                    A.setText(keyword);
                    break;
                case 2:
                    fillContent(A,C);
                    B.setText(keyword);
                    break;
                case 3:
                    fillContent(A,B);
                    C.setText(keyword);
                default:
                    break;
            }

            // keyword = "movie " + keyword;
            text2.setText("True counter: " + trueCounter + "/" + count);
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
                        Glide.with(getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewTarget);

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
        // View q = findViewById(R.id.loading_spinner);

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




    public void check(View view) {
        TextView text2=(TextView) findViewById(R.id.first_text);
        //text2.setText("check" + count);
        if ( answer == 1 && R.id.answer_1 == view.getId()){
            trueAnswer(R.id.answer_1,view);
        }
        else if ( answer == 2 && R.id.answer_2 == view.getId()){
            trueAnswer(R.id.answer_2,view);
        }
        else if ( answer == 3 && R.id.answer_3 == view.getId()){
            trueAnswer(R.id.answer_3,view);
        }
        else if ( answer == -1 ){ //first click
            request(view);
        }
        else falseAnswer( view.getId(),view);


    }
    public Button getAnswer(){
        if (answer == 1)
            return (Button) findViewById(R.id.answer_1);
        else if (answer == 2)
            return (Button) findViewById(R.id.answer_2);
        else if (answer == 3)
            return (Button) findViewById(R.id.answer_3);
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

    public void fillContent(final Button b1, final Button b2){

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
