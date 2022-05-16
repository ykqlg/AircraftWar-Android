package com.example.testpro.aircraft;

import com.example.testpro.basic.AbstractFlyingObject;
import com.example.testpro.bullet.BaseBullet;
import com.example.testpro.strategy.Strategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected int direction;
    protected int shootNum;
    protected int power;
//    public Strategy strategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }


    public int getHp() {
        return hp;
    }

    public int getDirection() {
        return direction;
    }

    public int getShootNum() {
        return shootNum;
    }

    public int getPower() {
        return power;
    }

    public void increaseHp(int increase){
        hp += increase;
        if(hp>=maxHp){
            hp = maxHp;
        }
    }

    public void increaseShootNum(){
        this.shootNum += 1;
    }

    public void resetShootNum(int num){
        this.shootNum = num;
    }


    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */

//    public void setStrategy(Strategy strategy){
//        this.strategy=strategy;
//    }
//    public abstract List<BaseBullet> executeStrategy();
//
//    public abstract List<AbstractProp> leave();

    public abstract List<BaseBullet> shoot();

    protected Strategy strategy;

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public List<BaseBullet> executeStrategy(AbstractAircraft abstractAircraft)  {
        return strategy.doOperation(abstractAircraft);
    }

}
