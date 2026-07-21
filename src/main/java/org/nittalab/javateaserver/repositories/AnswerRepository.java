package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.Answer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Set;

@Repository
public class AnswerRepository {

    private HashMap<String, HashMap <String, Answer>> answers = new HashMap<>(); //(qid, (aid, answer))という入れ子構造

    public AnswerRepository() {
        // test用
        createAnswer("qid1", "×まず図書館の中に入ってください。そのあと、左手に向かって進めばとりあえずパソコンはあります", "test02");
        createAnswer("qid1", "〇aid2分からん。すまんかった。", "test03");
        createAnswer("qid2", "×やっぱカレーでーす。以上でーす", "test04");
        createAnswer("qid2", "〇aid3実は…うどんが一番うまいんです！うどんならどれでもおいしいよ！", "test03");
        createAnswer("qid3", "×オーバーライドは子供が親を上書きすんねん！", "test03");
        createAnswer("qid3", "〇aid4共通の機能を持つ「親クラス」を引き継ぎつつ、特定の「子クラス」だけ一部の動作を変えたい場合に、同じ名前のメソッドを再定義して元の機能を上書きします。", "test04");
        createAnswer("qid4", "×分かりません。すまんかったです。", "test02");
        createAnswer("qid4", "〇aid5あるクラス（設計図）が持っている属性や機能を、別の新しいクラスに引き継がせる仕組み", "test04");
    }

    public Answer createAnswer(String qid, String body, String uid) { //解答の作成
        if(!answers.containsKey(qid)){
            answers.put(qid, new HashMap<>());
        }

        Set<String> keyList = answers.get(qid).keySet();
//        Set<String> keyList = answers.keySet();

        int size = keyList.size() + 1;
        String aid = "aid" + size;

        if(!answers.get(qid).containsKey(uid)){
            answers.get(qid).put(aid, new Answer(aid, body, uid));
        }

        return answers.get(qid).get(uid);


//        HashMap<String, Answer> answer = new HashMap<>();
//
//        answer.put(aid, new Answer(aid, body, uid));
//        answers.put(qid, answer);
//
//        return answers.get(qid).get(aid);
    }

    public HashMap <String, Answer> getAnswers(String qid) { //質問に紐づく解答一覧を返す
        if (!answers.containsKey(qid)) {
            return null;
        }

        return answers.get(qid);
    }

    public Answer getAnswer(String qid, String aid) { //aidに紐づく解答単体を返す
        if (!answers.get(qid).containsKey(aid)) {
            return null;
        }

        return answers.get(qid).get(aid);
    }
}
