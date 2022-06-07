package com.example.testpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testpro.application.ModeItemActivity;
import com.example.testpro.application.Mysql;
import com.example.testpro.application.OnlineOrNotActivity;
import com.example.testpro.application.RegisterActivity;
import com.example.testpro.user_dao.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    public static Socket socket;
    public static PrintWriter writer;
    public static BufferedReader in = null;
    private String content = "";

    protected class NetConn extends Thread{
        @Override
        public void run(){
            try{
                socket = new Socket();

//                //运行时修改成服务器的IP
                //刘培源的电脑ip地址
                socket.connect(new InetSocketAddress
                        ("10.250.66.62",9999),5000);
                //郑皓文的电脑ip地址
//                socket.connect(new InetSocketAddress
//                        ("10.250.123.219",9999),5000);

                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),"UTF-8")),true);


            }catch(UnknownHostException ex){
                ex.printStackTrace();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    class Client implements Runnable{
        private Socket socket;
//        private BufferedReader in = null;

        public Client(Socket socket){
            this.socket = socket;
            try{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {

            try {
                while ((content = in.readLine()) != null) {
                    if(content.equals("yes")){
                        System.out.println("login success");
                        Looper.prepare();
                        loginSuccess();
                        Looper.loop();
                    }else {
                        Looper.prepare();
                        loginFailed();
                        Looper.loop();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    EditText name,pwd;
    Button btnlogin,btnreg;
    SharedPreferences sp1,sp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //联网
        new Thread(new NetConn()).start();

        getScreenHW();
        name = this.findViewById(R.id.name);            //用户名输入框
        pwd = this.findViewById(R.id.pwd);              //密码输入框
        btnlogin = this.findViewById(R.id.login);         //登录按钮
        btnreg = this.findViewById(R.id.reg);               //注册按钮
        sp1 =  this.getSharedPreferences("useinfo",this.MODE_PRIVATE);
        sp2 = this.getSharedPreferences("username",this.MODE_PRIVATE);

        name.setText(sp1.getString("usname",null));
        pwd.setText(sp1.getString("uspwd",null));

        btnlogin.setOnClickListener(new View.OnClickListener() {                //登录事件
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String password = pwd.getText().toString();                 //获取用户输入的用户名和密码

                //创建新线程，将用户名和密码传到服务器
                new Thread(){
                    @Override
                    public void run(){
                        writer.println("login");
                        writer.println(username);
                        writer.println(password);
                        //等待服务器传回“是否登录成功”判断
                        new Thread(new Client(socket)).start();

                    }
                }.start();


            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {                  //注册事件
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);          //跳转到注册页面
                startActivity(intent);
                Toast.makeText(MainActivity.this,"前往注册！", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public static int screenWidth;
    public static int screenHeight;

    public void getScreenHW(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口宽度
        screenWidth = dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;
    }

    private void loginSuccess(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, OnlineOrNotActivity.class);
        startActivity(intent);

//        try{
//            socket.shutdownInput();
//            socket.shutdownOutput();
//            socket.close();
//        }catch(IOException e){
//            e.printStackTrace();
//        }

    }

    private void loginFailed() {
        Toast.makeText(MainActivity.this,"用户名或密码错误！",Toast.LENGTH_LONG).show();             //提示用户信息错误或没有账号
//        try{
//            socket.shutdownInput();
//            socket.shutdownOutput();
//            socket.close();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
    }
}
