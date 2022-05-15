package com.example.testpro.application;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.testpro.MainActivity;
import com.example.testpro.R;
import com.example.testpro.aircraft.HeroAircraft;


public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

//    int count = 0;
//    public float x = 50, y = 50;
    public float x,y;
//    int screenWidth = 480, screenHeight = 800;
    int screenWidth = MainActivity.screenWidth
        , screenHeight = MainActivity.screenHeight;
    boolean mbLoop = false; //控制绘画线程的标志位
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //绘图的画布
    private Paint mPaint;
    private int backGroundTop=0;
    private final HeroAircraft heroAircraft;

//    private final ScheduledExecutorService executorService;

//    private int time = 0;
//    private int timeInterval = 40;

    public GameView(Context context) {
        super(context);
        mbLoop = true;//画布循环渲染
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);//发生回调时处理
        this.setFocusable(true);

//        executorService = new ScheduledThreadPoolExecutor(1);

        loading_img();//加载图片
        heroAircraft = HeroAircraft.getHeroAircraft();

//        new HeroController(this, heroAircraft);

    }
    public void draw () {
        //通过SurfaceHolder对象的lockCanvans()方法，我们可以获取当前的Canvas绘图对象
        canvas = mSurfaceHolder.lockCanvas();
        if (mSurfaceHolder == null || canvas == null) {
            return;
        }
        canvas.drawBitmap(ImageManager.BACKGROUND1_IMAGE,0,this.backGroundTop-ImageManager.BACKGROUND1_IMAGE.getHeight(),mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND1_IMAGE,0,this.backGroundTop,mPaint);
        backGroundTop +=1;
        if(backGroundTop == screenHeight){
            this.backGroundTop = 0;
        }
        canvas.drawBitmap(ImageManager.HERO_IMAGE,heroAircraft.getLocationX()-ImageManager.HERO_IMAGE.getWidth() / 2,heroAircraft.getLocationY()-ImageManager.HERO_IMAGE.getHeight() / 2,mPaint);

        //通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
        mSurfaceHolder.unlockCanvasAndPost(canvas);
//        if (count < 100) {
//            count++;
//        } else {
//            count = 0;
//        }
//        mPaint.setAntiAlias(true);
//        mPaint.setColor(Color.BLUE);
//        //绘制一个全屏大小的矩形
//        canvas.drawRect(0, 0, screenWidth, screenHeight, mPaint);
//        switch (count % 4) {
//            case 0:
//                mPaint.setColor(Color.BLUE);
//                break;
//            case 1:
//                mPaint.setColor(Color.GREEN);
//                break;
//            case 2:
//                mPaint.setColor(Color.RED);
//                break;
//            case 3:
//                mPaint.setColor(Color.YELLOW);
//                break;
//            default:
//                mPaint.setColor(Color.WHITE);
//        }
//        //绘制一个圆形
//        canvas.drawCircle(x, y, 50, mPaint);
//        //通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
//        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }



    public void loading_img(){
        ImageManager.BACKGROUND1_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        ImageManager.HERO_IMAGE = BitmapFactory.decodeResource(getResources(),R.drawable.hero);
    }

    @Override
    public void run () {
        //Runnable task = ()->{
            //设置一个循环来绘制，通过标志位来控制开启绘制还是停止
            while (mbLoop) {
                synchronized (mSurfaceHolder) {
                    heroAircraft.setLocation(x, y);
                    draw();
                }
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
            }
        //};
        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        //executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }
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
