package org.nittalab.javateaserver.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUniversity {

    @Test
    void UniversityTest1(){
        University university = new University("U_001", "大学", "ダイガク");

        //大学情報の取得
        String univId = university.getId();
        assertEquals("U_001", univId);
        String univName = university.getName();
        assertEquals("大学", univName);
        String univKana = university.getKana();
        assertEquals("ダイガク", univKana);

        //大学情報の変更
        university.setName("医科大学");
        String univNameRenamed = university.getKana();
        assertEquals("医科大学", univNameRenamed);
        university.setKana("イカダイガク");
        String univKanaRenamed = university.getKana();
        assertEquals("イカダイガク", univKanaRenamed);
    }

    @Test
    void UniversityTest2(){
        University university = new University("U_001", "大学", "ダイガク");

        //学部の作成、取得
        ArrayList<Faculty> faculties = new ArrayList<>();
        faculties.add(university.createFaculty("A学部"));
        faculties.add(university.createFaculty("B学部"));
        faculties.add(university.createFaculty("C学部"));
        Set<String> facultyNamesExp = new HashSet<>();
        facultyNamesExp.add("A学部");
        facultyNamesExp.add("B学部");
        facultyNamesExp.add("C学部");
        assertEquals(3, faculties.size()); //サイズでの確認
        assertEquals(facultyNamesExp, university.getFaculties()); //学部名での確認
        assertNull(university.createFaculty("A学部")); //重複時の動作確認

        assertEquals(faculties.get(0), university.getFaculty("A学部"));
        assertEquals(faculties.get(1), university.getFaculty("B学部"));
        assertEquals(faculties.get(2), university.getFaculty("C学部"));
        assertNull(university.getFaculty("D学部")); //存在しない学部を叩いた際の確認


        //科目の追加、取得
        HashMap<String, Lecture> lectures = new HashMap<>();
        university.addLecture("L_001", new Lecture("授業A",1, "前期", 1,"月", 1));
        university.addLecture("L_002", new Lecture("授業B",1, "前期", 1,"月", 1));
        university.addLecture("L_003", new Lecture("授業C",1, "前期", 1,"月", 1));
        lectures.put("L_001", university.getLecture("L_001"));
        lectures.put("L_002", university.getLecture("L_002"));
        lectures.put("L_003", university.getLecture("L_003"));
        assertEquals(3, lectures.size()); //サイズでの確認
        assertEquals(lectures, university.getLectures()); //リストを取得できるかの確認

        assertNull(university.getLecture("L_000")); //存在しないIDを叩いた場合の確認
    }

    @Test
    void FacultyTest() {
        Faculty faculty = new Faculty("学部");

        //学部名の取得
        assertEquals("学部", faculty.getFacultyName());

        //学科の作成、取得
        //学部の作成、取得
        ArrayList<Department> departments = new ArrayList<>();
        faculty.createDepartment("A学科");
        faculty.createDepartment("B学科");
        faculty.createDepartment("C学科");
        Set<String> facultyNamesExp = new HashSet<>();
        facultyNamesExp.add("A学部");
        facultyNamesExp.add("B学部");
        facultyNamesExp.add("C学部");
        assertEquals(3, departments.size()); //サイズでの確認
        assertEquals(facultyNamesExp, university.getFaculties()); //学部名での確認
        assertNull(university.createFaculty("A学部")); //重複時の動作確認

        assertEquals(departments.get(0), university.getFaculty("A学部"));
        assertEquals(departments.get(1), university.getFaculty("B学部"));
        assertEquals(departments.get(2), university.getFaculty("C学部"));
        assertNull(university.getFaculty("D学部")); //存在しない学部を叩いた際の確認
    }
}
