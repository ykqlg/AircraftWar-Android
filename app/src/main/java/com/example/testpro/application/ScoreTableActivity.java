package com.example.testpro.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.MainActivity;
import com.example.testpro.R;
import com.example.testpro.application.EasyGame.EasyGameActivity;
import com.example.testpro.user_dao.UserDao;
import com.example.testpro.user_dao.UserDaoImpl;

import java.io.IOException;

public class ScoreTableActivity extends AppCompatActivity {

    ListView scoreTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoretable);
        scoreTable=(ListView) findViewById(R.id.scoreTable);
        //用于测试
//        UserDao userDao=new UserDaoImpl();
//        String[] tableData = new String[userDao.getAllUsers().size()];
//        for(int i=0;i<userDao.getAllUsers().size();i++){
//            tableData[i]= userDao.getAllUsers().get(i).getUserName();
//        }
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tableData);
//        scoreTable.setAdapter(arrayAdapter);

//        UserDao userDao=new UserDaoImpl();
//        String[][] tableData = new String[userDao.getAllUsers().size()][];
//        for(int i=0;i<userDao.getAllUsers().size();i++){
//            tableData[i]= new String[]{String.valueOf(userDao.getAllUsers().get(i).getUserRank()),
//                    userDao.getAllUsers().get(i).getUserName(),
//                    String.valueOf(userDao.getAllUsers().get(i).getUserScore()),
//                    String.valueOf(userDao.getAllUsers().get(i).getUserTime())};
//        }
//        ArrayAdapter<String[]> arrayAdapter = new ArrayAdapter<String[]>(this,android.R.layout.simple_list_item_1,tableData);
//        scoreTable.setAdapter(arrayAdapter);
//
//        Button deleteButton = findViewById(R.id.deleteButton);
//        deleteButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                int row = scoreTable.getSelectedItemPosition();
//                if(row!=-1){
//                    scoreTable.removeViewAt(row);
//                    try {
//                        userDao.doDelete(userDao.getAllUsers().get(row).getUserRank());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }
}
