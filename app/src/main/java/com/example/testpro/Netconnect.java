package com.example.testpro;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Netconnect extends Thread{
    private Socket socket;
    private PrintWriter writer;
    private String content = "";
    @Override
    public void run(){
        try{
            socket = new Socket();

//                //运行时修改成服务器的IP
//                socket.connect(new InetSocketAddress
//                        ("10.250.190.13",9999),5000);
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
