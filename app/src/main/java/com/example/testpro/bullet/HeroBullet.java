package com.example.testpro.bullet;

import com.example.testpro.MainActivity;
import com.example.testpro.application.GameView;
import com.example.testpro.application.ModeItemActivity;

public class HeroBullet extends BaseBullet{

    public HeroBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);


    }

//    private MusicThread musicThread;

    @Override
    public void musicEffect(){
//        if(Main.musicFlag){
//            musicThread = new MusicThread("src/videos/bullet_hit.wav");
//            musicThread.start();
//        }
        if(ModeItemActivity.musicFlag){
        GameView.myBinder.playBulletHit();

        }
    }

    @Override
    public void bomb(){

    }
}
