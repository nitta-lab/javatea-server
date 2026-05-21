package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class UserRepository {
    private final HashMap<String, User> userMap = new HashMap<>(); //User情報を持つHashMap

    //test用(起動時にユーザ登録)
    public UserRepository(){
        User user0 = new User("test01", "testUser01", "testpass01");
        userMap.put(user0.getUid(), user0);
    }

    //Userクラスの作成
    public User createUser(String uid, String name, String pw){
        User u = new User(uid, name, pw);
        userMap.put(uid, u);
        return u;
    }

    //Userクラスの取得
    public User getUser(String uid){
        return userMap.get(uid);
    }

    //Userが存在するかを返す(存在しない場合false)
    public boolean checkDuplicate(String uid){
        return userMap.containsKey(uid);
    }

    //Tokenの生成
    public String createToken(String uid){
        return null;
    }
}
