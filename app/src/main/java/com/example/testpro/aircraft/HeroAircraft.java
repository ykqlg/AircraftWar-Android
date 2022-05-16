package com.example.testpro.aircraft;

import com.example.testpro.MainActivity;
import com.example.testpro.application.ImageManager;
import com.example.testpro.bullet.BaseBullet;
import com.example.testpro.strategy.DirectBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {


    private int shootNum = 1;     //子弹一次发射数量
    private int power = 30;       //子弹伤害
    private int direction = -1;  //子弹射击方向 (向上发射：1，向下发射：-1)

    private volatile static HeroAircraft heroAircraft;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        DirectBullet directBullet = new DirectBullet();
        this.setStrategy(directBullet);
    }

    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft==null){
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {

                    heroAircraft = new HeroAircraft(MainActivity.screenWidth / 2,
                            MainActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight() ,
                            0,-7,1000);

//                    Strategy heroSingleType = new HeroDoubleType();
//                    heroAircraft.setStrategy(heroSingleType);
                }
            }
        }
        return heroAircraft;
    }



    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }



//    @Override
//    public List<BaseBullet> executeStrategy(){
//        return this.strategy.shootType(this.getLocationX(),this.getLocationY(),this.getSpeedY());
//    }
//    @Override
//    public List<AbstractProp> leave(){return new LinkedList<>();};

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public int getShootNum() {
        return shootNum;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void increaseShootNum(){
        this.shootNum += 1;
    }
    @Override
    public void resetShootNum(int num){
        this.shootNum = num;
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return executeStrategy(this);
    }

    @Override
    public void bomb(){

    }

}