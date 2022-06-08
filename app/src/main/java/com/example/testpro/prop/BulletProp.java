package com.example.testpro.prop;

import com.example.testpro.MainActivity;
import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.application.GameView;
import com.example.testpro.application.ModeItemActivity;
import com.example.testpro.application.OnlineOrNotActivity;
import com.example.testpro.basic.AbstractFlyingObject;
import com.example.testpro.strategy.DirectBullet;
import com.example.testpro.strategy.ScatterBullet;
import com.example.testpro.strategy.Strategy;

public class BulletProp extends AbstractProp{
    public BulletProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    private static Thread t;
    private static boolean isScatterShoot = false;
    private int maxShootNum = 5;

    @Override
    public void influence(AbstractAircraft abstractAircraft) throws InterruptedException {
        if(OnlineOrNotActivity.musicFlag){
            GameView.myBinder.playBullet();
        }
        //如果在火力道具有效期间，每触碰一次火力道具，子弹数目加1，有上限
        if(abstractAircraft.getShootNum()<maxShootNum){
            abstractAircraft.increaseShootNum();
        }

        Strategy scatterBullet = new ScatterBullet();
        Strategy directBullet = new DirectBullet();

        if(!isScatterShoot){
            t = new Thread(()->{
                try {
                    isScatterShoot = true;
                    abstractAircraft.setStrategy(scatterBullet);
                    Thread.sleep(5000);
                    abstractAircraft.setStrategy(directBullet);
                    isScatterShoot = false;

                } catch (InterruptedException e) {

                }

            });
            t.start();
        }
        else{
            t.interrupt();
            t = new Thread(()->{
                try {
                    isScatterShoot = true;
                    Thread.sleep(5000);
                    abstractAircraft.setStrategy(directBullet);
                    isScatterShoot = false;
                } catch (InterruptedException e) {

                }
            });
            t.start();
        }
    }
    @Override
    public void addSubscribe(AbstractFlyingObject abstractFlyingObject){

    }

    @Override
    public void removeSubscribe(AbstractFlyingObject abstractFlyingObject){

    }

    @Override
    public void notifyAllSubscribe(){
    }


    @Override
    public void bomb() {

    }
}
