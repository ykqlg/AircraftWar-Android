package com.example.testpro.application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
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
    private Socket socket = MainActivity.socket;
    private PrintWriter writer = MainActivity.writer;
    private String content = "";
    private int flag=0;
    private String name;
    private String pwd01;
    private String pwd02;


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
                    if(content.equals("yes")){
                        System.out.println("register success");
                        Looper.prepare();
                        userNameEmpty();
                        Looper.loop();
                    }else {
                        Looper.prepare();
                        userNameExist();
                        Looper.loop();
                    }

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //联网
//        new Thread(new RegisterActivity.NetConn()).start();

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
                name = usename.getText().toString();				//用户名
                pwd01 = usepwd.getText().toString();				//密码
                pwd02 = usepwd2.getText().toString();			//二次输入的密码
                if(name.equals("")||pwd01 .equals("")||pwd02.equals("")){
                    Toast.makeText(RegisterActivity.this, "用户名或密码不能为空!！", Toast.LENGTH_LONG).show();
                }
                else{
                    new Thread(){
                        @Override
                        public void run(){
                            writer.println("register");
                            writer.println(name);
                            writer.println(pwd01);

                            //等待服务器传回“是否注册成功”判断
                            new Thread(new RegisterActivity.Client(socket)).start();

                        }
                    }.start();
                }
            }


        });
    }

    private void userNameEmpty(){

        if (pwd01.equals(pwd02)) {		//判断两次输入的密码是否一致，若一致则继续，不一致则提醒密码不一致
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

    private void userNameExist() {
        Toast.makeText(RegisterActivity.this, "用户已存在！", Toast.LENGTH_LONG).show();			//提示密码不一致

    }

}
