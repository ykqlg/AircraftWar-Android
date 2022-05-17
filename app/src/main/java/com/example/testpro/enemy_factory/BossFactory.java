package com.example.testpro.enemy_factory;

import com.example.testpro.MainActivity;
import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.aircraft.BossEnemy;
import com.example.testpro.application.GameActivity;
import com.example.testpro.application.GameView;
import com.example.testpro.application.ImageManager;

import java.util.Random;

public class BossFactory implements EnemyFactory{
    private int locationX = (int) ( Math.random() * (MainActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1;
    private int locationY = (int) (Math.random() * MainActivity.screenHeight* 0.2)*1;
    private static int speedX = 6;
    private int baseSpeed = 1;

    private int speedY = 0;
    private static int hp = 800;
    private int baseHp = 400;


    @Override
    public AbstractAircraft createEnemy() {
        Random random = new Random();
        int pro = random.nextInt(2);
        int currentSpeed;
        if(pro == 0){
            currentSpeed = speedX;
        }
        else{

            currentSpeed = (-1)*speedX;
        }

        return new BossEnemy(this.locationX, this.locationY,currentSpeed, this.speedY,this.hp);

    }
    @Override
    public void speedUp(){
        speedX = speedX + baseSpeed;
//        System.out.println("BossEnemy's SpeedX increase");
    }

    @Override
    public int getHp(){
        return hp;
    }
    @Override
    public void bloodUp(){
//        System.out.println("BossEnemy's Hp increase");
        this.hp = this.hp + baseHp;
    }

}
