package org.nittalab.javateaserver.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestTimetableRepository {

    @Test
    void TimetableRepositoryTest() {

//        //年度の追加と年度の確認のテスト
//        TimetableRepository timetableRepository = new TimetableRepository();
//        timetableRepository.createTimetable("001", 2025);
//        timetableRepository.createTimetable("001", 2025);
//        timetableRepository.createTimetable("001", 2026);
//        timetableRepository.createTimetable("001", 2027);
//        timetableRepository.createTimetable("002", 2025);
//        timetableRepository.createTimetable("003", 2025);
//        timetableRepository.createTimetable("004", 2025);
//        HashSet<Integer> expectYears = new HashSet<>();
//        expectYears.add(2025);
//        expectYears.add(2026);
//        expectYears.add(2027);
//        System.out.println(expectYears);
//        assertEquals(timetableRepository.getTimetable("001").keySet(), expectYears);
//
//        //授業の追加と確認のテスト
//        timetableRepository.addLectureId("001", 2025, "001",);
//        timetableRepository.addLectureId("001", 2025, "002");
//        timetableRepository.addLectureId("001", 2025, "003");
//        timetableRepository.addLectureId("001", 2026, "001");
//        ArrayList<String> expectLectureIds2025 = new ArrayList<>();
//        expectLectureIds2025.add("001");
//        expectLectureIds2025.add("002");
//        expectLectureIds2025.add("003");
//        System.out.println(expectLectureIds2025);
//        ArrayList<String> expectLectureIds2026 = new ArrayList<>();
//        expectLectureIds2026.add("001");
//        System.out.println(expectLectureIds2026);
//        assertEquals(timetableRepository.getLectureIds("001", 2025), expectLectureIds2025);
//        assertEquals(timetableRepository.getLectureIds("001", 2026), expectLectureIds2026);
//
//        //授業の削除のテスト
//        timetableRepository.deleteLectureId("001", 2026, "001");
//        expectLectureIds2026 = new ArrayList<>();
//        assertEquals(timetableRepository.getLectureIds("001", 2026), expectLectureIds2026);
    }
}
