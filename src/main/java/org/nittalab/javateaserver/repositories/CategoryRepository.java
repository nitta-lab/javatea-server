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
    private record universitiesRecord(String name,String kana, char form, char to){}
    HashMap<universitiesRecord,String> universitiesRecords = new HashMap<>();
    public University createUniversity(String name, String kana) {
        int size = universities.size()+1;
        char from;
        char to;
        char firstChar = kana.charAt(0);

        //全角カタカナでなければ、nullを返す。
        for(int i=0;i<kana.length();i++){
            if(!(kana.charAt(i) >= 0x30a1 && kana.charAt(i) <= 0x30ff)){
                return null;
            }
        }


        if(firstChar >= 'ア' && firstChar < 'カ') {
            from = 'ア';
            to = 'カ';
        } else if (firstChar >= 'カ' && firstChar < 'サ') {
            from = 'カ';
            to = 'サ';
        } else if (firstChar >= 'サ' && firstChar < 'タ') {
            from = 'サ';
            to = 'タ';
        } else if (firstChar >= 'タ' && firstChar < 'ナ') {
            from = 'タ';
            to = 'ナ';
        } else if (firstChar >= 'ナ' && firstChar < 'ハ') {
            from = 'ナ';
            to = 'ハ';
        } else if (firstChar >= 'ハ' && firstChar < 'マ') {
            from = 'ハ';
            to = 'マ';
        } else if (firstChar >= 'マ' && firstChar < 'ヤ') {
            from = 'マ';
            to = 'ヤ';
        } else if (firstChar >= 'ヤ' && firstChar < 'ラ') {
            from = 'ヤ';
            to = 'ラ';
        } else if (firstChar >= 'ラ' && firstChar < 'ワ') {
            from = 'ラ';
            to = 'ワ';
        } else if (firstChar >= 'ワ') {
            from = 'ワ';
            to = 'ン';
        } else {
            from = '？';
            to = '？';
        }

        //大学名と読み仮名の同じものが存在したときは、その大学を返す。
        if(universitiesRecords.containsKey(new universitiesRecord(name,kana,from,to))){
            return getUniversity(universitiesRecords.get(new universitiesRecord(name,kana,from,to)));
        }
        String univ_id = "univ-id" + size;
        universities.put(univ_id,new University(univ_id, name, kana)); //universityに大学が追加される。putは追加
        universitiesRecords.put(new universitiesRecord(name,kana,from,to),univ_id);
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

//    public SortedMap<String, University> getUniversitiesByKana(String from, String to) {
//        return universities.subMap(from,to);
//    }
// Javaの頭の中
    public SortedMap<String, University> getUniversitiesByKana(String from, String to) {
        // 1. ア行の大学だけを入れる「一時的な空のリスト」をその場で作る
        SortedMap<String, University> filteredMap = new TreeMap<>();

        // 2. ごちゃ混ぜに保存されている全大学を、1つずつチェックする
        for (University univ : universities.values()) {
            String kana = univ.getKana(); // 「オオサカダイガク」を取り出す

            // 3. これ、ア（from）から カ（to）の間に挟まってるかな？ と計算
            if (kana.compareTo(from) >= 0 && kana.compareTo(to) < 0) {
                // 4. 条件に合えば、ア行の一時リストに代入する！
                filteredMap.put(univ.getId(), univ);
            }
        }

        // 5. 仕分け終わった「ア行だけのリスト」を画面に送り返す
        return filteredMap;
    }
}