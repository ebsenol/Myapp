package com.example.senolb.project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToPage1(View view){
        Intent intent = new Intent(this, Page1.class);
        //String message = editText.getText().toString();
        startActivity(intent);
    }
    public void goToWiki(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wikipedia.com"));
        startActivity(intent);
    }
    public void explore(View view){
        Intent intent = new Intent(this, ExploreGifs.class);
        startActivity(intent);
    }
    public void quit(View view){
        //finish();
        System.exit(0);
    }
}
