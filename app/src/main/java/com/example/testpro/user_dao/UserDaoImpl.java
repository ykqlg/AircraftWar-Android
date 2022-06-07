package com.example.testpro.user_dao;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private List<User> users;
    Context context;


    public static String fileName="datum.txt";

    public UserDaoImpl(Context context) {
        this.context=context;

        users = new ArrayList<>();
        try {

//            File f = new File(Environment.getExternalStorageDirectory(),fileName);
//            InputStream in = new FileInputStream(f);
            FileInputStream fis = context.openFileInput(fileName);

//            byte[]bytes = new byte[fis.available()];
//            fis.read(bytes);
//            fis.close();
//            System.out.println(bytes);

            ObjectInputStream ois = new ObjectInputStream(fis);
            this.users = (List<User>)ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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

        FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);

        ObjectOutputStream oos = new ObjectOutputStream(fos);
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
        FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(users);
        oos.close();
    }
}
