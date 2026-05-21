package org.nittalab.javateaserver.models;

import java.util.HashMap;
import java.util.Set;

public class University {
    //関数は動詞名詞
    //getは取ってくる(変更はなし)、setは設定(まるごと入れ替え)、putは一部書き換え,removeは削除
    //addは引数に渡している場合、createは関数の中で新しく作ったものを追加する場合
    private String univ_id;
    private String name;
    private String kana;
    private HashMap<String, Lecture> lectures = new HashMap<>();
    private HashMap<String, Faculty> faculties = new HashMap<>();//key:faculty_name, value:Facultyクラス

    //コンストラクタ(初期化)
    public University(String univ_id, String name, String kana) { //クラス名と同じにする、初期化みたいなもの
        this.univ_id = univ_id; //this.univ_idは、引数のuniv-id。
        this.name = name;
        this.kana = kana;
    }

    //univ-idの取得
    public String getId() {
        return univ_id;
    }

    //name(大学の名前)の取得
    public String getName() {
        return name;
    }

    //kada(大学の読み仮名)の取得
    public String getKana() {
        return kana;
    }

    //学部を作成
    public Faculty createFaculty(String faculty_name) {
        faculties.put(faculty_name, new Faculty(faculty_name));
        return faculties.get(faculty_name);
    }

    //Facultyクラスを取得
    public Faculty getFaculty(String faculty_name) {
        if (!faculties.containsKey(faculty_name)) {
            return null;
        }
        return faculties.get(faculty_name);
    }

    //学部一覧を取得
    public Set<String> getFaculties() {
        return faculties.keySet();
    }

    //大学全般科目の追加
    public void addLecture(String lecture_id, Lecture lecture) {
        lectures.put(lecture_id, lecture);
    }

    //lecture-idで指定された授業科目の取得
    public Lecture getLecture(String lecture_id) {
        if  (!lectures.containsKey(lecture_id)) {
            return null;
        }
        return lectures.get(lecture_id);
    }

    //大学全般科目の一覧取得(idのみ)
    public Set<String> getLectures() {
        return lectures.keySet();
    }
}