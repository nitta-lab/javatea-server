package org.nittalab.javateaserver.repositories;

import org.nittalab.javateaserver.models.Timetable;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.LocalDate;

@Repository
public class TimetableRepository {
    //タイムテーブル情報を保存するHashMap
    private HashMap<String,HashMap<Integer,Timetable>> timetableMap = new HashMap<>();
    private static final int curYear = LocalDate.now().getYear();

    //ユーザのタイムテーブルを作成
    //ユーザが存在するかどうかはuserRepositoryに丸投げ予定
    private void setTimetableMap(String uid){
        if(!timetableMap.containsKey(uid)){
            timetableMap.put(uid,new HashMap<>());
            timetableMap.get(uid).put(curYear,new Timetable(uid, curYear));
        }else{
            throw new RuntimeException("指定された" + uid + "が存在しません");
        }
    }

    //ユーザが時間割登録をした年度一覧を取得する
    public ArrayList<Integer> getYears(String uid){
        setTimetableMap(uid);
        return new ArrayList<>(timetableMap.get(uid).keySet());
    }

    //ユーザが時間割登録をした年度に登録した授業すべてを取得する
    public ArrayList<String> getLectureIds(String uid,int year){
        setTimetableMap(uid);
        //年度が存在しないときはnullを返す
        if(!timetableMap.get(uid).containsKey(year)){
            return null;
        }
        return timetableMap.get(uid).get(year).getLectureIds();
    }

    //ユーザが時間割登録をした年度を追加して、その年度のtimetableを作成
    public void addYear(String uid,int year){
        setTimetableMap(uid);
        timetableMap.get(uid).put(year,new Timetable(uid,year));
    }

    //時間割に授業を追加
    public boolean addLectureId(String uid,int year,String lectureId){
        setTimetableMap(uid);
        //yearが無い時の処理(追加失敗)
        if(!timetableMap.get(uid).containsKey(year)){
            return false;
        }
        timetableMap.get(uid).get(year).setLectureId(lectureId);
        //追加に成功
        return true;
    }

    //時間割の授業を削除
    public boolean deleteLectureId(String uid,int year,String lectureId){
        setTimetableMap(uid);
        //yearが無い時の処理(削除失敗)
        if(!timetableMap.get(uid).containsKey(year)){
            return false;
        }
        timetableMap.get(uid).get(year).deleteLectureId(lectureId);
        //削除に成功
        return true;
    }
}
