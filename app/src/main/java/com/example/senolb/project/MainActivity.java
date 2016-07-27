package com.example.senolb.project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.senolb.project.Page1;
import com.example.senolb.project.R;

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
}
