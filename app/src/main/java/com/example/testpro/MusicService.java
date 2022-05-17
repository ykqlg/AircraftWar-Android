package com.example.testpro;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.testpro.R;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MusicService extends Service {

    //创建播放器对象
    private MediaPlayer player;
    private boolean stopFlag = false;
    private boolean isRepeat = false;
    private int filename;
    private static  final String TAG = "MusicService";
    private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();
    private SoundPool mSoundPool;

    public MusicService() {
    }

    //播放音乐
    public void playMusic(){
        if(player == null){
            player = MediaPlayer.create(this, R.raw.bgm);
            if(isRepeat){
                player.setLooping(true);
            }
        }
        player.start();
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

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

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
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "==== MusicService onCreate ===");
        mSoundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
        soundID.put(1, mSoundPool.load(this, R.raw.bullet_hit, 1));
        soundID.put(2, mSoundPool.load(this, R.raw.game_over, 1));
    }

    @Override
    public IBinder onBind(Intent intent){
        playMusic();
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public void playBullet(){

            mSoundPool.play(soundID.get(1), 1, 1, 0,0,1);
        }

        public void playGameOver(){

            mSoundPool.play(soundID.get(2), 1, 1, 0, 0, 1);
        }
    }




}
