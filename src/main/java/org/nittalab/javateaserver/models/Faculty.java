package org.nittalab.javateaserver.models;

import java.util.HashMap;

public class Faculty {
    public HashMap<String, Department> departments = new HashMap<>();

    public void createDepartment(String department_name){
        this.departments.put(department_name, new Department());
    }

    public Department getDepartment(String department_name){
        Department dep = this.departments.get(department_name);

        if (dep == null){
            throw new IllegalArgumentException(department_name + "not found");
        }

        return dep;
    }
}
