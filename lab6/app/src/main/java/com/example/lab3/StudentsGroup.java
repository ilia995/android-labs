package com.example.lab3;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentsGroup {
    private final int id;
    private final String groupNumber;
    private final String groupName;
    private final int educationLevel;
    private final boolean hasContracts;
    private final boolean hasPrivilegeStudents;

    public StudentsGroup(int id, String groupNumber, String groupName, int educationLevel,
                         boolean hasContracts, boolean hasPrivilegeStudents){
        this.id = id;
        this.groupNumber = groupNumber;
        this.groupName = groupName;
        this.educationLevel = educationLevel;
        this.hasContracts = hasContracts;
        this.hasPrivilegeStudents = hasPrivilegeStudents;
    }
    public int getId(){
        return id;
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
    private static final ArrayList<StudentsGroup> groups = new ArrayList<StudentsGroup>(
            Arrays.asList(
                    new StudentsGroup(1, "K25–1", "Комп’ютерні науки", 0, false, false),
                    new StudentsGroup(2, "K25–2", "Комп’ютерні науки", 0, true, false),
                    new StudentsGroup(3, "K25–3", "Комп’ютерні науки", 0, true, true),
                    new StudentsGroup(4, "K25–4", "Комп’ютерні науки", 0, true, false),
                    new StudentsGroup(5, "K25m", "Комп’ютерні науки", 1, true, false)
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
    public static ArrayList<String> getGroups(){
        ArrayList<String> groupNameList = new ArrayList<>();
        for (StudentsGroup g: groups){
            groupNameList.add(g.groupNumber);
        }
        return groupNameList;
    }
    public static ArrayList<StudentsGroup> getGroupObjects(){
        return new ArrayList<>(groups);
    }
//    public static void addGroup(String number, String name){
//        StudentsGroup newGroup = new StudentsGroup(number, name, 0, false, false);
//        groups.add(newGroup);
//    }
}
