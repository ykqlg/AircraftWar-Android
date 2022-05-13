package com.example.testpro;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;


public class GameView {
    public void loading_img(){
        ImageManager.BACKGROUND1_IMAGE = BitmapFactory.decodeResource(getResource(),R.drawable.bg);
        ImageManager.BACKGROUND1_IMAGE = BitmapFactory.de
    }
    int count = 0;
    public float x = 50, y = 50;
    int screenWidth = 480, screenHeight = 800;
    boolean mbLoop = false; //控制绘画线程的标志位
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //绘图的画布
    private Paint mPaint;
    private int backGroundTop;
    public GameView(Context context) {
        super(context);
        mbLoop = true;//画布循环渲染
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);//发生回调时处理
        this.setFocusable(true);
    }
    public void draw () {
        //通过SurfaceHolder对象的lockCanvans()方法，我们可以获取当前的Canvas绘图对象
        canvas = mSurfaceHolder.lockCanvas();
        if (mSurfaceHolder == null || canvas == null) {
            return;
        }
        canvas.drawBitmap(ImageManager.BACKGROUND1_IMAGE,0,this,backGroundTop-screenHeight,mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND1_IMAGE,0,this,backGroundTop,mPaint);
        backGroundTop+=1;
//        if(backGroundTop == screenHeight){
//            this.backGroundTop = 0;
//        }

        if (count < 100) {
            count++;
        } else {
            count = 0;
        }
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        //绘制一个全屏大小的矩形
        canvas.drawRect(0, 0, screenWidth, screenHeight, mPaint);
        switch (count % 4) {
            case 0:
                mPaint.setColor(Color.BLUE);
                break;
            case 1:
                mPaint.setColor(Color.GREEN);
                break;
            case 2:
                mPaint.setColor(Color.RED);
                break;
            case 3:
                mPaint.setColor(Color.YELLOW);
                break;
            default:
                mPaint.setColor(Color.WHITE);
        }
        //绘制一个圆形
        canvas.drawCircle(x, y, 50, mPaint);
        //通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }
    @Override
    public void run () {
        //设置一个循环来绘制，通过标志位来控制开启绘制还是停止
        while (mbLoop) {
            synchronized (mSurfaceHolder) {
                draw();
            }
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }
        }
    }
    @Override
    public void surfaceCreated (SurfaceHolder holder){
        new Thread(this).start();
    }
    @Override
    public void surfaceChanged (SurfaceHolder holder,int format, int width, int height)
    {
        screenWidth = width;
        screenHeight = height;
    }
    @Override
    public void surfaceDestroyed (SurfaceHolder holder){
        mbLoop = false;
    }
}
