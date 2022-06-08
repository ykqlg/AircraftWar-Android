package com.example.testpro.prop;

import com.example.testpro.MainActivity;
import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.application.GameView;
import com.example.testpro.application.ModeItemActivity;
import com.example.testpro.application.OnlineOrNotActivity;
import com.example.testpro.basic.AbstractFlyingObject;

public class BloodProp extends AbstractProp {
    public BloodProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);

    }
    @Override
    public void influence(AbstractAircraft abstractAircraft){
        if(OnlineOrNotActivity.musicFlag){
            GameView.myBinder.playGetSupply();
        }
        abstractAircraft.increaseHp(150);
    }

    @Override
    public void addSubscribe(AbstractFlyingObject abstractFlyingObject){

    }

    @Override
    public void removeSubscribe(AbstractFlyingObject abstractFlyingObject){

    }

    @Override
    public void notifyAllSubscribe(){
    }

    @Override
    public void bomb(){

    }
}
