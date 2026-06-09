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

    private TreeMap<String, University> universities =  new TreeMap<>();
    private record universitiesRecord(String name,String kana){}
    HashMap<universitiesRecord,String> universitiesRecords = new HashMap<>();
    public University createUniversity(String name, String kana) {
        int size = universities.size()+1;
        //全角カタカナでなければ、nullを返す。
        for(int i=0;i<kana.length();i++){
            if(!(kana.charAt(i) >= 0x30a1 && kana.charAt(i) <= 0x30ff)){
                return null;
            }
        }
        //大学名と読み仮名の同じものが存在したときは、その大学を返す。
        if(universitiesRecords.containsKey(new universitiesRecord(name,kana))){
            return getUniversity(universitiesRecords.get(new universitiesRecord(name,kana)));
        }
        String univ_id = "univ-id" + size;
        universities.put(univ_id,new University(univ_id, name, kana)); //universityに大学が追加される。putは追加
        universitiesRecords.put(new universitiesRecord(name,kana),univ_id);
        return universities.get(univ_id); //getは取得。(Pythonのd[key])
    }

    public University getUniversity(String univ_id) {
        if (!universities.containsKey(univ_id)){ //univ-idが登録されていなければ、nullを返す。
            return null;
        }
        return universities.get(univ_id);
    }

    //大学一覧を取得
    public SortedMap<String, University> getUniversities() {
        return universities;
    }

    public SortedMap<String, University> getUniversitiesByKana(String from, String to) {
        return universities.subMap(from,to);
    }
}