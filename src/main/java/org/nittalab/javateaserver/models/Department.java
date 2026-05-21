package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class Department {
    private String department_name; //学科の名称(ID代わり)
    private HashMap<String, Lecture> lecturesInDepartment = new HashMap<>(); //学科特有の科目(ID, 授業科目)

    //コンストラクタ
    public Department(String department_name) {
        this.department_name = department_name;
        this.lecturesInDepartment = new HashMap<>();
    }

    //学科名(ID)を取得(念のため)
    public String getDepartmentName() {
        return department_name;
    }

    //学科特有の科目の追加
    public void addLecture(String lectureId, Lecture lecture) {
        lecturesInDepartment.put(lectureId, lecture);
    }

    //lecture_IDで指定された授業科目の取得(念のため)
    public Lecture getLecture(String lectureId) {
        if (!lecturesInDepartment.containsKey(lectureId)){
            return null; //指定先がなければnullを返す
        }

        return lecturesInDepartment.get(lectureId);
    }

    //学科特有の科目の一覧の取得
    public HashMap<String, Lecture> getLectures() {
        return lecturesInDepartment;
    }
}
