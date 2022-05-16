package com.example.testpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import com.example.testpro.application.GameActivity;

public class MainActivity extends AppCompatActivity {

    public static boolean musicFlag = true;

    public static int screenWidth;
    public static int screenHeight;
    private Switch sc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getScreenHW();

        Button button1 = findViewById(R.id.startButton);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        sc = findViewById(R.id.music);

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

//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        if (b){
//            Toast.makeText(getApplicationContext(),"on",Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(getApplicationContext(),"off",Toast.LENGTH_SHORT).show();
//        }
//    }


//    private Button play, stop, pause, exit;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        play = (Button) findViewById(R.id.play);
//        stop = (Button) findViewById(R.id.stop);
//        pause = (Button) findViewById(R.id.pause);
//        exit = (Button) findViewById(R.id.exit);
//
//        play.setOnClickListener(this);
//        stop.setOnClickListener(this);
//        pause.setOnClickListener(this);
//        exit.setOnClickListener(this);
//    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(MainActivity.this, "MainHelloService onDestroy",
//                Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "MainHelloService onDestroy");
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(this, MusicService.class);
//        switch ( v.getId()){
//            case R.id.play://播放
//                intent.putExtra("action","play");
//                startService(intent);
//                break;
//            case R.id.stop://停止
//                intent.putExtra("action","stop");
//                startService(intent);
//                break;
//            case R.id.pause://暂停
//                intent.putExtra("action","pause");
//                startService(intent);
//                break;
//            case R.id.exit://退出并关闭音乐
//                //停止服务
//                stopService(intent);
//                finish();
//                break;
//            default:
//
//        }
//    }
}
