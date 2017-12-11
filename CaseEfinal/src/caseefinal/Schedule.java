/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Viktor
 */
public class Schedule {
// AFH van scenario inlezen

  
    public ArrayList<Department> departments; // USAGE?

    public Schedule(ArrayList<Department> departments) {
        this.departments = departments;
    }

    public void addDep(Department dep) {
        departments.add(dep);
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

}
