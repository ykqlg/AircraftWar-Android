package com.example.testpro.prop_factory;

import com.example.testpro.prop.AbstractProp;
import com.example.testpro.prop.BulletProp;

public class BulletPropFactory implements PropFactory{
    @Override
    public AbstractProp generateProp(int locationX, int locationY, int speedX, int speedY){
        return new BulletProp(locationX, locationY,speedX, speedY);
    }
}
