package com.example.testpro.enemy_factory;

import com.example.testpro.aircraft.AbstractAircraft;

public interface EnemyFactory {
    AbstractAircraft createEnemy();
    void bloodUp();
    void speedUp();
    int getHp();
}
