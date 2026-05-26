package org.nittalab.javateaserver.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUniversity {

    @Test
    void UniverityTest(){
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
        ArrayList<Lecture> lectures = new ArrayList<>();
        lectures.add(university.addLecture("L_001", new Lecture()));
    }
}
