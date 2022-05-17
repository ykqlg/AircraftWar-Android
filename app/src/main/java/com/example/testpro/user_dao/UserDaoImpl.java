package com.example.testpro.user_dao;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private List<User> users;


    public static String fileName="datum.dat";

    public UserDaoImpl() {
        users = new ArrayList<>();
        try {

            File f = new File(fileName);
            InputStream in = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(in);
            this.users = (List<User>)ois.readObject();
            ois.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        }

    }
    @Override
    public List<User> getAllUsers(){
        //根据分数进行从大到小排序
        Collections.sort(users, (o1, o2) -> {
            if(o1.getUserScore()!= o2.getUserScore()){
                return o2.getUserScore()-o1.getUserScore();
            }
            else{
                return o1.getUserTime().compareTo(o2.getUserTime());
            }
        });
        //对排序后的结果，使用遍历去设置排名
        int rank=1;
        for(int i=0; i<users.size();i++){
            User user = users.get(i);
            user.setUserRank(rank);
            rank++;
        }
        return users;
    }

    @Override
    public void doAdd(User user) throws IOException {
        users.add(user);
        Log.i("table","[");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        Log.i("table","]");
        oos.writeObject(users);
        oos.close();
    }

    @Override
    public void doDelete(int rank) throws IOException {
        int size = users.size();
        for(int i = size-1;i>=0;i--){
            User targetUser = users.get(i);
            if(targetUser.getUserRank() == rank){
                users.remove(targetUser);
            }
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(users);
        oos.close();
    }
}
