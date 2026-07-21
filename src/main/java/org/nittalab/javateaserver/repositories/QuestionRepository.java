package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public class QuestionRepository {

    private HashMap<String, Question> questionMap = new HashMap<>();

    public QuestionRepository() {
        // test用
        createQuestion("図書館の使い方について", "図書館の蔵書検索のパソコンの使い方を教えてください", "test01", new ArrayList<String>(List.of("甲南大学","図書館")), "だれでも", "だれでも"); // qid1
        createQuestion("食堂のおすすめメニュー", "食堂の一番美味いやつを教えろください", "test01", new ArrayList<String>(List.of("甲南大学","食堂")), "同じ大学", "だれでも"); // qid2
        createQuestion("オーバーライドとは", "オーバーライドって何ですか", "test04", new ArrayList<String>(List.of("オブジェクト指向")), "だれでも", "だれでも"); // qid3
        createQuestion("継承とは", "継承って何ですか", "test04", new ArrayList<String>(List.of("オブジェクト指向")), "同じ大学", "同じ学部"); // qid4
        createQuestion("クラス図の書き方", "クラス図ってどうやって書けばいいんでしょうか。むずかちい", "test04", new ArrayList<String>(List.of("ソフトウェア工学","楽単")), "だれでも", "だれでも"); // qid5
        createQuestion("大規模アプリケーションの食べ方", "クラス図ってどうやって書けばいいんでしょうか。むずかちい", "test04", new ArrayList<String>(List.of("ソフトウェア工学","楽単")), "同じ学部", "同じ学部"); // qid6
        createQuestion("この授業は簡単ですか", "単位マジでやばいんで、これが楽単かどうか教えてください", "test04", new ArrayList<String>(List.of("ロボティクス","楽単")), "だれでも", "だれでも"); // qid7
        createQuestion("このテストの持ち込みについて", "この授業って持ち込みありですか", "test04", new ArrayList<String>(List.of("ロボティクス","テスト")), "同じ学部", "同じ学部"); // qid8
        createQuestion("クラス図のおいしい作り方を教えてください", "継承って何ですか", "test01", new ArrayList<String>(List.of("オブジェクト指向")), "同じ学部", "同じ学部"); // qid9
        createQuestion("おいしいプログラミング言語はありますか", "クラス図ってどうやって書けばいいんでしょうか。むずかちい", "test04", new ArrayList<String>(List.of("ソフトウェア工学","楽単")), "同じ大学", "同じ大学"); // qid10
        createQuestion("ロボットを壊すとどうなるの", "この授業って持ち込みありですか", "test04", new ArrayList<String>(List.of("ロボティクス","テスト")), "同じ大学", "同じ大学"); // qid11
    }

    //質問の追加
    public String createQuestion(String title, String body, String uid, List<String> tags, String viewPermission, String resPermission) {
        Set<String> keyList = questionMap.keySet();
        int num = keyList.size() + 1;
        String qid = "qid" + num;
        questionMap.put(qid, new Question(title, body, uid, tags, viewPermission, resPermission, qid));
        return qid;
    }

    public Question getQuestion(String qid) {
        if(!questionMap.containsKey(qid)) {
            return null;
        }
        return questionMap.get(qid);
    }
}