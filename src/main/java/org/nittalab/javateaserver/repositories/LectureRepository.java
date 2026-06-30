package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.Lecture;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public class LectureRepository {

    private HashMap<String, Lecture> lectureMap = new HashMap<>();
    public LectureRepository(){
        createLecture("オブジェクト指向プログラミング", 3, "前期", 1, "月", 2);
        createLecture("ソフトウェア工学", 3, "前期", 1, "火", 2);
        createLecture("ロボティクス", 3, "前期", 1, "水", 2);
        createLecture("コンパイラインタプリタ", 3, "後期", 1, "水", 5);
        createLecture("基礎体育学演習", 1, "通年", 2, "木", 1);
        createLecture("AI・データサイエンス入門", 1, "その他", 1, "土", 3);
    }
    //授業の追加
    public String createLecture(String name, int grade, String semester, int frame, String day, int period) {
        Set<String> keyList = lectureMap.keySet();
        int num = keyList.size() + 1;
        String lectureId = "Lecture-id" + num;
        lectureMap.put(lectureId, new Lecture(name, grade, semester, frame, day, period, lectureId));
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