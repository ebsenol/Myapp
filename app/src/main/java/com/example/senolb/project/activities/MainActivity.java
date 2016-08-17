package com.example.senolb.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import com.example.senolb.project.R;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.DimType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @BindView(R.id.boom) BoomMenuButton boomMenu;
    private String genre="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        boomMenu.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                switch (buttonIndex){
                    case 0:
                        genre = "Action";
                        break;
                    case 1:
                        genre = "Animation";
                        break;
                    case 2:
                        genre = "Drama";
                        break;
                    case 3:
                        genre = "Sci-Fi";
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void goToPage1(View view){
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("genre",genre);
        intent.putExtra("easyMode",false);
        startActivity(intent);
        this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void easyMode(View view){
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("genre",genre);
        intent.putExtra("easyMode", true);
       // intent.putExtra("url",passUrl);
        startActivity(intent);
        this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void goToLikes(View view){
        Intent intent = new Intent(MainActivity.this, LikesActivity.class);

        getWindow().setExitTransition(new Explode());
        startActivity(intent);
        this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    //boom menu
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Drawable[] subButtonDrawables = new Drawable[4];
        int[] drawablesResource = new int[]{
                R.drawable.action,
                R.drawable.animation,
                R.drawable.drama,
                R.drawable.scifi
        };
        for (int i = 0; i < 4; i++)
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] subButtonTexts = new String[]{"Action", "Animation", "Drama","Sci-Fi"};

        int[][] subButtonColors = new int[4][2];
        for (int i = 0; i < 4; i++) {
            subButtonColors[i][1] = ContextCompat.getColor(this, R.color.com_facebook_button_background_color);
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
        }
        boomMenu.init(
                subButtonDrawables, // The drawables of images of sub buttons. Can not be null.
                subButtonTexts,     // The texts of sub buttons, ok to be null.
                subButtonColors,    // The colors of sub buttons, including pressed-state and normal-state.
                ButtonType.HAM,     // The button type.
                BoomType.LINE,  // The boom type.
                PlaceType.HAM_4_1,  // The place type.
                null,               // Ease type to move the sub buttons when showing.
                null,               // Ease type to scale the sub buttons when showing.
                null,               // Ease type to rotate the sub buttons when showing.
                null,               // Ease type to move the sub buttons when dismissing.
                null,               // Ease type to scale the sub buttons when dismissing.
                null,               // Ease type to rotate the sub buttons when dismissing.
                null                // Rotation degree.
        );
        boomMenu.setDimType(DimType.DIM_9);
    }
}
