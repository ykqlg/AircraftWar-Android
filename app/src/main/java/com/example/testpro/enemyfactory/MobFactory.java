package com.example.testpro.enemyfactory;

import com.example.testpro.MainActivity;
import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.aircraft.MobEnemy;
import com.example.testpro.application.ImageManager;

public class MobFactory implements EnemyFactory{
    @Override
    public AbstractAircraft createEnemy(int hp){
        return new MobEnemy((int) ( Math.random() * (MainActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * MainActivity.screenHeight * 0.2)*1,
                0,
                10,
                hp);
    }
}
