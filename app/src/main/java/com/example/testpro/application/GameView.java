package com.example.testpro.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.testpro.MainActivity;
import com.example.testpro.R;
import com.example.testpro.aircraft.AbstractAircraft;
import com.example.testpro.aircraft.BossEnemy;
import com.example.testpro.aircraft.EliteEnemy;
import com.example.testpro.aircraft.HeroAircraft;
import com.example.testpro.aircraft.MobEnemy;
import com.example.testpro.basic.AbstractFlyingObject;
import com.example.testpro.bullet.BaseBullet;
import com.example.testpro.bullet.EnemyBullet;
import com.example.testpro.bullet.HeroBullet;
import com.example.testpro.enemy_factory.BossFactory;
import com.example.testpro.enemy_factory.EliteFactory;
import com.example.testpro.enemy_factory.EnemyFactory;
import com.example.testpro.enemy_factory.MobFactory;
import com.example.testpro.prop.AbstractProp;
import com.example.testpro.prop.BloodProp;
import com.example.testpro.prop.BombProp;
import com.example.testpro.prop.BulletProp;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public abstract class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    //鼠标坐标
    public float x , y ;

//    int screenWidth = 480, screenHeight = 800;
    int screenWidth = MainActivity.screenWidth;
    int screenHeight = MainActivity.screenHeight;
    boolean mbLoop = true; //控制绘画线程的标志位
    public SurfaceHolder mSurfaceHolder;
    public Canvas canvas;  //绘图的画布
    public Paint mPaint;

    public int backGroundTop=0;
    public final HeroAircraft heroAircraft;
    public float eliteEnemyCreatePro = 0.4f;

    public int enemyMaxNumber;
    public List<AbstractAircraft> enemyAircrafts;
    public List<BaseBullet> enemyBullets;
    public List<BaseBullet> heroBullets;
    public   List<AbstractProp> props;
    public EnemyFactory enemyFactory;
    public AbstractAircraft enemyAircraft;
    /**
     * Scheduled 线程池，用于任务调度
     */
    public final ScheduledExecutorService executorService;
    public static int score = 0;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    public int timeInterval = 20;
    public int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    public int cycleDuration = 400;
    public int cycleTime = 0;

    public boolean gameOverFlag = false;
    protected int bossScoreThreshold = 800 ;
    protected boolean bossDied = true;
    protected boolean bossHappened = false;
//    protected MusicThread musicThread;
    protected int lastScore;
    protected int cycleTimeFlag=0;


    public GameView(Context context) {

        super(context);
        mbLoop = true;//画布循环渲染
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);//发生回调时处理
        this.setFocusable(true);

        loading_img();//加载图片
        x = MainActivity.screenWidth / 2;
        y = MainActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight();
        //Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(1);

        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();


    }

    @Override
    public void run () {
        action();
    }

    public void action(){
        Runnable task = () -> {
//            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
//                System.out.println("time:"+time);
                // 新敌机产生
                enemyProduce();
                // 飞机射出子弹
                shootAction();
            }
            //这边的x，y一开始默认都为0
            heroAircraft.setLocation(x, y);

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            try {
                crashCheckAction();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 后处理
            postProcessAction();

            //绘制界面
            draw();

//          每个时刻重绘界面
//            synchronized (mSurfaceHolder) {
//                draw();
//            }
//            try {
//                Thread.sleep(200);
//            } catch (Exception e) {
//
//            }

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;

//                if(Main.musicFlag){
//
//                    new MusicThread("src/videos/game_over.wav").start();
//                    musicThread.stopMusic();
//                }
                System.out.println("Game Over!");
//                synchronized (Main.panelLock) {
//                    Main.panelLock.notify();
//                }
            }

        };
        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }


    //***********************
    //      Action 各部分
    //***********************

    public boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    public void shootAction() {

        // 敌机射击
        for(int i = 0; i<enemyAircrafts.size(); i++){

            enemyBullets.addAll(enemyAircrafts.get(i).shoot());
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());


    }

    public void bulletsMoveAction() {
        for (int i = 0; i<heroBullets.size(); i++) {
            heroBullets.get(i).forward();
        }
        for (int i = 0; i<enemyBullets.size(); i++) {
            enemyBullets.get(i).forward();
        }
    }

    public abstract void enemyProduce();

