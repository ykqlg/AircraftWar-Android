package com.example.testpro.application;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(GameView.mode == 1){
            mGameView = new EasyGameView(this);

        }
        else if(GameView.mode == 2){
            mGameView = new MediumGameView(this);

        }
        else {
            mGameView = new HardGameView(this);

        }
        setContentView(mGameView);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mGameView.x = event.getX();
            mGameView.y = event.getY();
        }
        return  true;
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            this.finish();
//        }
//        return true;
//    }
}
