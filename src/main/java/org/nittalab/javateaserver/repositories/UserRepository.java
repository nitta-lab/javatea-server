package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserRepository {
    private final HashMap<String, User> userMap = new HashMap<>(); //User情報を持つHashMap

    //Userクラスの作成
    public User createUser(String uid, String name, String pw){
        User u = new User(uid, name, pw);
        userMap.put(uid, u);
        return u;
    }

    //Userクラスの取得
    public User getUser(String uid){
        return null;
    }
}
