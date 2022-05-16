package com.example.testpro.strategy;

import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.bullet.BaseBullet;

import java.util.List;

public interface Strategy {
    List<BaseBullet> doOperation(AbstractAircraft abstractAircraft) ;

}
