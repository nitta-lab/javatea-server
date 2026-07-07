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
        user01.setUniversity("甲南大学");
        user01.setFaculty("知能情報学部");

        User user02 = createUser("test02", "testUser02", "testPass02");
        user02.setToken("testToken02");
        user02.setUniversity("神戸大学");
        user02.setFaculty("工学部");

        User user03 = createUser("test03", "testUser03", "testPass03");
        user03.setToken("testToken03");
        user03.setUniversity("甲南大学");
        user03.setFaculty("経営学部");

        User user04 = createUser("test04", "testUser04", "testPass04");
        user04.setToken("testToken04");
        user04.setUniversity("甲南大学");
        user04.setFaculty("知能情報学部");
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
