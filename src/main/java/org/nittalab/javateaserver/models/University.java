package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class University {
    //関数は動詞名詞
    //getは取ってくる(変更はなし)、setは設定(まるごと入れ替え)、putは一部書き換え,removeは削除
    //addは引数に渡している場合、createは関数の中で新しく作ったものを追加する場合
    private String faculty_name;
    private String univ_id;
    private String name;
    private String kana;
    private HashMap<String, Lecture> lectures = new HashMap<>();
    private HashMap<String, Faculty> faculties = new HashMap<>();
    public University(String univ_id, String name, String kana) { //クラス名と同じにする、初期化みたいなもの
        this.univ_id = univ_id; //this.univ_idは、引数のuniv-id。
        this.name = name;
        this.kana = kana;
    }
    public Faculty createFaculty(String faculty_name){
        return null;
    }

    public Faculty getFaculty(String faculty_name){
        return null;
    }

}