package com.example.testpro.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
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

public class OnlineOrNotActivity extends AppCompatActivity {

    private String content = "";
    private Socket socket = MainActivity.socket;
    private PrintWriter writer = MainActivity.writer;

//    protected class NetConn extends Thread{
//        @Override
//        public void run(){
//            try{
//                socket = new Socket();
//
////                //运行时修改成服务器的IP
//                //刘培源的电脑ip地址
//                socket.connect(new InetSocketAddress
//                        ("10.250.66.62",9999),5000);
//                //郑皓文的电脑ip地址
////                socket.connect(new InetSocketAddress
////                        ("10.250.123.219",9999),5000);
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
                        System.out.println("match success");
                        Looper.prepare();
                        matchSuccess();//匹配成功
                        Looper.loop();
                        socket.shutdownInput();
                        socket.shutdownOutput();
                        socket.close();
                    }else {
                        Looper.prepare();
                        matchFailed();//匹配失败
                        Looper.loop();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



    public static boolean musicFlag = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlineornot);

        //联网
//        new Thread(new NetConn()).start();

        Switch soundSwitch = findViewById(R.id.soundSwitch);

        Button standAloneButton = findViewById(R.id.standAloneButton);
        Button onlineButton = findViewById(R.id.onlineButton);

        standAloneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(soundSwitch.isChecked()==true){
                    musicFlag = true;
                }
                GameView.matchFlag=false;
                Intent intent = new Intent();
                intent.setClass(OnlineOrNotActivity.this, ModeItemActivity.class);
                startActivity(intent);
            }
        });
        onlineButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(soundSwitch.isChecked()==true){
                    musicFlag = true;
                }
                Toast.makeText(OnlineOrNotActivity.this,"等待其他玩家加入...",Toast.LENGTH_LONG).show();

                //创建新线程，将匹配信号传到服务器
                new Thread(){
                    @Override
                    public void run(){
                        writer.println("match");
                        writer.println("whatever");
                        writer.println("whatever");

                        //等待服务器传回“是否匹配成功”判断

                        new Thread(new Client(socket)).start();

                    }
                }.start();
            }
        });
    }
    private void matchSuccess(){
        GameView.matchFlag = true;
        Intent intent = new Intent();
        intent.setClass(OnlineOrNotActivity.this, ModeItemActivity.class);
        startActivity(intent);

    }
    private void matchFailed(){
        Toast.makeText(OnlineOrNotActivity.this, "匹配失败！", Toast.LENGTH_LONG).show();
    }

}
