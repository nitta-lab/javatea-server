package org.nittalab.javateaserver.models;
import java.util.*;
public class Timetable {
    private String uid;
    private int year;
    private HashMap<String,HashMap<Integer,String>> timetable = new HashMap<>();
    private HashSet<String> lectureIds = new HashSet<>();

    public Timetable(String uid,int year){
        this.uid = uid;
        this.year = year;
    }

    public int getYear(){
        return year;
    }

    public String getUid(){
        return uid;
    }

    public ArrayList<String> getLectureIds(){
        return new ArrayList<>(lectureIds);
    }

    public void setLectureId(String lectureId) {
        lectureIds.add(lectureId);
    }

    public void deleteLectureId(String lectureId){
        lectureIds.remove(lectureId);
    }
}

