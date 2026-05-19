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
        if (!departments.containsKey(department_name)){
            return null;
        }

        return this.departments.get(department_name);
    }

    public void addLecture(String lectureId, Lecture lecture) {
        lecturesInFaculty.put(lectureId, lecture);
    }

    public HashMap<String, Lecture> getLectures() {
        return lecturesInFaculty;
    }
}
