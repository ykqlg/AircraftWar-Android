package com.example.testpro.application;

import android.content.Context;

import com.example.testpro.application.GameView;
import com.example.testpro.enemy_factory.BossFactory;
import com.example.testpro.enemy_factory.EliteFactory;
import com.example.testpro.enemy_factory.EnemyFactory;
import com.example.testpro.enemy_factory.MobFactory;

public class HardGameView extends GameView {
    public HardGameView(Context context) {
        super(context);

    }

    @Override
    public boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        /**
         * 周期（ms)
         * 指示子弹的发射、敌机的产生频率
         */
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            difficultyIncreaseEverytime();
            // 跨越到新的周期

            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void enemyProduce() {
        EnemyFactory enemyFactory;
        double pro = Math.random();
        enemyMaxNumber = 5;


//        System.out.println("[");
        difficultyIncrease();
//        System.out.println("]");


        //随机生成精英机和普通机
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (pro < eliteEnemyCreatePro) {
                enemyFactory = new EliteFactory();
            } else {
                enemyFactory = new MobFactory();
            }
            enemyAircrafts.add(enemyFactory.createEnemy());
        }
        //若boss机处于待生成状态，且分数达到，则生成boss机
        if (createBoss()) {
            bossHappened = true;
            enemyFactory = new BossFactory();
            System.out.println("Boss敌机血量为：" + enemyFactory.getHp());
            //停止当前游戏bgm
//            if (Main.musicFlag) {
//                musicThread.stopMusic();
//            }

            enemyAircrafts.add(enemyFactory.createEnemy());
            enemyFactory.bloodUp();
            enemyFactory.speedUp();
        }
    }
}
