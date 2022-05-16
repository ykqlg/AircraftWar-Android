package com.example.testpro;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.example.testpro.R;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MusicService extends Service {

    //创建播放器对象
    private MediaPlayer player;
    private boolean stopFlag = false;
    private boolean isRepeat = false;
    private int filename;

    public MusicService() {
         player = MediaPlayer.create(this,R.raw.bgm);
//        this.filename = filename;
    }

    //播放音乐
    public void playMusic(){
        player.start();
        if(isRepeat){
            player.isLooping();
        }
    }

    public void stopMusic(){
        if (player != null) {
            player.stop();
            player.reset();//重置
            player.release();//释放
            player = null;
        }
    }


    public void setRepeat(){
        this.isRepeat = true;
    }

    private static final String TAG = "MusicService";



//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i(TAG, "==== MusicService onStartCommand ===");
//        String action = intent.getStringExtra("action");
//
//        if ("play".equals(action)) {
//        //播放
//            playMusic();
//        } else if ("stop".equals(action)) {
//        //停止
//            stopMusic();
//        }
//        return super.onStartCommand(intent, flags, startId);
//
//    }








    @Override
    public void onCreate() {
        super.onCreate();
            Log.i(TAG, "==== MusicService onCreate ===");
    }
    @Override

    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }


}
