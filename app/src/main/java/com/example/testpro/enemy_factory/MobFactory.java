package com.example.testpro.enemy_factory;

import com.example.testpro.MainActivity;
import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.aircraft.MobEnemy;
import com.example.testpro.application.ImageManager;

public class MobFactory implements EnemyFactory{
    private int locationX = (int) ( Math.random() * (MainActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1;
    private int locationY = (int) (Math.random() * MainActivity.screenHeight* 0.2)*1;
    private int speedX = 0;
    private static int speedY = 10;
    private int baseSpeed = 1;
    private static int hp = 30;
    private int baseHp = 30;



    @Override
    public AbstractAircraft createEnemy(){
        return new MobEnemy(this.locationX, this.locationY,this.speedX, this.speedY,this.hp);
    }
    @Override
    public void speedUp(){
        speedY = speedY + baseSpeed;
//        System.out.println("MobEnemy's SpeedY increase");


    }
    @Override
    public int getHp(){
        return hp;
    }


    @Override
    public void bloodUp(){
        this.hp = this.hp + baseHp;
//        System.out.println("MobEnemy's Hp increase");
    }

}
