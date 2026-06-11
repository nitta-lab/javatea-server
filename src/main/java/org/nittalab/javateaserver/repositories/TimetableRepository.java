package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.Timetable;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TimetableRepository {
    //タイムテーブル情報を保存するHashMap
    private HashMap<String, TreeMap<Integer, Timetable>> timetableMap = new HashMap<>();

    //ユーザのタイムテーブルを作成してTimetable
    //ユーザが存在するかどうかはuserRepositoryに丸投げ予定
    private Timetable getTimetableMap(String uid, int year) {
        if (!timetableMap.containsKey(uid)) {
            return null;
        }
        if (!timetableMap.get(uid).containsKey(year)) {
            return null;
        }
        return timetableMap.get(uid).get(year);
    }

    //ユーザが時間割登録をした年度を追加して、その年度のtimetableを作成
    public void createTimetable(String uid, int year) {
        if (!timetableMap.containsKey(uid)) {
            timetableMap.put(uid, new TreeMap<>());
        }
        if (!timetableMap.get(uid).containsKey(year)) {
            timetableMap.get(uid).put(year, new Timetable(uid, year));
        }
    }

    //ユーザが時間割登録をした年度一覧と授業一覧を取得する
    public TreeMap<Integer,HashSet<String>> getTimetable(String uid) {
        TreeMap<Integer,HashSet<String>> userTimetable = new TreeMap<>();

        for(int year : timetableMap.get(uid).keySet()){
            userTimetable.put(year,new HashSet<>(timetableMap.get(uid).get(year).getLectureIds()));
        }

        return userTimetable;
    }

    //ユーザが時間割登録をした年度に登録した授業すべてを取得する
    //年度が存在しなければnullを返す
    public ArrayList<String> getLectureIds(String uid, int year) {
        Timetable timetable = getTimetableMap(uid, year);
        if (timetable == null) {
            return null;
        }
        return timetable.getLectureIds();
    }

    //時間割に授業を追加
    //追加に成功したらtrue
    //年度が存在しなければ false
    public boolean addLectureId(String uid, int year, String lectureId) {
        Timetable timetable = getTimetableMap(uid, year);
        if (timetable == null) {
            return false;
        }
        timetable.addLectureId(lectureId);
        return true;
    }

    //時間割の授業を削除
    //削除に成功したらtrue
    //年度が存在しなければfalse
    public boolean deleteLectureId(String uid, int year, String lectureId) {
        Timetable timetable = getTimetableMap(uid, year);
        if (timetable == null) {
            return false;
        }
        timetable.deleteLectureId(lectureId);
        return true;
    }
}
