package com.example.testpro.application;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.MainActivity;
import com.example.testpro.R;
import com.example.testpro.user_dao.User;
import com.example.testpro.user_dao.UserDao;
import com.example.testpro.user_dao.UserDaoImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ScoreTableActivity extends AppCompatActivity {

    ListView scoreTable;

    private int id;
    private List<User> users;
    private Socket socket = MainActivity.socket;
    private PrintWriter writer = MainActivity.writer;
    private String content = "";
    private int flag=0;
    private String [] sTableData = new String[3];

    private ArrayList<String> strArray = new ArrayList ();
    private ArrayList<String>tableData = new ArrayList ();


    class Client implements Runnable{
        private BufferedReader in = null;

        public Client(Socket socket){
            try{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }catch (IOException ex){ex.printStackTrace();}
        }
        @Override
        public void run() {
            try {
                while ((content = in.readLine()) != null) {
//                    if(content.equals("yes")){
//                        System.out.println("scoreTable success");
//                        while ((content = in.readLine()) != null) {
//                            System.out.println(content);
//                            tableData.add(content);
//                        }
//                    }else {}
                    tableData.add(content);

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new Thread(){
            @Override
            public void run(){
                writer.println("scoreTable");
                writer.println(String.valueOf(GameView.score));
                writer.println("whatever");

                new Thread(new ScoreTableActivity.Client(socket)).start();

            }
        }.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoretable);
        scoreTable=(ListView) findViewById(R.id.scoreTable);

//        UserDao userDao=new UserDaoImpl(this);
//        users = userDao.getAllUsers();


//        for(User user:users){
//            String message = user.getUserRank()+" "
//                    +user.getUserName()+" "
//                    +user.getUserScore()+" "
//                    +user.getUserTime();
//            tableData.add(message);
//        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tableData);
        scoreTable.setAdapter(arrayAdapter);


        scoreTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id = (int)l;

            }
        });

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                    userDao.doDelete(id+1);
//                    users = userDao.getAllUsers();
//                tableData.remove(id);
                for(int i = tableData.size()-1;i>-1;i--){
                    tableData.remove(i);
                }
                new Thread(){
                    @Override
                    public void run(){
                        writer.println("scoreTableDelete");
                        writer.println(String.valueOf(id));
                        writer.println("whatever");

                        new Thread(new ScoreTableActivity.Client(socket)).start();

                    }
                }.start();
//                    for(User user:users){
//                        String message = user.getUserRank()+" "
//                                +user.getUserName()+" "
//                                +user.getUserScore()+" "
//                                +user.getUserTime();
//                        tableData.add(message);
//                    }

                arrayAdapter.notifyDataSetChanged();

            }
        });
    }

}
