package com.example.testpro.prop_factory;

import com.example.testpro.prop.AbstractProp;

public interface PropFactory {
    AbstractProp generateProp(int locationX, int locationY, int speedX, int speedY);

}
