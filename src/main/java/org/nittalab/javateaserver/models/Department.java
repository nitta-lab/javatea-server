package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class Department {
    private String department_name;
    private HashMap<String, Lecture> lecturesInDepartment = new HashMap<>();

    public Department(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartmentName() {
        return department_name;
    }

    public void addLecture(String lectureId, Lecture lecture) {
        lecturesInDepartment.put(lectureId, lecture);
    }

    public Lecture getLecture(String lectureId) {
        Lecture lec = this.lecturesInDepartment.get(lectureId);

        if (lec == null){
            throw new IllegalArgumentException("Lecture not found: " + lectureId);
        }

        return lec;
    }
}
