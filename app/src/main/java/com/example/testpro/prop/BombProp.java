package com.example.testpro.prop;

import com.example.testpro.MainActivity;
import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.application.GameView;
import com.example.testpro.application.ModeItemActivity;
import com.example.testpro.application.OnlineOrNotActivity;
import com.example.testpro.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;

public class BombProp extends AbstractProp {
    private List<AbstractFlyingObject> subscribeList = new ArrayList<>();
    public BombProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);

    }

    @Override
    public void bomb(){

    }

    @Override
    public void addSubscribe(AbstractFlyingObject abstractFlyingObject){
        subscribeList.add(abstractFlyingObject);
    }

    @Override
    public void removeSubscribe(AbstractFlyingObject abstractFlyingObject){
        subscribeList.remove(abstractFlyingObject);
    }

    @Override
    public void notifyAllSubscribe(){
        for(AbstractFlyingObject abstractFlyingObject:subscribeList){
            abstractFlyingObject.bomb();
        }

    }
    @Override
    public void influence(AbstractAircraft abstractAircraft){
        if(OnlineOrNotActivity.musicFlag){
            GameView.myBinder.playBombExplosion();
        }
        this.notifyAllSubscribe();
    }
}
