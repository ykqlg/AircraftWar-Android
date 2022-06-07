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

//    protected class NetConn extends Thread{
//        @Override
//        public void run(){
//            try{
//                socket = new Socket();
//
//                //郑皓文的电脑ip地址
//                socket.connect(new InetSocketAddress
//                        ("10.250.123.219",9999),5000);
//
//                writer = new PrintWriter(new BufferedWriter(
//                        new OutputStreamWriter(
//                                socket.getOutputStream(),"UTF-8")),true);
//
//
//            }catch(UnknownHostException ex){
//                ex.printStackTrace();
//            }catch(IOException ex){
//                ex.printStackTrace();
//            }
//        }
//    }

    class Client implements Runnable{
        private Socket socket;
        private BufferedReader in = null;
        int i = 0;

        public Client(Socket socket){
            this.socket = socket;
            try{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }catch (IOException ex){ex.printStackTrace();}
        }
        @Override
        public void run() {
            try {
                while ((content = in.readLine()) != null) {
                    if(content.equals("yes")){
                        System.out.println("scoreTable success");
                        while ((content = in.readLine()) != null) {
                            tableData.add(content);
                        }
//                        showScoreTable();

                    }else {
//                        flag = 0;

                    }
//                    strArray.add(content);这是什么？？

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //联网
//        new Thread(new ScoreTableActivity.NetConn()).start();
        new Thread(){
//            NetConn netConn = new ScoreTableActivity.NetConn();
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

        UserDao userDao=new UserDaoImpl(this);
        users = userDao.getAllUsers();


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

    public void showScoreTable(){

        try{
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
