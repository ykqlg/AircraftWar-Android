package com.example.testpro.application.EasyGame;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.application.GameView;
import com.example.testpro.application.ScoreTableActivity;

public class EasyGameActivity extends AppCompatActivity {
    private GameView mGameView;
    public static final Object GAME_LOCK = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mGameView = new GameView(this);
        mGameView = new EasyGameView(this);
        setContentView(mGameView);
//        synchronized (GAME_LOCK){
//            while(mGameView.gameOverFlag==false){
//                try {
//                    GAME_LOCK.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        Intent intent = new Intent();
//        intent.setClass(EasyGameActivity.this, ScoreTableActivity.class);
//        startActivity(intent);

//        //另一种实现方法，也不可行
//        while(true){
//            if(mGameView.gameOverFlag==true){
//                Intent intent = new Intent();
//                intent.setClass(EasyGameActivity.this, ScoreTableActivity.class);
//                startActivity(intent);
//            }
//        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mGameView.x = event.getX();
            mGameView.y = event.getY();
        }
//        if (event.getAction() == MotionEvent.) {
//            System.out.println("why");
//            mGameView.x = event.getX();
//            mGameView.y = event.getY();
//        }
        return  true;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        return true;
    }
}
