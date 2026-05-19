package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class Faculty {
    public HashMap<String, Department> departments = new HashMap<>();

    public void createDepartment(String department_name){
        this.departments.put(department_name, new Department());
    }

    public String getDepartment(String department_name){
        return this.departments.get(department_name).department_name;
    }
}
