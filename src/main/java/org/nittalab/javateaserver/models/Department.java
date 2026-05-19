package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class Department {
    private String department_name;
    private HashMap<String, Lecture> lectures = new HashMap<>();

    public Department(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartmentName() {
        return department_name;
    }

    public void addLecture(String lectureId, Lecture lecture) {
        lectures.put(lectureId, lecture);
    }

    public Lecture getLecture(String lectureId) {
        return lectures.get(lectureId);
    }
}
