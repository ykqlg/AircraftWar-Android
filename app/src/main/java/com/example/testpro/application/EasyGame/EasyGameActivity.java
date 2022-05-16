package com.example.testpro.application.EasyGame;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.application.GameView;

public class EasyGameActivity extends AppCompatActivity {
    private GameView mGameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mGameView = new GameView(this);
        mGameView = new EasyGameView(this);
        setContentView(mGameView);
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
