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
    public static boolean battleFlag = false;


    class Client implements Runnable{
        private BufferedReader in = null;
        public Client(Socket socket){
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

        Switch soundSwitch = findViewById(R.id.soundSwitch);
        Button standAloneButton = findViewById(R.id.standAloneButton);
        Button onlineButton = findViewById(R.id.onlineButton);

        standAloneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(soundSwitch.isChecked()==true){
                    musicFlag = true;
                }
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

                new Thread(){
                    @Override
                    public void run(){
                        writer.println("match");
                        writer.println("whatever");
                        writer.println("whatever");

                        new Thread(new Client(socket)).start();

                    }
                }.start();
            }
        });
    }


    private void matchSuccess(){
        GameView.mode = 3;
        battleFlag = true;
        Intent intent = new Intent();
        intent.setClass(OnlineOrNotActivity.this, GameActivity.class);
        startActivity(intent);

    }
    private void matchFailed(){
        Toast.makeText(OnlineOrNotActivity.this, "匹配失败！", Toast.LENGTH_LONG).show();
    }

}