//    public void enemyProduce(){
//        double pro = Math.random();
//        enemyMaxNumber = 5;
//
//        //随机生成精英机和普通机
//        if (enemyAircrafts.size() < enemyMaxNumber) {
//
//            if(pro<eliteEnemyCreatePro){
//                enemyFactory = new EliteFactory();
//                enemyAircraft = enemyFactory.createEnemy();
//            }
//            else{
//                enemyFactory = new MobFactory();
//                enemyAircraft = enemyFactory.createEnemy();
//            }
//            enemyAircrafts.add(enemyAircraft);
//        }
//        if (createBoss()) {
//            bossHappened = true;
//            enemyFactory = new BossFactory();
//            System.out.println("Boss敌机血量为：" + enemyFactory.getHp());
//            //停止当前游戏bgm
////            if (Main.musicFlag) {
////                musicThread.stopMusic();
////            }
//
//            enemyAircrafts.add(enemyFactory.createEnemy());
//        }
//
//
//    }

    private void aircraftsMoveAction() {
        for(int i = 0 ; i<enemyAircrafts.size();i++){
            enemyAircrafts.get(i).forward();

        }
    }

    protected void propsMoveAction(){
        for(int i = 0; i<props.size();i++){
            props.get(i).forward();
        }
    }

    protected boolean createBoss(){
        //当前得分超出当前阈值一定差值且boss已死亡，避免出现多架boss机
        if(score-lastScore >= bossScoreThreshold && bossDied)
        {
            this.bossDied = false;//标注boss生成
            return true;
        }
        else{
            return false;
        }
    }


    protected void difficultyIncrease(){
        //以boss机被消灭作为一轮的结束，每轮新开始时，游戏难度上升
        if (bossDied && bossHappened) {
            //敌机产生周期、英雄机射击、敌机射击周期减小
            cycleDuration -= 10;
            //boss产生阈值提高
            bossScoreThreshold = bossScoreThreshold - 50;
            //精英机产生比例提高
            eliteEnemyCreatePro = eliteEnemyCreatePro + 0.1f;
            //敌机属性增幅
            EnemyFactory eliteFactory = new EliteFactory();
            eliteFactory.bloodUp();
            eliteFactory.speedUp();

            EnemyFactory mobFactory = new MobFactory();
            mobFactory.bloodUp();
            mobFactory.speedUp();
            //敌机数目最大值增加
            enemyMaxNumber++;
            bossHappened = false;
            System.out.println("提高难度！精英机产生概率：" + eliteEnemyCreatePro +
                    ", 精英机血量提高为：" + eliteFactory.getHp() +
                    ", 普通机血量提高为：" + mobFactory.getHp() + ", 敌机速度提高");
            System.out.println("敌机数目最大值变为：" + enemyMaxNumber + ",boss机产生阈值变为：" + bossScoreThreshold);
        }
    }

    protected void difficultyIncreaseEverytime(){

        cycleTimeFlag++;
        if(cycleTimeFlag == 10){
            cycleTimeFlag = 0;
            //精英机产生比例提高
            eliteEnemyCreatePro += 0.01;
            //敌机产生周期、英雄机射击、敌机射击周期减小
            cycleDuration -= 1;
            System.out.println("提高难度！精英机产生概率：" + eliteEnemyCreatePro +
                    ", 敌机周期：" + cycleDuration);
        }


    }

    protected void bombEmpty(AbstractProp abstractProp){
        for(int i =0 ; i<enemyAircrafts.size();i++){
            AbstractAircraft abstractAircraft = enemyAircrafts.get(i);
            if(abstractAircraft instanceof BossEnemy){
                continue;
            }
            else{
                abstractProp.addSubscribe(abstractAircraft);
            }
        }
        for(int i = 0; i<enemyBullets.size();i++){
            BaseBullet baseBullet = enemyBullets.get(i);
            abstractProp.addSubscribe(baseBullet);
        }
    }



    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    protected void crashCheckAction() throws InterruptedException {
        // 敌机子弹攻击英雄
        for (int i=0;i<enemyBullets.size();i++) {
            BaseBullet bullet = enemyBullets.get(i);
            if (bullet.notValid()) {
                continue;
            }
            if(heroAircraft.crash(bullet)){
                //有点问题
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (int i =0; i<heroBullets.size();i++) {

            BaseBullet bullet = heroBullets.get(i);
            if (bullet.notValid()) {
                continue;
            }
            for (int j=0; j<enemyAircrafts.size();j++) {
                AbstractAircraft enemyAircraft = enemyAircrafts.get(j);
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
//                    bullet.musicEffect();
                    bullet.vanish();

                    if (enemyAircraft.notValid()) {
                        //根据击落敌机的类型判断

                        if(enemyAircraft instanceof MobEnemy){
                            //击败普通机 score+30
                            score = score+30;

                        }
                        if(enemyAircraft instanceof BossEnemy){
                            //击败boss机 score+30
                            score = score+50;
                            lastScore = score;//重置boss生成阈值
                            bossDied = true;//标记boss已死亡
//                            if(Main.musicFlag){
//                                System.out.println("why");
//                                musicThread = new MusicThread("src/videos/bgm.wav");
//                                musicThread.start();
//                            }
                            enemyAircraft.vanish();
                            ((BossEnemy) enemyAircraft).generateProp(props);
                        }

                        // 获得分数，产生道具补给
                        if(enemyAircraft instanceof EliteEnemy){
                            //击败精英机 score+40;产生道具
                            score = score+40;

                            ((EliteEnemy) enemyAircraft).generateProp(props);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        //  我方获得道具，道具生效
        for(int i = 0; i< props.size();i++){
            AbstractProp prop = props.get(i);
            if(prop.crash(heroAircraft) || heroAircraft.crash(prop)){
                if(prop instanceof BombProp){
                    bombEmpty(prop);
                    prop.influence(heroAircraft);

                }
                else{
                    prop.influence(heroAircraft);
                }
                prop.vanish();
            }
        }
    }


    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    public void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }





    //***********************
    //      Paint 各部分
    //***********************

    public void loading_img() {

        ImageManager.BACKGROUND1_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        ImageManager.HERO_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
        ImageManager.MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.mob);
        ImageManager.BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.boss);
        ImageManager.ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.elite);
        ImageManager.ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_enemy);
        ImageManager.HERO_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_hero);
        ImageManager.BLOOD_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_blood);
        ImageManager.BOMB_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_bomb);
        ImageManager.BULLET_PROP_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_bullet);


        ImageManager.CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), ImageManager.HERO_IMAGE);
        ImageManager.CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), ImageManager.MOB_ENEMY_IMAGE);
        ImageManager.CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ImageManager.ELITE_ENEMY_IMAGE);
        ImageManager.CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ImageManager.ENEMY_BULLET_IMAGE);
        ImageManager.CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), ImageManager.HERO_BULLET_IMAGE);
        ImageManager.CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), ImageManager.BOSS_ENEMY_IMAGE);
        ImageManager.CLASSNAME_IMAGE_MAP.put(BloodProp.class.getName(), ImageManager.BLOOD_PROP_IMAGE);
        ImageManager.CLASSNAME_IMAGE_MAP.put(BombProp.class.getName(), ImageManager.BOMB_PROP_IMAGE);
        ImageManager.CLASSNAME_IMAGE_MAP.put(BulletProp.class.getName(), ImageManager.BULLET_PROP_IMAGE);
    }

    public void draw () {
        //画布滚动
        canvasRolling();

        //绘制子弹
        paintImageWithPositionRevised(canvas,enemyBullets);
        paintImageWithPositionRevised(canvas,heroBullets);
        paintImageWithPositionRevised(canvas,props);

        //绘制敌机
        paintImageWithPositionRevised(canvas,enemyAircrafts);

        //绘制英雄机
        canvas.drawBitmap(ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX()-ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY()-ImageManager.HERO_IMAGE.getHeight() / 2,mPaint);



        //通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
        mSurfaceHolder.unlockCanvasAndPost(canvas);

        //绘制得分和生命值
//        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Canvas canvas, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }
        for (int i=0;i<objects.size();i++) {
            Bitmap image = objects.get(i).getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";

            canvas.drawBitmap(image, objects.get(i).getLocationX() - image.getWidth() / 2,
                    objects.get(i).getLocationY() - image.getHeight() / 2, mPaint);

        }
    }

    private void canvasRolling(){

        //通过SurfaceHolder对象的lockCanvans()方法，我们可以获取当前的Canvas绘图对象
        canvas = mSurfaceHolder.lockCanvas();
        if (mSurfaceHolder == null || canvas == null) {
            return;
        }
        canvas.drawBitmap(ImageManager.BACKGROUND1_IMAGE,0,this.backGroundTop-ImageManager.BACKGROUND1_IMAGE.getHeight(),mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND1_IMAGE,0,this.backGroundTop,mPaint);
        this.backGroundTop += 1;
        if(this.backGroundTop >= screenHeight){
            this.backGroundTop = this.backGroundTop-screenHeight;
        }
    }

//    绘制排行榜（不会弄）
//    void paintScoreAndLife(Graphics g) {
//        int x = 10;
//        int y = 25;
//        g.setColor(new Color(16711680));
//        g.setFont(new Font("SansSerif", Font.BOLD, 22));
//        g.drawString("SCORE:" + this.score, x, y);
//        y = y + 20;
//        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
//    }

    @Override
    public void surfaceCreated (@NonNull SurfaceHolder holder){
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged (@NonNull SurfaceHolder holder,int format, int width, int height)
    {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void surfaceDestroyed (@NonNull SurfaceHolder holder){
        mbLoop = false;
    }


}
