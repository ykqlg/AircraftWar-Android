package com.example.testpro;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MainView mMainView;

    public int screenWidth;
    public int screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainView = new MainView(this);
        setContentView(mMainView);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener((v)->{
            Toast.makeText(getApplicationContext(),"按了按钮",Toast.LENGTH_LONG).show();
        });
    }

    public void gerScreenHW(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;
//        Log.i(Tag,);
        screenHeight = dm.heightPixels;

    }
}
