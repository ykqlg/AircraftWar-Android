package com.example.testpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.testpro.application.EasyGame.EasyGameActivity;
import com.example.testpro.application.HardGame.HardGameActivity;
import com.example.testpro.application.MediumGame.MediumGameActivity;
import com.example.testpro.application.ScoreTableActivity;

public class MainActivity extends AppCompatActivity {

    public static boolean musicFlag = false;
    public static int screenWidth;
    public static int screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getScreenHW();

        Switch soundSwitch = findViewById(R.id.soundSwitch);

        Button easyButton = findViewById(R.id.easyButton);
        easyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(soundSwitch.isChecked()==true){
                    musicFlag = true;
                }
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EasyGameActivity.class);
                startActivity(intent);
            }
        });

        Button commonButton = findViewById(R.id.commonButton);
        commonButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(soundSwitch.isChecked()==true){
                    musicFlag = true;
                }
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MediumGameActivity.class);
                startActivity(intent);
            }
        });

        Button hardButton = findViewById(R.id.hardButton);
        hardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(soundSwitch.isChecked()==true){
                    musicFlag = true;
                }
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, HardGameActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getScreenHW(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口宽度
        screenWidth = dm.widthPixels;
//        Log.i("TAG","screenWidth:"+screenWidth);

        //窗口高度
        screenHeight = dm.heightPixels;
//        Log.i("TAG","screenHeight:"+screenHeight);
    }
}
