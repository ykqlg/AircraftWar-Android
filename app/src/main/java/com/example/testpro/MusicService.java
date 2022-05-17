package com.example.testpro;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.testpro.R;

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
        soundID.put(3, mSoundPool.load(this, R.raw.bomb_explosion, 1));
        soundID.put(4, mSoundPool.load(this, R.raw.bullet, 1));
        soundID.put(5, mSoundPool.load(this, R.raw.get_supply, 1));
    }

    @Override
    public IBinder onBind(Intent intent){
//        playMusic();
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public void playBGM(){
            BGM();
        }

        public void playBossMusic(){
            BossMusic();
        }

        public void playBulletHit(){

            mSoundPool.play(soundID.get(1), 1, 1, 0,0,1);
        }

        public void playGameOver(){

            mSoundPool.play(soundID.get(2), 1, 1, 0, 0, 1);
        }

        public void playBombExplosion(){

            mSoundPool.play(soundID.get(3), 1, 1, 0, 0, 1);
        }

        public void playBullet(){

            mSoundPool.play(soundID.get(4), 1, 1, 0, 0, 1);
        }

        public void playGetSupply(){

            mSoundPool.play(soundID.get(5), 1, 1, 0, 0, 1);
        }


    }


    //播放BGM
    public void BGM(){
        stopMusic();
        if(player == null){
            player = MediaPlayer.create(this, R.raw.bgm);
//            player.setVolume(100,100);
            player.setLooping(true);
        }
        player.start();
    }

    //播放Boss音乐
    public void BossMusic(){
        stopMusic();
        if(player == null){
            player = MediaPlayer.create(this, R.raw.bgm_boss);
            player.setLooping(true);
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


}
