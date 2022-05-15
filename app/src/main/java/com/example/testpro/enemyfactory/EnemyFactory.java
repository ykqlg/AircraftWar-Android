package com.example.testpro.enemyfactory;

import com.example.testpro.aircraft.AbstractAircraft;

public interface EnemyFactory {
    public AbstractAircraft createEnemy(int hp);
}
