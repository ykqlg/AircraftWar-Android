package com.example.testpro.application.EasyGame;

import android.content.Context;

import com.example.testpro.application.GameView;
import com.example.testpro.enemy_factory.EliteFactory;
import com.example.testpro.enemy_factory.EnemyFactory;
import com.example.testpro.enemy_factory.MobFactory;

public class EasyGameView extends GameView {

    public EasyGameView(Context context) {
        super(context);

    }

    @Override
    public void enemyProduce(){
        double pro = Math.random();
        enemyMaxNumber = 5;

        //随机生成精英机和普通机
        if (enemyAircrafts.size() < enemyMaxNumber) {

            if(pro<eliteEnemyCreatePro){
                enemyFactory = new EliteFactory();
                enemyAircraft = enemyFactory.createEnemy();
            }
            else{
                enemyFactory = new MobFactory();
                enemyAircraft = enemyFactory.createEnemy();
            }
            enemyAircrafts.add(enemyAircraft);
        }
    }
}
