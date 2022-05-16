package com.example.testpro.aircraft;

import com.example.testpro.MainActivity;
import com.example.testpro.application.GameView;
import com.example.testpro.bullet.BaseBullet;
import com.example.testpro.prop.AbstractProp;
import com.example.testpro.prop_factory.BloodPropFactory;
import com.example.testpro.prop_factory.BombPropFactory;
import com.example.testpro.prop_factory.BulletPropFactory;
import com.example.testpro.prop_factory.PropFactory;
import com.example.testpro.strategy.DirectBullet;
import com.example.testpro.strategy.Strategy;

import java.util.List;
import java.util.Random;

public class EliteEnemy extends AbstractAircraft {
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        Strategy directBullet = new DirectBullet();
        this.setStrategy(directBullet);
    }

    /** 攻击方式 */

    private int shootNum = 1;     //子弹一次发射数量
    private int power = 30;       //子弹伤害
    private int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)



    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.screenHeight) {
            vanish();
        }
    }

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
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {

        return executeStrategy(this);
    }

    @Override
    public void bomb(){
        GameView.score = GameView.score + 40;

        vanish();
    }


    public void generateProp(List<AbstractProp>props){
        int x = this.getLocationX();
        int y = this.getLocationY();

        AbstractProp prop;
        PropFactory propFactory;

        Random random = new Random();
        int pro = random.nextInt(10);
//        int pro = 4;
        if(pro>=0 && pro<=2){
            propFactory = new BloodPropFactory();
            prop = propFactory.generateProp(x, y, 0, 10) ;
            props.add(prop);
        }
        else if(pro>=3 && pro<=5){
            propFactory = new BombPropFactory();
            prop = propFactory.generateProp(x, y, 0, 10) ;
            props.add(prop);
        }
        else if(pro>=6 && pro<=8){
            propFactory = new BulletPropFactory();
            prop = propFactory.generateProp(x, y, 0, 10) ;
            props.add(prop);
        }
        else{
            prop = null;
        }

    }
}
