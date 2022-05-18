package com.example.testpro.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.MainActivity;
import com.example.testpro.R;
import com.example.testpro.user_dao.User;
import com.example.testpro.user_dao.UserDao;
import com.example.testpro.user_dao.UserDaoImpl;

import java.io.IOException;

public class InputActivity extends AppCompatActivity {

    public String playerName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        EditText nameText = findViewById(R.id.nameText);
        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                playerName = nameText.getText().toString();
                UserDao userDao=new UserDaoImpl(InputActivity.this);
                User user=new User(GameView.score,playerName);
                try {
                    userDao.doAdd(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Log.i("table","name:"+user.getUserName()+",s" +
//                        "core:"+user.getUserScore()+",time:"+user.getUserTime());
//                System.out.println("name:"+user.getUserName()+",s" +
//                        "core:"+user.getUserScore()+",time:"+user.getUserTime());
//                System.out.println(user);
//                System.out.println(userDao);

                //跳转到得分排行榜
                Intent intent = new Intent();
                intent.setClass(InputActivity.this, ScoreTableActivity.class);
                startActivity(intent);
            }
        });
    }


}
