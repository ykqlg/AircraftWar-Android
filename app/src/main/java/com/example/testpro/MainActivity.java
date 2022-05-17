package com.example.testpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.testpro.application.GameActivity;
import com.example.testpro.application.GameView;
import com.example.testpro.application.ScoreTableActivity;

public class MainActivity extends AppCompatActivity {

    public static boolean musicFlag = false;
    public static int screenWidth;
    public static int screenHeight;

    private  final int REQUEST_EXTERNAL_STORAGE = 1;
    private  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verifyStoragePermissions(this);

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
                GameView.mode = 1;
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GameActivity.class);
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
                GameView.mode = 2;

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GameActivity.class);
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
                GameView.mode = 3;

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GameActivity.class);
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
