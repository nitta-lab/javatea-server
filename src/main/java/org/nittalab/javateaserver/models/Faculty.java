package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class Faculty {
    private String faculty_name; //学部の名称(ID代わり)
    private HashMap<String, Department> departments; //学科(学科の名称(ID), 学科)
    private HashMap<String, Lecture> lecturesInFaculty; //学部全般科目(ID, 授業科目)

    //コンストラクタ
    public Faculty(String faculty_name) {
        this.faculty_name = faculty_name;
        this.departments = new HashMap<>();
        this.lecturesInFaculty = new HashMap<>();
    }

    //学部名(ID)を取得(念のため)
    public String getFacultyName() {
        return faculty_name;
    }

    //学部の作成、追加
    public Department createDepartment(String department_name){

        //学科が既に存在していた場合はnullを返す
        if (departments.containsKey(department_name)){
            System.out.println("Department already exists.");
            return null;
        }

        this.departments.put(department_name, new Department(department_name));
        return departments.get(department_name);
    }

    //名称(ID)で指定された学部の取得(念のため)
    public Department getDepartment(String department_name){
        if (!departments.containsKey(department_name)){
            return null; //指定先がなければnullを返す
        }

        return this.departments.get(department_name);
    }

    //学部の一覧の取得
    public HashMap <String, Department> getDepartments(){
        return departments;
    }

    //学部全般科目の追加
    public void addLecture(String lectureId, Lecture lecture) {
        lecturesInFaculty.put(lectureId, lecture);
    }

    //lecture_IDで指定された授業科目の取得(念のため)
    public Lecture getLecture(String lectureId) {
        if (!lecturesInFaculty.containsKey(lectureId)){
            return null; //指定先がなければnullを返す
        }

        return lecturesInFaculty.get(lectureId);
    }

    //学部全般科目の一覧の取得
    public HashMap<String, Lecture> getLectures() {
        return this.lecturesInFaculty;
    }
}
