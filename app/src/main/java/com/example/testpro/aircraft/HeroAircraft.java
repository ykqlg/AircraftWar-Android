package com.example.testpro.aircraft;

import com.example.testpro.MainActivity;
import com.example.testpro.application.ImageManager;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {


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
    }
    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft==null){
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(MainActivity.screenWidth / 2,
                            MainActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 300);
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

}