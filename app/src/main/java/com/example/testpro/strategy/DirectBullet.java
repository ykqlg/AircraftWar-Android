package com.example.testpro.strategy;

import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.aircraft.EliteEnemy;
import com.example.testpro.aircraft.HeroAircraft;
import com.example.testpro.bullet.BaseBullet;
import com.example.testpro.bullet.EnemyBullet;
import com.example.testpro.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DirectBullet implements Strategy{

    @Override
    public List<BaseBullet> doOperation(AbstractAircraft abstractAircraft){

        List<BaseBullet> res = new LinkedList<>();
        abstractAircraft.resetShootNum(1);
        int shootNum = abstractAircraft.getShootNum();
        int power = abstractAircraft.getPower();
        BaseBullet baseBullet;

        if(abstractAircraft instanceof HeroAircraft){

            int direction = abstractAircraft.getDirection();
            int x = abstractAircraft.getLocationX();
            int y = abstractAircraft.getLocationY() + direction*2;
            int speedX = 0;
            int speedY = abstractAircraft.getSpeedY() + direction*10;
            System.out.println(abstractAircraft.getSpeedY());

            for(int i=0; i<shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
                res.add(baseBullet);
            }
        }

        if(abstractAircraft instanceof EliteEnemy) {
            int direction = abstractAircraft.getDirection();
            int x = abstractAircraft.getLocationX();
            int y = abstractAircraft.getLocationY() + direction*2;
            int speedX = 0;
            int speedY = abstractAircraft.getSpeedY() + direction*8;
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
                res.add(baseBullet);
            }
        }

        return res;
    }
}
