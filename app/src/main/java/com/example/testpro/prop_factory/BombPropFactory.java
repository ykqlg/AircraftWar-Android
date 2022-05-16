package com.example.testpro.prop_factory;

import com.example.testpro.prop.AbstractProp;
import com.example.testpro.prop.BombProp;

public class BombPropFactory implements PropFactory {
    @Override
    public AbstractProp generateProp(int locationX, int locationY, int speedX, int speedY){
        return new BombProp(locationX, locationY,speedX, speedY);
    }
}
