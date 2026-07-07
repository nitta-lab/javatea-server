package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.Question;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public class QuestionRepository {

    private HashMap<String, Question> questionMap = new HashMap<>();

    //質問の追加
    public String createQuestion(String title, String body, String uid, List<String> tags, String viewPermission, String resPermission) {
        Set<String> keyList = questionMap.keySet();
        int num = keyList.size() + 1;
        String qid = "qid" + num;
        questionMap.put(qid, new Question(title, body, uid, tags, viewPermission, resPermission));
        return qid;
    }

    public Question getQuestion(String qid) {
        if(!questionMap.containsKey(qid)) {
            return null;
        }
        return questionMap.get(qid);
    }
}