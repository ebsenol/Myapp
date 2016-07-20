package com.example.senolb.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Page1 extends AppCompatActivity {
    private TextView myText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void computeSquare(View view){
        EditText text = (EditText)findViewById(R.id.first_number);
        String numStr=text.getText().toString();
      //  Intent intent = new Intent();
        //String message = editText.getText().toString();
        int num = 0;
        if (numStr.length()>0) {
            try {
                num = Integer.parseInt(numStr);
            } catch (Exception e) {
                Log.e("logtag", "Exception: " + e.toString());
            }
        }
        TextView text2= (TextView)findViewById(R.id.first_text);
        num = num*num;
        text2.setText("Square of the number is " + num);
       // startActivity(intent);
    }
}
