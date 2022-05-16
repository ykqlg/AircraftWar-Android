package com.example.testpro.bullet;

import com.example.testpro.aircraft.Subscribe;

public class EnemyBullet extends BaseBullet implements Subscribe {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void bomb(){
        vanish();
    }

    @Override
    public void musicEffect() {

    }
}
