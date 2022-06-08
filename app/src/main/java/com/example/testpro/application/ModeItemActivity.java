package com.example.testpro.application;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.testpro.MainActivity;
import com.example.testpro.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ModeItemActivity extends AppCompatActivity {
    public static boolean musicFlag = false;
    public static int screenWidth;
    public static int screenHeight;
    private String content = "";
    private Socket socket = MainActivity.socket;
    private PrintWriter writer = MainActivity.writer;

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

//        Switch soundSwitch = findViewById(R.id.soundSwitch);

        Button easyButton = findViewById(R.id.easyButton);
        easyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                if(soundSwitch.isChecked()==true){
//                    musicFlag = true;
//                }
                GameView.mode = 1;
                new Thread(){
                    @Override
                    public void run(){
                        writer.println("modeItemChoose");
                        writer.println(Integer.toString(1));
                        writer.println("whatever");
                    }
                }.start();
                Intent intent = new Intent();
                intent.setClass(ModeItemActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        Button commonButton = findViewById(R.id.commonButton);
        commonButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                if(soundSwitch.isChecked()==true){
//                    musicFlag = true;
//                }
                GameView.mode = 2;
                new Thread(){
                    @Override
                    public void run(){
                        writer.println("modeItemChoose");
                        writer.println(Integer.toString(2));
                        writer.println("whatever");
                    }
                }.start();
                Intent intent = new Intent();
                intent.setClass(ModeItemActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        Button hardButton = findViewById(R.id.hardButton);
        hardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                if(soundSwitch.isChecked()==true){
//                    musicFlag = true;
//                }
                GameView.mode = 3;
                new Thread(){
                    @Override
                    public void run(){
                        writer.println("modeItemChoose");
                        writer.println(Integer.toString(3));
                        writer.println("whatever");
                    }
                }.start();
                Intent intent = new Intent();
                intent.setClass(ModeItemActivity.this, GameActivity.class);
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
