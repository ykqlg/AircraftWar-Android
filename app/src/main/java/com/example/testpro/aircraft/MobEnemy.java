package com.example.testpro.aircraft;


import com.example.testpro.MainActivity;
import com.example.testpro.application.GameView;
import com.example.testpro.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft /*观察者模式*//*implements DeathObject */{

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.screenHeight) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

//    @Override
//    public List<BaseBullet> executeStrategy() {
//        return new LinkedList<>();
//    }
//
//    @Override
//    public List<AbstractProp> leave(){return new LinkedList<>();};
//
//    @Override
//    public void update() {
//        this.vanish();
//    }
    @Override
    public void bomb(){
        GameView.score = GameView.score + 40;

        vanish();
    }

}
