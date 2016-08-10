package com.example.senolb.project.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.senolb.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @BindView(R.id.spinner) Spinner spin;
    private static final String[]paths = {"All", "Action","Animation","Drama"};
    private String genre;
    private String passUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        passUrl = "https://media.giphy.com/media/10XLlvdBlLtRFm/giphy.gif";
        Glide.with(getApplicationContext())
                .load(passUrl)
                .downloadOnly(300,300);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                switch ((int)id){
                    case 1:
                        genre = "Action";
                        break;
                    case 2:
                        genre = "Animation";
                        break;
                    case 3:
                        genre = "Drama";
                        break;
                    default:
                        break;
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {}});
    }

    public void goToPage1(View view){
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("genre",genre+"");
        intent.putExtra("easyMode",false);
        intent.putExtra("url",passUrl);
        startActivity(intent,
                ActivityOptions
                        .makeSceneTransitionAnimation(this).toBundle());
    }

    public void easyMode(View view){
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("genre",genre+"");
        intent.putExtra("easyMode", true);
        intent.putExtra("url",passUrl);
        startActivity(intent,
                ActivityOptions
                        .makeSceneTransitionAnimation(this).toBundle());}

    public void explore(View view){
        Intent intent = new Intent(this, BrowseActivity.class);
        getWindow().setExitTransition(new Explode());
        startActivity(intent,
                ActivityOptions
                        .makeSceneTransitionAnimation(this).toBundle());}
}
