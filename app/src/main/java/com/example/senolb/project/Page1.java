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

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Retrofit;

public class Page1 extends AppCompatActivity {
    private TextView myText = null;
    public int num = 0;
    public int prevNum = 0;
    // TODO - insert your themoviedb.org API KEY here
//    private final static String API_KEY = "052ab3ed3f1f39a747fc24b817ee31e7";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        Glide.with(this).load("https://media.giphy.com/media/LyJ6KPlrFdKnK/giphy.gif").into(imageView);
    }
    public void computeSquare(View view){
       /* EditText text = (EditText)findViewById(R.id.first_number);
        String numStr=text.getText().toString();
      //  Intent intent = new Intent();
        //String message = editText.getText().toString();

    //    if (numStr.length()>0) {
            try {
                num = Integer.parseInt(numStr);
            } catch (Exception e) {
                Log.e("logtag", "Exception: " + e.toString());
            }
    //    }
        TextView text2= (TextView)findViewById(R.id.first_text);
        num = num*num;
        text2.setText("Square of the number is " + num);
       // startActivity(intent);*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.giphy.com/v1/gifs/search?api_key=dc6zaTOxFJmzC&fmt=json&limit=1&q=")
                .build();

        ApiInterface service = retrofit.create(ApiInterface.class);
        String tag="funny";

        Gif testGif = null;
        String url="";
        Gif myGif = service.getGif(url);
        TextView text2= (TextView)findViewById(R.id.first_text);

        //testGif = myGif.execute();

       // text2.setText(myGif.getUrl());

    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        //String message = editText.getText().toString();
        startActivity(intent);
    }
}
