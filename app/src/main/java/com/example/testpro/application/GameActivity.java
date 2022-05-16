package com.example.testpro.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.MainActivity;

public class GameActivity extends AppCompatActivity {
    private GameView mGameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameView = new GameView(this);
        setContentView(mGameView);
//        while (true){
//            if(mGameView.gameOverFlag==true){
//                Intent intent = new Intent();
//                intent.setClass(GameActivity.this, ScoreTableActivity.class);
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
