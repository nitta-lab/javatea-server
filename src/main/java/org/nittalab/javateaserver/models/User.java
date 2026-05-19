package org.nittalab.javateaserver.models;

public class User {

    private String uid;
    private String name;
    private String pw;
    private String university;
    private String faculty;
    private String department;
    private int grade;

    //渡されたuid, name, pwをフィールドに代入
    public User(String uid, String name, String pw){
        this.uid = uid;
        this.name = name;
        this.pw = pw;
    }

    //uidの取得
    public String getUid(){
        return uid;
    }

    //setUid
    //getName
    //setName
    //getPw
    //setPw
    //getUniversity
    //setUniversity
    //getFaculty
    //setFaculty
    //getDepartment
    //setDepartment
    //getGrade
    //setGrade

    //UserのTokenを返すメゾット
    public String getToken(){
        return null;
    }

    //setToken
}
