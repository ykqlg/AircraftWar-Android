package com.example.testpro.application.HardGame;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.application.EasyGame.EasyGameView;
import com.example.testpro.application.GameView;
import com.example.testpro.application.MediumGame.MediumGameActivity;
import com.example.testpro.application.ScoreTableActivity;

public class HardGameActivity extends AppCompatActivity {
    private GameView mGameView;
    public static final Object GAME_LOCK = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mGameView = new GameView(this);
        mGameView = new HardGameView(this);
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
//        intent.setClass(HardGameActivity.this, ScoreTableActivity.class);
//        startActivity(intent);
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
