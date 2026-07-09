package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.Answer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class AnswerRepository {

    private HashMap<String, HashMap <String, Answer>> answers = new HashMap<>(); //(qid, (aid, answer))という入れ子構造

    public Answer createAnswer(String qid, String body, String uid) {
        int size = answers.size() + 1;
        String aid = "aid" + size;
        HashMap<String, Answer> answer = new HashMap<>();

        answer.put(aid, new Answer(qid, body, uid));
        answers.put(qid, answer);

        return answers.get(qid).get(aid);
    }

    public HashMap <String, Answer> getAnswers(String qid) {
        if (!answers.containsKey(qid)) {
            return null;
        }

        return answers.get(qid);
    }

    public Answer getAnswer(String qid, String aid) {
        if (!answers.get(qid).containsKey(aid)) {
            return null;
        }

        return answers.get(qid).get(aid);
    }
}
