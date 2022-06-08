package com.example.testpro.bullet;

import com.example.testpro.MainActivity;
import com.example.testpro.application.GameView;
import com.example.testpro.application.ModeItemActivity;
import com.example.testpro.application.OnlineOrNotActivity;

public class HeroBullet extends BaseBullet{

    public HeroBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);


    }


    @Override
    public void musicEffect(){
        if(OnlineOrNotActivity.musicFlag){
            GameView.myBinder.playBulletHit();
        }
    }

    @Override
    public void bomb(){

    }
}
