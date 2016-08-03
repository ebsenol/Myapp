package com.example.senolb.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.senolb.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @BindView(R.id.spinner) Spinner spin;
    private static final String[]paths = {"Choose a genre", "Action","Animation","Drama"};
    int genre = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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

    @Override
    protected void onResume(){
        super.onResume();
        genre =4;
    }

    public void goToPage1(View view){
        Intent intent = new Intent(this, QuizActivity.class);
        //String message = editText.getText().toString();
        intent.putExtra("genre",genre+"");
        intent.putExtra("easyMode",false);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_pop_out);
        startActivity(intent);
    }
    public void easyMode(View view){
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("genre",genre+"");
        intent.putExtra("easyMode", true);
        startActivity(intent);
    }

    public void explore(View view){
        Intent intent = new Intent(this, BrowseActivity.class);
        startActivity(intent);
    }
    public void quit(View view){
        //finish();
        System.exit(0);
    }


}
