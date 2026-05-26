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
        User user01 = createUser("test01", "testUser01", "testPass01");
        user01.setToken("testToken01");
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

    //Userが重複していないかを調べる(重複している場合True)
    public boolean checkDuplicate(String uid){
        return userMap.containsKey(uid);
    }

    //Tokenの生成(生成したTokenを返す)
    public String createToken(String uid){
        userMap.get(uid).setToken(UUID.randomUUID().toString());
        return userMap.get(uid).getToken();
    }

    //Tokenの削除
    public void deleteToken(String uid){
        userMap.get(uid).setToken(null);
    }
}
