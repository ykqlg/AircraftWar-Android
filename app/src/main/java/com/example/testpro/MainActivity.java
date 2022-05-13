package com.example.testpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MainView mMainView;

    public static int screenWidth;
    public static int screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mMainView = new MainView(this);
//
//
        getScreenHW();
//
//        setContentView(mMainView);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });


    }

    public void getScreenHW(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口宽度
        screenWidth = dm.widthPixels;
        Log.i("TAG","screenWidth:"+screenWidth);

        //窗口高度
        screenHeight = dm.heightPixels;
        Log.i("TAG","screenHeight:"+screenHeight);
    }
}
