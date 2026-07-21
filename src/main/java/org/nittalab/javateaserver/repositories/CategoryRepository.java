package org.nittalab.javateaserver.repositories;
//完成
import org.nittalab.javateaserver.models.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class CategoryRepository {

    // test用(起動時にユーザ登録)
    public CategoryRepository() {
        University university1 = createUniversity("甲南大学", "コウナンダイガク");
        Faculty faculty1 = university1.createFaculty("知能情報学部");
        Department department1 = faculty1.createDepartment("知能情報学科");
        university1.addLecture("Lecture-id1", new Lecture("オブジェクト指向プログラミング", 3, "前期", 1, "月", 2, "Lecture-id1"));
        faculty1.addLecture("Lecture-id2", new Lecture("ソフトウェア工学", 3, "前期", 1, "火", 2, "Lecture-id2"));
        department1.addLecture("Lecture-id3", new Lecture("ロボティクス", 3, "前期", 1, "水", 2,  "Lecture-id3"));
        university1.addQuestion(new Question("図書館の使い方について", "図書館の蔵書検索のパソコンの使い方を教えてください", "test01", new ArrayList<String>(List.of("甲南大学","図書館")), "だれでも", "だれでも", "qid1"));
        university1.addQuestion(new Question("食堂のおすすめメニュー", "食堂の一番美味いやつを教えろください", "test01", new ArrayList<String>(List.of("甲南大学","食堂")), "同じ大学", "だれでも", "qid2"));
        Lecture lecture1 = university1.getLecture("Lecture-id1");
        lecture1.addQuestion(new Question("オーバーライドとは", "オーバーライドって何ですか", "test04", new ArrayList<String>(List.of("オブジェクト指向")), "だれでも", "だれでも", "qid3"));
        lecture1.addQuestion(new Question("継承とは", "継承って何ですか", "test04", new ArrayList<String>(List.of("オブジェクト指向")), "同じ大学", "同じ学部", "qid4"));
        lecture1.addQuestion(new Question("クラス図のおいしい作り方を教えてください", "継承って何ですか", "test01", new ArrayList<String>(List.of("オブジェクト指向")), "同じ学部", "同じ学部", "qid9"));
        Lecture lecture2 = faculty1.getLecture("Lecture-id2");
        lecture2.addQuestion(new Question("クラス図の書き方", "クラス図ってどうやって書けばいいんでしょうか。むずかちい", "test04", new ArrayList<String>(List.of("ソフトウェア工学","楽単")), "だれでも", "だれでも", "qid5"));
        lecture2.addQuestion(new Question("大規模アプリケーションの食べ方", "クラス図ってどうやって書けばいいんでしょうか。むずかちい", "test04", new ArrayList<String>(List.of("ソフトウェア工学","楽単")), "同じ学部", "同じ学部", "qid6"));
        lecture2.addQuestion(new Question("おいしいプログラミング言語はありますか", "クラス図ってどうやって書けばいいんでしょうか。むずかちい", "test04", new ArrayList<String>(List.of("ソフトウェア工学","楽単")), "同じ大学", "同じ大学", "qid10"));
        Lecture lecture3 = department1.getLecture("Lecture-id3");
        lecture3.addQuestion(new Question("この授業は簡単ですか", "単位マジでやばいんで、これが楽単かどうか教えてください", "test04", new ArrayList<String>(List.of("ロボティクス","楽単")), "だれでも", "だれでも", "qid7"));
        lecture3.addQuestion(new Question("このテストの持ち込みについて", "この授業って持ち込みありですか", "test04", new ArrayList<String>(List.of("ロボティクス","テスト")), "同じ学部", "同じ学部", "qid8"));
        lecture3.addQuestion(new Question("ロボットを壊すとどうなるの", "この授業って持ち込みありですか", "test04", new ArrayList<String>(List.of("ロボティクス","テスト")), "同じ大学", "同じ大学", "qid11"));
    }

    //関数は動詞名詞
    //getは取ってくる(変更はなし)、setは設定(まるごと入れ替え)、putは一部書き換え,removeは削除
    //addは引数に渡している場合、createは関数の中で新しく作ったものを追加する場合

    private Set<Question> generalQuestions = new HashSet<>();

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

    // 【全般】の質問一覧
    public Set<Question> getGeneralQuestions() {
        return generalQuestions;
    }

    // 【全般】の質問一覧
    public void addGeneralQuestion(Question question) {
        generalQuestions.add(question);
    }
}