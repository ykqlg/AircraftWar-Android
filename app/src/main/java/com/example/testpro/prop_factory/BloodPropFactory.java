package com.example.testpro.prop_factory;

import com.example.testpro.prop.AbstractProp;
import com.example.testpro.prop.BloodProp;

public class BloodPropFactory implements PropFactory {
    @Override
    public AbstractProp generateProp(int locationX, int locationY, int speedX, int speedY) {
        return new BloodProp(locationX, locationY, speedX, speedY);
    }
}
