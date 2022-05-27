package com.example.testpro.application;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testpro.MainActivity;
import com.example.testpro.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RegisterActivity extends AppCompatActivity {
    EditText usename,usepwd,usepwd2;
    Button submit;
    SharedPreferences sp;
    private Socket socket;
    private PrintWriter writer;
    private String content = "";
    private int flag=0;


    protected class NetConn extends Thread{
        @Override
        public void run(){
            try{
                socket = new Socket();

                //郑皓文的电脑ip地址
                socket.connect(new InetSocketAddress
                        ("10.250.123.219",9999),5000);

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
        private BufferedReader in = null;

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
                        System.out.println("login success");
                        flag = 1;
                        socket.shutdownInput();
                        socket.shutdownOutput();
                        socket.close();
                    }else {flag = 0;}
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //联网
        new Thread(new RegisterActivity.NetConn()).start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usename = this.findViewById(R.id.usename);			    //用户名编辑框
        usepwd =  this.findViewById(R.id.usepwd);				//设置初始密码编辑框
        usepwd2 = this.findViewById(R.id.usepwd2);			    //二次输入密码编辑框
        submit =  this.findViewById(R.id.submit);				//注册按钮
        sp = this.getSharedPreferences("useinfo",this.MODE_PRIVATE);


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = usename.getText().toString();				//用户名
                String pwd01 = usepwd.getText().toString();				//密码
                String pwd02 = usepwd2.getText().toString();			//二次输入的密码
                if(name.equals("")||pwd01 .equals("")||pwd02.equals("")){
                    Toast.makeText(RegisterActivity.this, "用户名或密码不能为空!！", Toast.LENGTH_LONG).show();
                }
                else{
//                    Cursor cursor = db.query("logins",new String[]{"usname"},null,null,null,null,null);
//
//                    while (cursor.moveToNext()){
//                        if(cursor.getString(0).equals(name)){
//                            flag = false;
//                            break;
//                        }
//                    }
                    new Thread(){
                        @Override
                        public void run(){
                            writer.println("register");
                            writer.println(name);
                            writer.println(pwd01);

                            //等待服务器传回“是否注册成功”判断
                            while(true){
                                new Thread(new RegisterActivity.Client(socket)).start();
                            }
                        }
                    }.start();
                    if(flag==1){                                             //判断用户是否已存在
                        if (pwd01.equals(pwd02)) {								//判断两次输入的密码是否一致，若一致则继续，不一致则提醒密码不一致

//                            ContentValues cv = new ContentValues();
//                            cv.put("usname",name);
//                            cv.put("uspwd",pwd01);
//                            db.insert("logins",null,cv);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("usname",name);
                            editor.putString("uspwd",pwd01);
                            editor.commit();
                            Intent intent = new Intent();
                            intent.setClass(RegisterActivity.this, MainActivity.class);      //跳转到登录页面
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "密码不一致！", Toast.LENGTH_LONG).show();			//提示密码不一致
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "用户已存在！", Toast.LENGTH_LONG).show();			//提示密码不一致
                    }

                }
            }


        });
    }
}
