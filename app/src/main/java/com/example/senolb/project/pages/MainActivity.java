package com.example.senolb.project.pages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.senolb.project.api_help.ExploreGifs;
import com.example.senolb.project.R;

public class MainActivity extends Activity {
    private Spinner spinner;
    private static final String[]paths = {"Choose a genre", "Action","Animation","Drama"};
    int genre = 4;
    @Override
    protected void onResume(){
        super.onResume();
        genre =4;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                // have no idea why it works this way
                switch ((int)id){
                    case 1:
                        genre--;
                        System.out.println(genre);
                    case 2:
                        genre--;
                        System.out.println(genre);
                    case 3:
                        genre--;
                        System.out.println(genre);
                    default:
                        break;
                }

            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


    }

    public void goToPage1(View view){
        Intent intent = new Intent(this, Page1.class);
        //String message = editText.getText().toString();
        intent.putExtra("genre",genre+"");
        startActivity(intent);
    }
    public void easyMode(View view){
        Intent intent = new Intent(this, EasyMode.class);
        intent.putExtra("genre",genre+"");
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
