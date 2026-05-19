package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class Faculty {
    private String faculty_name;
    private HashMap<String, Department> departments = new HashMap<>();
    private HashMap<String, Lecture> lecturesInFaculty = new HashMap<>();

    public Faculty(String faculty_name) {
        this.faculty_name = faculty_name;
        this.departments = new HashMap<>();
    }

    public String getFacultyName() {
        return faculty_name;
    }

    public void createDepartment(String department_name){
        this.departments.put(department_name, new Department(department_name));
    }

    public Department getDepartment(String department_name){
        Department dep = this.departments.get(department_name);

        if (dep == null){
            throw new IllegalArgumentException(department_name + "not found");
        }

        return dep;
    }

    public void addLecture(String lectureId, Lecture lecture) {
        lecturesInFaculty.put(lectureId, lecture);
    }

    public Lecture getLecture(String lectureId) {
        return lecturesInFaculty.get(lectureId);
    }
}
