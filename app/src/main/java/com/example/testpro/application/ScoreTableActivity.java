package com.example.testpro.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.MainActivity;
import com.example.testpro.R;
import com.example.testpro.user_dao.User;
import com.example.testpro.user_dao.UserDao;
import com.example.testpro.user_dao.UserDaoImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ScoreTableActivity extends AppCompatActivity {

    ListView scoreTable;

    private int id;
    private List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoretable);
        scoreTable=(ListView) findViewById(R.id.scoreTable);

        UserDao userDao=new UserDaoImpl(this);
        users = userDao.getAllUsers();
        ArrayList<String>tableData = new ArrayList<>();

        for(User user:users){
            String message = user.getUserRank()+" "
                    +user.getUserName()+" "
                    +user.getUserScore()+" "
                    +user.getUserTime();
            tableData.add(message);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tableData);
        scoreTable.setAdapter(arrayAdapter);


        scoreTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id = (int)l;

            }
        });



        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    userDao.doDelete(id+1);
                    tableData.remove(id);
                    users = userDao.getAllUsers();
                    for(int i = users.size()-1;i>-1;i--){
                        tableData.remove(i);
                    }
                    for(User user:users){
                        String message = user.getUserRank()+" "
                                +user.getUserName()+" "
                                +user.getUserScore()+" "
                                +user.getUserTime();
                        tableData.add(message);
                    }

                    arrayAdapter.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
