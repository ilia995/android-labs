package com.example.lab3;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentsGroup {
    private String groupNumber;
    private String groupName;
    private int educationLevel;
    private boolean hasContracts;
    private boolean hasPrivilegeStudents;

    public StudentsGroup(String groupNumber, String groupName, int educationLevel,
                         boolean hasContracts, boolean hasPrivilegeStudents){
        this.groupNumber = groupNumber;
        this.groupName = groupName;
        this.educationLevel = educationLevel;
        this.hasContracts = hasContracts;
        this.hasPrivilegeStudents = hasPrivilegeStudents;
    }
    public String getNumber(){
        return groupNumber;
    }
    public String getGroupName(){
        return groupName;
    }
    public int getEducationLevel(){
        return educationLevel;
    }
    public boolean hasContracts(){
        return hasContracts;
    }
    public boolean hasPrivilegeStudent(){
        return hasPrivilegeStudents;
    }
    private final static ArrayList<StudentsGroup> groups = new ArrayList<StudentsGroup>(
            Arrays.asList(
                    new StudentsGroup("K25–1", "Комп’ютерні науки", 0, false, false),
                    new StudentsGroup("K25–2", "Комп’ютерні науки", 0, true, false),
                    new StudentsGroup("K25–3", "Комп’ютерні науки", 0, true, true),
                    new StudentsGroup("K25–4", "Комп’ютерні науки", 0, true, false),
                    new StudentsGroup("K25m", "Комп’ютерні науки", 1, true, false)
                )
    );
    public static StudentsGroup getGroup(String groupNumber){
        for(StudentsGroup g: groups){
            if(g.getNumber().equals(groupNumber)){
                return g;
            }
        }
        return null;
    }
}
