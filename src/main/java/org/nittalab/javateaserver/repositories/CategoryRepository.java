package org.nittalab.javateaserver.repositories;
//完成
import org.nittalab.javateaserver.models.University;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class CategoryRepository {
    //関数は動詞名詞
    //getは取ってくる(変更はなし)、setは設定(まるごと入れ替え)、putは一部書き換え,removeは削除
    //addは引数に渡している場合、createは関数の中で新しく作ったものを追加する場合

    private HashMap<String, University> universities =  new HashMap<>();

    public University createUniversity(String name, String kana) {
        int size = universities.size()+1;
        universities.put("univ-id"+size,new University("univ-id"+size, name, kana)); //universityに大学が追加される。putは追加
        return universities.get("univ-id"+size); //getは取得。(Pythonのd[key])
    }

    public University getUniversity(String univ_id) {
        if (!universities.containsKey(univ_id)){ //univ-idが登録されていなければ、nullを返す。
            return null;
        }
        return universities.get(univ_id);
    }

    //大学一覧を取得
    public HashMap<String, University> getUniversities() {
        return universities;
    }
}