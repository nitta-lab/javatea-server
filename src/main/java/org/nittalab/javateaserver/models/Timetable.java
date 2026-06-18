package org.nittalab.javateaserver.models;
import java.util.*;
public class Timetable {
    private String uid;
    private int year;
//    private Set<String> lectureIds = new HashSet<>();
    private HashMap<String,Lecture> lectureIds = new HashMap<>();
    private HashMap<String,HashMap<String,HashMap<Integer,String>>> timetable = new HashMap<>();
    private HashSet<String> otherTimetable = new HashSet<>();
    private String[] days = {"月","火","水","木","金"};
    public Timetable(String uid, int year) {
        this.uid = uid;
        this.year = year;
        timetable.put("前期",new HashMap<>());
        timetable.put("後期",new HashMap<>());
        for(int i=0;i<5;i++){
            timetable.get("前期").put(days[i],new HashMap<>());
            timetable.get("後期").put(days[i],new HashMap<>());
            for(int j=1;j<=7;j++){
                timetable.get("前期").get(days[i]).put(j,null);
                timetable.get("後期").get(days[i]).put(j,null);
            }
        }
    }

    public int getYear() {
        return year;
    }

    public String getUid() {
        return uid;
    }

    public ArrayList<String> getLectureIds() {
        return new ArrayList<>(lectureIds.keySet());
    }

    public void addLectureId(String lectureId,Lecture lecture) {
        lectureIds.put(lectureId,lecture);
        //登録する授業の情報
        String semester = lecture.getSemester();
        int frame = lecture.getFrame();
        String day = lecture.getDay();
        int period = lecture.getPeriod();
        if(day.equals("土曜日")||day.equals("日曜日")){
            otherTimetable.add(lectureId);
            return;
        }
        if(period > 7){
            otherTimetable.add(lectureId);
            return;
        }
        if(semester.equals("前期")||semester.equals("後期")){
            changeTimetable(lectureId,semester,frame,day,period);
        }else if(semester.equals("通年")){
            changeTimetable(lectureId,"前期",frame,day,period);
            changeTimetable(lectureId,"後期",frame,day,period);
        }else{//その他の時
            otherTimetable.add(lectureId);
        }
    }
    private void changeTimetable(String lectureId,String semester,int frame,String day,int period){
        for(int i=0;i<frame;i++){
            //現在登録されていた授業
            String curLecture = timetable.get(semester).get(day).get(period+i);
            if(curLecture == null){//もともと登録されてなかったら、普通に登録
                timetable.get(semester).get(day).put(period+i,lectureId);
                continue;
            }
            if(!curLecture.equals(lectureId)){
                //現在登録されていた授業の情報
                String curSemester = lectureIds.get(curLecture).getSemester();
                int curFrame = lectureIds.get(curLecture).getFrame();
                String curDay = lectureIds.get(curLecture).getDay();;
                int curPeriod = lectureIds.get(curLecture).getPeriod();
                //その授業をすべて削除
                if(curSemester.equals("通年")){
                    for(int j=0;j<curFrame;j++){
                        timetable.get("前期").get(curDay).put(curPeriod+j,null);
                        timetable.get("後期").get(curDay).put(curPeriod+j,null);
                    }
                }else{
                    for(int j=0;j<curFrame;j++){
                        timetable.get(curSemester).get(curDay).put(curPeriod+j,null);
                    }
                }
                lectureIds.remove(curLecture);
                //新しい授業を登録
                timetable.get(semester).get(day).put(period+i,lectureId);
            }else{//同じならそのまま
                continue;
            }
        }
    }

    public void deleteLectureId(String curLecture) {
        otherTimetable.remove(curLecture);
        //現在登録されていた授業の情報
        String curSemester = lectureIds.get(curLecture).getSemester();
        int curFrame = lectureIds.get(curLecture).getFrame();
        String curDay = lectureIds.get(curLecture).getDay();;
        int curPeriod = lectureIds.get(curLecture).getPeriod();
        //その授業をすべて削除
        if(curSemester.equals("通年")){
            for(int j=0;j<curFrame;j++){
                timetable.get("前期").get(curDay).put(curPeriod+j,null);
                timetable.get("後期").get(curDay).put(curPeriod+j,null);
            }
        }else{
            for(int j=0;j<curFrame;j++){
                timetable.get(curSemester).get(curDay).put(curPeriod+j,null);
            }
        }
        lectureIds.remove(curLecture);
    }
}

