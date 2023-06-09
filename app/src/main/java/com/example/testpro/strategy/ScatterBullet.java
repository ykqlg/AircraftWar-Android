package com.example.testpro.strategy;

import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.aircraft.BossEnemy;
import com.example.testpro.aircraft.HeroAircraft;
import com.example.testpro.bullet.BaseBullet;
import com.example.testpro.bullet.EnemyBullet;
import com.example.testpro.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterBullet implements Strategy{

    @Override
    public List<BaseBullet> doOperation(AbstractAircraft abstractAircraft) {
        List<BaseBullet> res = new LinkedList<>();

        int shootNum = abstractAircraft.getShootNum();
        int power = abstractAircraft.getPower();
        BaseBullet baseBullet;


        if (abstractAircraft instanceof HeroAircraft) {
            int direction = abstractAircraft.getDirection();
            int x = abstractAircraft.getLocationX();
            int y = abstractAircraft.getLocationY() + direction * 2;
            int speedX = 0;
            int speedY = abstractAircraft.getSpeedY() + direction * 11;
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 15, y,
                        speedX + (i * 2 - shootNum + 1) * 3, speedY, power);
                res.add(baseBullet);
            }
        }

        if(abstractAircraft instanceof BossEnemy) {
            int direction = abstractAircraft.getDirection();
            int x = abstractAircraft.getLocationX();
            int y = abstractAircraft.getLocationY() + direction*7;
            int speedX = 0;
            int speedY = abstractAircraft.getSpeedY() + direction*15;
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 15, y,
                        speedX+(i*2 - shootNum + 1)*3, speedY, power);
                res.add(baseBullet);
            }
        }

        return res;
    }
}
