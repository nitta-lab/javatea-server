package org.nittalab.javateaserver.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestTimetable {

    @Test
    void TimetableTest(){
        Timetable timetable = new Timetable("001",2025);
        //授業の追加と取得
        timetable.addLectureId("001");
        timetable.addLectureId("002");
        timetable.addLectureId("003");
        ArrayList<String> lectures = new ArrayList<>();
        lectures.add("001");
        lectures.add("002");
        lectures.add("003");
        System.out.println(lectures);
        assertEquals(timetable.getLectureIds(),lectures);

        //授業の削除
        timetable.deleteLectureId("001");
        lectures = new ArrayList<>();
        lectures.add("002");
        lectures.add("003");
        System.out.println(lectures);
        assertEquals(timetable.getLectureIds(),lectures);
    }
}
