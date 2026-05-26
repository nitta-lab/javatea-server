package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.Lecture;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public class LectureRepository {

    private HashMap<String, Lecture> lectureMap = new HashMap<>();

    //授業の追加
    public String createLecture(String name, int grade, String semester, int frame, String day, int period) {
        Set<String> keyList = lectureMap.keySet();
        int num = keyList.size() + 1;
        String lectureId = "Lecture-id" + num;
        lectureMap.put(lectureId, new Lecture(name, grade, semester, frame, day, period));
        return lectureId;
    }

    //授業の取得
    public Lecture getLecture(String lectureId) {
        if (!lectureMap.containsKey(lectureId)) {
            return null;
        }
        return lectureMap.get(lectureId);
    }

}