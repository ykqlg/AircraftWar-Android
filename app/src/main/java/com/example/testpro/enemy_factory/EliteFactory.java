package com.example.testpro.enemy_factory;

import com.example.testpro.MainActivity;
import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.aircraft.EliteEnemy;
import com.example.testpro.application.ImageManager;

import java.util.Random;

public class EliteFactory implements EnemyFactory {
//    @Override
//    public AbstractAircraft createEnemy(int hp){
//        return new EliteEnemy((int) ( Math.random() * (MainActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
//                (int) (Math.random() * MainActivity.screenHeight * 0.2)*1,
//                0,
//                20,
//                hp);
//    }

    private int locationX = (int) ( Math.random() * (MainActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1;
    private int locationY = (int) (Math.random() * MainActivity.screenHeight* 0.2)*1;
    private int speedX = 3;
    private static int speedY = 12;
    private int baseSpeed = 1;
    private static int hp = 30;
    private int baseHp = 30;

    @Override
    public AbstractAircraft createEnemy() {
        Random random = new Random();
        int pro = random.nextInt(2);
        if(pro == 0){
            this.speedX = speedX;
        }
        else{
            this.speedX = (-1)*speedX;
        }
        return new EliteEnemy(this.locationX, this.locationY,this.speedX, this.speedY,this.hp);

    }
    @Override
    public void speedUp(){
        speedY = speedY + baseSpeed;
//        System.out.println("EliteEnemy's speedY increase:");

    }


    @Override
    public int getHp(){
        return hp;
    }

    @Override
    public void bloodUp(){
        hp = hp + baseHp;
//        System.out.println("EliteEnemy's Hp increase");
    }
}
