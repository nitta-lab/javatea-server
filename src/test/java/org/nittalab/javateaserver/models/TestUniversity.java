package org.nittalab.javateaserver.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUniversity {

    @Test
    void UniverityTest(){
        University university = new University("U_001", "大学", "ダイガク");

        //大学情報の取得
        ArrayList<String> univInfos = new ArrayList<>();
        univInfos.add(university.getId());
        univInfos.add(university.getName());
        univInfos.add(university.getKana());
        ArrayList<String> univInfosExp = new ArrayList<>();
        univInfosExp.add("U_001");
        univInfosExp.add("大学");
        univInfosExp.add("ダイガク");
        System.out.println(univInfosExp);
        assertEquals(univInfosExp, univInfos);

        //大学情報の変更
        university.setName("医科大学");
        university.setKana("イカダイガク");
        ArrayList<String> univNameInfos = new ArrayList<>();
        univNameInfos.add(university.getName());
        univNameInfos.add(university.getKana());
        ArrayList<String> univNameInfosExp = new ArrayList<>();
        univNameInfosExp.add("医科大学");
        univNameInfosExp.add("イカダイガク");
        System.out.println(univNameInfosExp);
        assertEquals(univNameInfosExp, univNameInfos);

        //学部の作成、取得
        university.createFaculty("A学部");
        university.createFaculty("B学部");
        university.createFaculty("C学部");
        university.createFaculty("A学部"); //重複時の確認
        ArrayList<String> facultiesExp = new ArrayList<>();
        facultiesExp.add("A学部");

    }
}
